package jp.co.eatfirst.backendapi.middleware.database;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
@Order(-1)
public class DynamicDataSourceAspect {

    @Before("@annotation(jp.co.eatfirst.backendapi.middleware.database.DataSource) || @annotation(org.springframework.transaction.annotation.Transactional)")
    public void beforeSwitchDataSource(JoinPoint point){
        Class<?> className = point.getTarget().getClass();
        String methodName = point.getSignature().getName();
        Class[] argClass = ((MethodSignature)point.getSignature()).getParameterTypes();
        try {
            Method method = className.getMethod(methodName, argClass);

            if (method.isAnnotationPresent(Transactional.class)) {
                log.info("transaction is opened.use primary datasource");
                DataSourceContextHolder.setDB(DataSourceContextHolder.DEFAULT_DS);
            } else {
                if(StringUtils.isNotEmpty(DataSourceContextHolder.getDB())){
                    log.info("already have a datasource. just use it");
                    //TODO 是否要判断自己和前序的级别，如果自己级别更高的话 覆盖前序数据源？
                } else {
                    if (method.isAnnotationPresent(DataSource.class)) {
                        log.info("user the datasource defined by annotation");
                        DataSource annotation = method.getAnnotation(DataSource.class);
                        DataSourceContextHolder.setDB(annotation.value());
                    }
                }
            }
            log.info(String.format("%s_%s USE DATASOURCE %s", className.getSimpleName(), methodName, DataSourceContextHolder.getDB()));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @After("@annotation(jp.co.eatfirst.backendapi.middleware.database.DataSource)|| @annotation(org.springframework.transaction.annotation.Transactional)")
    public void afterSwitchDS(JoinPoint point){
    }
}
