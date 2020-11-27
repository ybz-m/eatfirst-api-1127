package jp.co.eatfirst.backendapi.middleware.web.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import jp.co.eatfirst.backendapi.middleware.web.validated.MinNumValidate;

/**
 * 全角半角文字バリデーションチェック
 * 
 */
public class MinNumValidator implements ConstraintValidator<MinNumValidate, Integer> {

    private int min;

    @Override
    public void initialize(MinNumValidate constraintAnnotation) {
        this.min = constraintAnnotation.min();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {

        if (null == value) {
            return true;
        }
        // エラーメッセージを取得
        String defaultConstraintMessageTemplate = context.getDefaultConstraintMessageTemplate();
        // エラーメッセージを保存する
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(defaultConstraintMessageTemplate).addConstraintViolation();
        if (value.intValue() < min) {
            // BindingResultにはエラー情報が含まれています
            return false;
        }

        return true;
    }
}
