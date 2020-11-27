package jp.co.eatfirst.backendapi.middleware.log;

import jp.co.eatfirst.backendapi.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
public class LogAspect {
    @Before("@within(jp.co.eatfirst.backendapi.middleware.log.Log)" +
            "|| @annotation(org.springframework.web.bind.annotation.GetMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.PutMapping)" +
            "|| @annotation(org.springframework.web.bind.annotation.PostMapping)" +
            "|| @annotation(org.springframework.web.bind.annotation.DeleteMapping)" +
            "|| @annotation(org.springframework.web.bind.annotation.RequestMapping)" +
            "|| @annotation(jp.co.eatfirst.backendapi.middleware.log.Log)")
    public void beforeSwitchDataSource(JoinPoint point){
        Class<?> className = point.getTarget().getClass();
        String methodName = point.getSignature().getName();
        log.info(String.format("%s_%s START %s", className.getSimpleName(), methodName, DateUtil.asString_ymdhms(LocalDateTime.now())));

    }

    @After("@within(jp.co.eatfirst.backendapi.middleware.log.Log)" +
            "|| @annotation(org.springframework.web.bind.annotation.GetMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.PutMapping)" +
            "|| @annotation(org.springframework.web.bind.annotation.PostMapping)" +
            "|| @annotation(org.springframework.web.bind.annotation.DeleteMapping)" +
            "|| @annotation(org.springframework.web.bind.annotation.RequestMapping)" +
            "|| @annotation(jp.co.eatfirst.backendapi.middleware.log.Log)")
    public void afterSwitchDS(JoinPoint point){
        Class<?> className = point.getTarget().getClass();
        String methodName = point.getSignature().getName();
        log.info(String.format("%s_%s END %s", className.getSimpleName(), methodName, DateUtil.asString_ymdhms(LocalDateTime.now())));
    }
}
