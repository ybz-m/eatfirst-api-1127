package jp.co.eatfirst.backendapi.middleware.redis;

import com.google.common.collect.Lists;
import jp.co.eatfirst.backendapi.middleware.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
public class RedisDistributedLockAspect {
    @Autowired
    RedisTemplate redisTemplate;
    @Value("${distributedlock.waitingTime}")
    private long waitingTime = 3000L;
    @Value("${distributedlock.timeout}")
    private long timeout = 3000L;
    @Value("${distributedlock.retries}")
    private int retries = 3;

    @Before("@annotation(jp.co.eatfirst.backendapi.middleware.redis.RedisDistributedLock)")
    public void before(JoinPoint point){
        String key = getKey(point);

        if(StringUtils.isNotEmpty(key) && RedisLockHolder.get(key) == null) {
            DistributedLock lock = null;
            try {
                log.info("get lock:" + key);
                lock = DistributedLock.lock(redisTemplate, key, timeout, retries, waitingTime);
                if(lock == null){
                    throw new BusinessException("lock error");
                }
                log.info("get lock:" + key + ";success");
                RedisLockHolder.save(key, lock);
            } catch (InterruptedException e) {
                throw new BusinessException("操作失败");
            }
        }
    }

    @After("@annotation(jp.co.eatfirst.backendapi.middleware.redis.RedisDistributedLock)")
    public void after(JoinPoint point){
        String key = getKey(point);
        DistributedLock lock = RedisLockHolder.get(key);
        if(lock != null){
            log.info("release lock:" + key);
            lock.release();
            RedisLockHolder.clear(key);
        }
    }

    private String getKey(JoinPoint point){
        Class<?> className = point.getTarget().getClass();
        String methodName = point.getSignature().getName();
        Class[] argClass = ((MethodSignature)point.getSignature()).getParameterTypes();
        try {
            Method method = className.getMethod(methodName, argClass);
            RedisDistributedLock annotation = method.getAnnotation(RedisDistributedLock.class);
            if(annotation.key().startsWith("#")){
                String[] names = ((MethodSignature)point.getSignature()).getParameterNames();
                int paramIndex = Lists.newArrayList(names).indexOf(annotation.key().substring(1));
                if(paramIndex < 0 || paramIndex > point.getArgs().length - 1){
                    return "";
                }
                Object paramValue = point.getArgs()[paramIndex];
                return annotation.prex() + String.valueOf(paramValue);
            } else {
                return annotation.prex() + annotation.key();
            }

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return "";
    }
}
