package jp.co.eatfirst.backendapi.middleware.config;

import jp.co.eatfirst.backendapi.middleware.database.DataSourceCleanInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new DataSourceCleanInterceptor()).addPathPatterns("/**");
    }
}
