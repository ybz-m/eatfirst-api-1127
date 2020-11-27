package jp.co.eatfirst.backendapi.middleware.security;

import jp.co.eatfirst.backendapi.middleware.security.core.SimpleTokenFilter;
import jp.co.eatfirst.backendapi.middleware.security.web.AuthenticationAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jp.co.eatfirst.backendapi.middleware.security.core.SimpleAuthenticationEntryPoint;
import jp.co.eatfirst.backendapi.middleware.security.web.LogoutAuthenticationSuccessHandler;
import jp.co.eatfirst.backendapi.middleware.security.open.OpenAuthenticationFilter;
import jp.co.eatfirst.backendapi.middleware.security.open.OpenAuthenticationManager;
import jp.co.eatfirst.backendapi.middleware.security.open.OpenLoginSuccessHandler;

@Configuration
@EnableWebSecurity
@Order(1)
public class OpenWebApiSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LogoutAuthenticationSuccessHandler logoutSuccessHandler;

    @Autowired
    AuthenticationAccessDeniedHandler deniedHandler;

    @Autowired
    SimpleAuthenticationEntryPoint simpleAuthenticationEntryPoint;

    @Autowired
    OpenLoginSuccessHandler wechatLoginSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
        .anonymous().disable()
            // AUTHORIZE
            .antMatcher("/openwebapi/**")
                .authorizeRequests()
            // white list
            .antMatchers("/openwebapi/token/**").permitAll()
            .antMatchers("/openwebapi/api/**")
                .authenticated()
            // AUTHORIZE
        .and()
                .addFilterAt(wechatAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(simpleWechatApiTokenFilter(), OpenAuthenticationFilter.class)
            // EXCEPTION
            .exceptionHandling()
            .accessDeniedHandler(deniedHandler)
            .authenticationEntryPoint(simpleAuthenticationEntryPoint)
        .and()
            // LOGOUT
            .logout()
            .logoutUrl("/openwebapi/auth/logout")
            .logoutSuccessHandler(logoutSuccessHandler)
        .and()
            // CSRF
        .csrf().disable()
            // SESSION
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        ;
        // @formatter:on
    }

    @Autowired
    private OpenAuthenticationManager wechatAuthenticationManager;

    @Bean
    public SimpleTokenFilter simpleWechatApiTokenFilter() {
        return new SimpleTokenFilter("/openwebapi/api/**", true);
    }

    @Bean
    public OpenAuthenticationFilter wechatAuthenticationFilter() {
        OpenAuthenticationFilter wechatAuthenticationFilter = new OpenAuthenticationFilter("/openwebapi/auth/login");
        wechatAuthenticationFilter.setAuthenticationManager(wechatAuthenticationManager);
        wechatAuthenticationFilter.setAuthenticationSuccessHandler(wechatLoginSuccessHandler);
        return wechatAuthenticationFilter;
    }
}
