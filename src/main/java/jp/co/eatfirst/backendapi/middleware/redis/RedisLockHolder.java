package jp.co.eatfirst.backendapi.middleware.redis;

import com.google.common.collect.Maps;

import java.util.concurrent.ConcurrentMap;

public class RedisLockHolder {
    private static final String KEY= "RedisLockHolder";
    private static final ThreadLocal<ConcurrentMap<String, DistributedLock>> contextHolder = new ThreadLocal<>();

    public static void save(String key, DistributedLock lock) {
        synchronized (KEY){
            if(contextHolder.get() != null){
                contextHolder.get().put(key, lock);
            } else {
                ConcurrentMap<String, DistributedLock> map = Maps.newConcurrentMap();
                map.put(key, lock);
                contextHolder.set(map);
            }
        }
    }
    public static DistributedLock get(String key) {
        if(contextHolder.get() != null && contextHolder.get().containsKey(key)){
            return contextHolder.get().get(key);
        }
        return null;

    }
    public static void clear(String key) {
        synchronized (KEY){
            if(contextHolder.get() != null && contextHolder.get().containsKey(key)){
                contextHolder.get().remove(key);
            }
        }
    }
}
