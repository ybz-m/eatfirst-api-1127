package jp.co.eatfirst.backendapi.middleware.frequentlycheck;


import jp.co.eatfirst.backendapi.middleware.redis.DistributedLock;
import jp.co.eatfirst.backendapi.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Aspect
@Component
@Slf4j
public class RedisUserIdFrequentlyCheckAspect extends FrequentlyCheckAspect{
    @Autowired
    RedisTemplate redisTemplate;
    @Value("${frequentlycheck.timeout}")
    private long timeout = 3000L;
    @Autowired
    MessageSource messageSource;

    @Override
    public void lock(String key, long timeout) {
        try {
            log.info("get lock:" + key);
            if(DistributedLock.lock(redisTemplate, key, timeout < 0? this.timeout: timeout, 0, 0) == null){
                throw new FrequentlyCheckException(messageSource.getMessage("FrequentlyCheck.error", null, Locale.JAPANESE), null);
            }
            log.info("get lock:" + key + ";success");
        } catch (InterruptedException e) {
            throw new FrequentlyCheckException(messageSource.getMessage("FrequentlyCheck.error", null, Locale.JAPANESE), null);
        }
    }

    @Override
    public String getDefaultKey() {
        return SecurityUtil.getLoginUserIdForApi() + "_";
    }
}
