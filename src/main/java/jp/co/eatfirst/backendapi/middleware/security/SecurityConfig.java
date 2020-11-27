package jp.co.eatfirst.backendapi.middleware.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jp.co.eatfirst.backendapi.middleware.security.core.LoginUserDetailsService;
import jp.co.eatfirst.backendapi.middleware.security.core.SimpleAuthenticationEntryPoint;
import jp.co.eatfirst.backendapi.middleware.security.core.SimpleTokenFilter;
import jp.co.eatfirst.backendapi.middleware.security.web.AuthenticationAccessDeniedHandler;
import jp.co.eatfirst.backendapi.middleware.security.web.LoginAuthenticationFailureHandler;
import jp.co.eatfirst.backendapi.middleware.security.web.LoginAuthenticationSuccessHandler;
import jp.co.eatfirst.backendapi.middleware.security.web.LogoutAuthenticationSuccessHandler;

/**
 * アクセス制御規約
 *
 * @author hzhou
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(2)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginUserDetailsService userDetailsService;

    @Autowired
    private LoginAuthenticationSuccessHandler successHandler;

    @Autowired
    private LoginAuthenticationFailureHandler failureHandler;

    @Autowired
    private LogoutAuthenticationSuccessHandler logoutSuccessHandler;

    @Autowired
    AuthenticationAccessDeniedHandler deniedHandler;

    @Autowired
    SimpleAuthenticationEntryPoint simpleAuthenticationEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                // AUTHORIZE
                .authorizeRequests()

                // white list
				.antMatchers("/openwebapi/**",  //openwebapiURL群
                        "/token/**", //トークン処理群
                        "/"//ヘルスチェック
				        ).permitAll()
                .mvcMatchers("/webapi/**")
                    .hasAnyRole("ADMIN", "USER")
                .mvcMatchers("/webapi/admin/**")
                    .hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                // EXCEPTION
                .exceptionHandling()
                .accessDeniedHandler(deniedHandler)
                .authenticationEntryPoint(simpleAuthenticationEntryPoint)
                .and()
                // LOGIN
                .formLogin()
                .loginPage("/login_p")
                .loginProcessingUrl("/webapi/login").permitAll()
                .usernameParameter("userId")
                .passwordParameter("password")
                .successHandler(successHandler)
                .failureHandler(failureHandler)
                .and()
                // LOGOUT
                .logout()
                .logoutUrl("/webapi/logout")
                .logoutSuccessHandler(logoutSuccessHandler)
                .and()
                // CSRF
                .csrf()
                .disable()
                // AUTHORIZE
                .addFilterBefore(simpleTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                // SESSION
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        ;
        // @formatter:on
    }

    @Bean
    public SimpleTokenFilter simpleTokenFilter() {
        return new SimpleTokenFilter("/webapi/**");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // @formatter:off
        auth
                .eraseCredentials(true)
                .userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
        // @formatter:on
    }
}
