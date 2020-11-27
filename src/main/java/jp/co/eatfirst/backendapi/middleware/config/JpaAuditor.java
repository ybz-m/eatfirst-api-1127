package jp.co.eatfirst.backendapi.middleware.config;

import jp.co.eatfirst.backendapi.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@Configuration
@Slf4j
public class JpaAuditor implements AuditorAware<String> {
    /**
     * @return
     */
    @Override
    public Optional<String> getCurrentAuditor() {

        try {
            return Optional.ofNullable(String.valueOf(SecurityUtil.getLoginUserIdForApi()));
        }catch (Exception e){
            return Optional.empty();
        }
    }
}
