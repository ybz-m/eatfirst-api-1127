package jp.co.eatfirst.backendapi.middleware.security.core;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 認証が必要なリソースに未認証でアクセスしたときの処理
 */
@Slf4j
@Component
public class SimpleAuthenticationEntryPoint implements AuthenticationEntryPoint {

    public SimpleAuthenticationEntryPoint() {
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        if (response.isCommitted()) {
            log.info("Response has already been committed.");
            return;
        }

        if (request.getAttribute(SimpleTokenFilter.FILTER_APPLIED) == null) {
            response.sendError(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase());
        }
        response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
    }
}
