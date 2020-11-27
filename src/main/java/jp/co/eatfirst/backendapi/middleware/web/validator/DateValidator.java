package jp.co.eatfirst.backendapi.middleware.web.validator;

import java.time.LocalDateTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import jp.co.eatfirst.backendapi.middleware.web.validated.DateValidate;
import org.apache.commons.lang.StringUtils;

import jp.co.eatfirst.backendapi.util.DateUtil;

/**
 * データのバリデーションチェック
 * 
 */
public class DateValidator implements ConstraintValidator<DateValidate, String> {

    @Override
    public void initialize(DateValidate constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (StringUtils.isEmpty(value)) {
            return true;
        }

        try {
            LocalDateTime.parse(value, DateUtil.ymdhms);
        } catch (Exception e) {
            return false;
        }

        return true;

    }
}
