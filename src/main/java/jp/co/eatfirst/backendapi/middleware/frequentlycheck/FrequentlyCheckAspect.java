package jp.co.eatfirst.backendapi.middleware.frequentlycheck;

import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;


public abstract class FrequentlyCheckAspect {


    @Before("@annotation(FrequentlyCheck)")
    public void before(JoinPoint point){
        Class<?> className = point.getTarget().getClass();
        String methodName = point.getSignature().getName();
        Class[] argClass = ((MethodSignature)point.getSignature()).getParameterTypes();
        try {
            Method method = className.getMethod(methodName, argClass);
            FrequentlyCheck annotation = method.getAnnotation(FrequentlyCheck.class);
            String key = getDefaultKey() + getKey(annotation, point, className.getSimpleName() + ":" + methodName);
            lock(key, annotation.timeout());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public abstract void lock(String key, long timeout);
    public abstract String getDefaultKey();

    private String getKey(FrequentlyCheck annotation, JoinPoint point, String defaultKey){
        if(annotation.key().startsWith("#")){
            String[] names = ((MethodSignature)point.getSignature()).getParameterNames();
            int paramIndex = Lists.newArrayList(names).indexOf(annotation.key().substring(1));
            if(paramIndex < 0 || paramIndex > point.getArgs().length - 1){
                return "";
            }
            Object paramValue = point.getArgs()[paramIndex];
            return String.valueOf(paramValue);
        } else {
            if(StringUtils.isNotEmpty(annotation.key())){
                return annotation.key();
            } else {
                return defaultKey;
            }
        }
    }
}
