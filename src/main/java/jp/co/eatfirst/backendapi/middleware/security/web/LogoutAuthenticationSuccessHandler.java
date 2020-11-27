package jp.co.eatfirst.backendapi.middleware.security.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.exceptions.TokenExpiredException;
import jp.co.eatfirst.backendapi.middleware.security.core.SimpleTokenFilter;
import jp.co.eatfirst.backendapi.middleware.web.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

import jp.co.eatfirst.backendapi.middleware.security.core.JwtService;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LogoutAuthenticationSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    private JwtService jwtService;

    @Value("${security.token-header}")
    private String headerKey = "";

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (response.isCommitted()) {
            log.info("Response has already been committed.");
            return;
        }

        String token = resolveToken(request);
        if (token == null) {
            return;
        }
        try {
            DecodedJWT jwt = jwtService.verifyToken(token);
            if(request.getRequestURI().contains("openwebapi")){
                jwtService.authenticationForOpenWeb(jwt);
            } else {
                jwtService.authentication(jwt);
            }

            response.setContentType("application/json;charset=utf-8");
            JsonResult respBean = JsonResult.success("ログアウト成功しました。");
            ObjectMapper om = new ObjectMapper();
            PrintWriter out = response.getWriter();
            out.write(om.writeValueAsString(respBean));
            out.flush();
            out.close();
            jwtService.evictToken(token);

        } catch (TokenExpiredException e) {
            SecurityContextHolder.clearContext();
            request.setAttribute(SimpleTokenFilter.FILTER_APPLIED, true);
            ((HttpServletResponse) response).sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
            jwtService.evictToken(token);
        } catch (JWTVerificationException e) {
            SecurityContextHolder.clearContext();
            request.setAttribute(SimpleTokenFilter.FILTER_APPLIED, true);
            ((HttpServletResponse) response).sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
        }
    }

    private String resolveToken(ServletRequest request) {
        String token = ((HttpServletRequest) request).getHeader("Authorization");
        if (token == null || !token.startsWith(headerKey + " ")) {
            return null;
        }
        // remove "Bearer "
        return token.substring(7);
    }
}
