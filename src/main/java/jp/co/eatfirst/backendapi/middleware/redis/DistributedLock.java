package jp.co.eatfirst.backendapi.middleware.redis;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;


public class DistributedLock{
    private RedisTemplate redisTemplate;

    private String key;
    private String value;
    private long expire;
    private static final String LOCK_PREX = "-D_LOCK-";
    private static final String COMPARE_AND_DELETE =
            "if redis.call('get',KEYS[1]) == ARGV[1] then " +
                    "return redis.call('del',KEYS[1]) else return 0 end";
    private static final String LOCK = "if redis.call('setnx',KEYS[1],ARGV[1]) == 1 then" +
            " redis.call('expire',KEYS[1],ARGV[2]) return 1 else return 0 end";

    public String getKey(){
        return key;
    }
    /**
     * @param redisTemplate
     * @param key
     * @param value
     * @param expire
     */
    public DistributedLock(RedisTemplate redisTemplate, String key, String value, long expire) {
        this.redisTemplate = redisTemplate;
        this.key = key;
        this.value = value;
        this.expire = expire;
    }
    public void release() {
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText(COMPARE_AND_DELETE);
        redisScript.setResultType(Boolean.class);
        redisTemplate.execute(redisScript, Arrays.asList(key), value);
    }
    private boolean lock() {
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText(LOCK);
        redisScript.setResultType(Boolean.class);
        return (boolean)redisTemplate.execute(redisScript, Arrays.asList(key), value, expire);
    }
    /**
     * @param key           lock key
     * @param timeout       timeout
     * @param retries       number of retries
     * @param waitingTime   retry interval
     * @return
     * @throws InterruptedException
     */
    public static DistributedLock lock(RedisTemplate redisTemplate, String key, long timeout, int retries, long waitingTime) throws InterruptedException {
        final String value
                = RandomStringUtils.randomAlphanumeric(4) + System.currentTimeMillis();
        do {
            DistributedLock lock = new DistributedLock(redisTemplate, LOCK_PREX + key, value, timeout);
            if (lock.lock()) {
                return lock;
            }
            if (retries > 0) {
                TimeUnit.MILLISECONDS.sleep(waitingTime);
            }
            if(Thread.currentThread().isInterrupted()){
                break;
            }
        } while (retries-- > 0);

        return null;
    }

}
