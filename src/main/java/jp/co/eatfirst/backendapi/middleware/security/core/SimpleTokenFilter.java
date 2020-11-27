package jp.co.eatfirst.backendapi.middleware.security.core;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.GenericFilterBean;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import jp.co.eatfirst.backendapi.middleware.exception.UserLockedException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleTokenFilter extends GenericFilterBean {

    public static final String FILTER_APPLIED = "__spring_security_simpleTokenFilter_filterApplied";

    @Autowired
    private JwtService jwtService;

    @Value("${security.token-header}")
    private String headerKey = "";

    @Value("${security.token-new}")
    private String headerNewToken = "";

    String defaultFilterProcessesUrl;

    boolean isOpenWeb;

    public SimpleTokenFilter(String defaultFilterProcessesUrl ) {
       this(defaultFilterProcessesUrl ,false);
    }
    public SimpleTokenFilter(String defaultFilterProcessesUrl, boolean isOpenWeb) {
        this.defaultFilterProcessesUrl = defaultFilterProcessesUrl;
        this.isOpenWeb = isOpenWeb;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        if (new AntPathRequestMatcher(defaultFilterProcessesUrl).matches((HttpServletRequest) request)) {

            String token = jwtService.resolveToken(request);
            if (token == null) {
                filterChain.doFilter(request, response);
                return;
            }

            if (request.getAttribute(FILTER_APPLIED) != null) {
                filterChain.doFilter(request, response);
                return;
            }

            try {
                DecodedJWT jwt = jwtService.verifyToken(token);
                if(isOpenWeb){
                    jwtService.authenticationForOpenWeb(jwt);
                } else {
                    jwtService.authentication(jwt);
                }
                filterChain.doFilter(request, response);

            } catch (TokenExpiredException e) {
                SecurityContextHolder.clearContext();
                request.setAttribute(FILTER_APPLIED, true);
                ((HttpServletResponse) response).sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
                jwtService.evictToken(token);
            } catch (JWTVerificationException e) {
                SecurityContextHolder.clearContext();
                request.setAttribute(FILTER_APPLIED, true);
                ((HttpServletResponse) response).sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
            }
//            catch (UserLockedException e) {
//                SecurityContextHolder.clearContext();
//                request.setAttribute(FILTER_APPLIED, true);
//                log.info("账号（Userid:" + e.getLockedUser().getId() + "）被锁定。锁定类型:" + e.getLockedUser().getStatus());
//                ((HttpServletResponse) response).sendError(HttpStatus.LOCKED.value(), HttpStatus.LOCKED.getReasonPhrase());
//            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

}
