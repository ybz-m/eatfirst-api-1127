package jp.co.eatfirst.backendapi.middleware.security.open;

import jp.co.eatfirst.backendapi.middleware.exception.BusinessException;
import jp.co.eatfirst.backendapi.middleware.security.core.LoginUserDetailsService;
import jp.co.eatfirst.backendapi.middleware.security.core.SimpleTokenFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class OpenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public OpenAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Autowired
    private LoginUserDetailsService loginUserDetailsService;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        String openid = httpServletRequest.getParameter("openid");
        if (StringUtils.isBlank(openid)) {
            log.info("openid is null");
            openid = UUID.randomUUID().toString();
        } else {
            try{
                loginUserDetailsService.loadWebUserByIdentifier(openid);
            } catch (UsernameNotFoundException e) {
                httpServletRequest.setAttribute(SimpleTokenFilter.FILTER_APPLIED, true);
                throw new BusinessException("openid is invalid");
            }
        }
        return this.getAuthenticationManager().authenticate(new AuthenticationToken(openid));
    }

}