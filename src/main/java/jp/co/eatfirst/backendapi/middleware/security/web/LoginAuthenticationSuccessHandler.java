package jp.co.eatfirst.backendapi.middleware.security.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.eatfirst.backendapi.app.dto.mapper.StoreStaffMapper;
import jp.co.eatfirst.backendapi.app.service.StaffService;
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

import jp.co.eatfirst.backendapi.middleware.security.core.ApiUser;
import jp.co.eatfirst.backendapi.middleware.security.core.JwtService;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j(topic = "LoginAuditLog")
public class LoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private StaffService staffService;
    @Autowired
    private StoreStaffMapper storeStaffMapper;

    @Autowired
    private JwtService jwtService;

    @Value("${security.token-header}")
    private String headerKey = "";

    @Value("${security.token-authorization}")
    private String authorizationKey = "";
    @Value("${security.token-refresh}")
    private String authorizationRefreshKey = "";
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (response.isCommitted()) {
            log.info("Response has already been committed.");
            return;
        }

//        if (((ApiUser) authentication.getPrincipal()).getUser() != null && 7 < ((ApiUser) authentication.getPrincipal()).getUser().getStatus()) {
//            log.info("账号（Userid:" + ((ApiUser) authentication.getPrincipal()).getUser().getId() + "）被锁定。锁定类型:" + ((ApiUser) authentication.getPrincipal()).getUser().getStatus());
//            request.setAttribute(SimpleTokenFilter.FILTER_APPLIED, true);
//            response.sendError(HttpStatus.LOCKED.value(), HttpStatus.LOCKED.getReasonPhrase());
//            return;
//        }

        String referer = request.getHeader("Referer");
        String contextPath;
        try {
            URL url = new URL(referer);
            contextPath = url.getPath();
        } catch (MalformedURLException e) {
            contextPath = "";
        }

        ((ApiUser) authentication.getPrincipal()).setContextPath(contextPath);

        // JWTトークンをレスポンスのヘッダに設定する
        String token = jwtService.generateToken(authentication, false);
        String refreshToken = jwtService.generateToken(authentication, true);
        log.debug("generate token : {}", token);

        setToken(response, token, refreshToken);
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpStatus.OK.value());
        clearAuthenticationAttributes(request);
        log.info(AuditLogs.createAuditLog("success", request));

        // ユーザの基本情報をレスポンスに設置する
        ApiUser loginUserDetails = (ApiUser) authentication.getPrincipal();
        ObjectMapper om = new ObjectMapper();
        PrintWriter out = response.getWriter();
        out.write(om.writeValueAsString(JsonResult.success(storeStaffMapper.toVo(staffService.findStaffId(Long.parseLong(loginUserDetails.getUsername())).get()))));
        out.flush();
        out.close();
    }

    private void setToken(HttpServletResponse response, String token, String refreshToken) {
        response.setHeader(authorizationKey, String.format(headerKey + " %s", token));
        response.setHeader(authorizationRefreshKey, String.format(headerKey + " %s", refreshToken));
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
