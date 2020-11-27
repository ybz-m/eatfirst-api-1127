package jp.co.eatfirst.backendapi.middleware.redis;

import jp.co.eatfirst.backendapi.middleware.log.Log;
import jp.co.eatfirst.backendapi.middleware.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Log
public class DistributedLockService {
    @Autowired
    RedisTemplate redisTemplate;
    @Value("${distributedlock.waitingTime}")
    private long waitingTime = 3000L;
    @Value("${distributedlock.timeout}")
    private long timeout = 3000L;
    @Value("${distributedlock.retries}")
    private int retries = 3;

    public DistributedLock lock(String key){
        DistributedLock lock = null;
        try {
            log.info("get lock:" + key);
            lock = DistributedLock.lock(redisTemplate, key, timeout, retries, waitingTime);
            if(lock == null){
                throw new BusinessException("lock error");
            }
            log.info("get lock:" + key + ";success");
            return lock;
        } catch (InterruptedException e) {
            throw new BusinessException("lock error");
        }
    }

    public void release(DistributedLock lock){
        if(lock == null) return;
        log.info("release lock:" + lock.getKey());
        lock.release();
    }
}
