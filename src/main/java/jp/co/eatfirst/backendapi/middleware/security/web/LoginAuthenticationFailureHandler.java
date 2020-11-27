package jp.co.eatfirst.backendapi.middleware.security.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.eatfirst.backendapi.middleware.web.JsonResult;
import jp.co.eatfirst.backendapi.util.AuditLogs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class LoginAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private static Logger auditLog = LoggerFactory.getLogger("LoginAuditLog");

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        if (response.isCommitted()) {
            auditLog.info("Response has already been committed.");
            return;
        }

        JsonResult respBean = null;
        if (e instanceof BadCredentialsException || e instanceof UsernameNotFoundException) {
            respBean = JsonResult.fail("アカウントまたはパースワード不正。");
        } else if (e instanceof LockedException) {
            respBean = JsonResult.fail("アカウントはロックされました、管理者に連絡してください。");
        } else if (e instanceof CredentialsExpiredException) {
            respBean = JsonResult.fail("パスワードが期限切れです、管理者に連絡してください。");
        } else if (e instanceof AccountExpiredException) {
            respBean = JsonResult.fail("アカウントが期限切れです、管理者に連絡してください。");
        } else if (e instanceof DisabledException) {
            respBean = JsonResult.fail("アカウントが無効になっています、管理者に連絡してください。");
        } else {
            respBean = JsonResult.fail("ログインに失敗しました。");
        }
        response.sendError(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase());
        ObjectMapper om = new ObjectMapper();
        PrintWriter out = response.getWriter();
        out.write(om.writeValueAsString(respBean));
        out.flush();
        out.close();
        auditLog.info(AuditLogs.createAuditLog("failure", request));
    }
}
