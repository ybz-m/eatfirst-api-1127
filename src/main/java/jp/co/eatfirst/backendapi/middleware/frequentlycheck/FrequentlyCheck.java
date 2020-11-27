package jp.co.eatfirst.backendapi.middleware.frequentlycheck;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface FrequentlyCheck {
    String key() default "";
    long timeout() default -1L;
}
