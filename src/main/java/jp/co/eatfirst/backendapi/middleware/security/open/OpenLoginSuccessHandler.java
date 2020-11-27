package jp.co.eatfirst.backendapi.middleware.security.open;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.eatfirst.backendapi.middleware.security.core.JwtService;
import jp.co.eatfirst.backendapi.middleware.web.JsonResult;
import jp.co.eatfirst.backendapi.util.AuditLogs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OpenLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private JwtService jwtService;

    @Value("${security.token-authorization}")
    private String authorizationKey = "";

    @Value("${security.token-refresh}")
    private String authorizationRefreshKey = "";

    @Value("${security.token-header}")
    private String headerKey = "";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        if (httpServletResponse.isCommitted()) {
            log.info("Response has already been committed.");
            return;
        }
        AuthenticationToken info = (AuthenticationToken) authentication;

        // JWTトークンをレスポンスのヘッダに設定する
        String token = jwtService.generateToken(authentication, false);
        String refreshToken = jwtService.generateToken(authentication, true);
        log.debug("generate token : {}", token);

        setToken(httpServletResponse, token, refreshToken);
        httpServletResponse.setHeader("UserId", info.getUser().getUsername());
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.setStatus(HttpStatus.OK.value());
        clearAuthenticationAttributes(httpServletRequest);
        log.info(AuditLogs.createAuditLog("success", httpServletRequest));

        // ユーザの基本情報をレスポンスに設置する
        ObjectMapper om = new ObjectMapper();
        PrintWriter out = httpServletResponse.getWriter();
        out.write(om.writeValueAsString(JsonResult.success(info.getUser())));
        out.flush();
        out.close();
    }

    private void setToken(HttpServletResponse response, String token, String refreshToken) {
        response.setHeader(authorizationKey, String.format((headerKey.endsWith(" ") ? headerKey : headerKey + " ") + "%s", token));
        response.setHeader(authorizationRefreshKey, String.format((headerKey.endsWith(" ") ? headerKey : headerKey + " ") + "%s", refreshToken));
    }

    /**
     * Removes temporary authentication-related data which may have been stored in the session during the authentication process.
     */
    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}
