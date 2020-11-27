package jp.co.eatfirst.backendapi.middleware.web.validator;

import java.io.UnsupportedEncodingException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;

import jp.co.eatfirst.backendapi.middleware.web.validated.MaxLengthValidate;

/**
 * 全角半角文字バリデーションチェック
 * 
 */
public class MaxLengthValidator implements ConstraintValidator<MaxLengthValidate, String> {

    private int max;

    @Override
    public void initialize(MaxLengthValidate constraintAnnotation) {
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // テキスト文字を取得
        byte[] bytes;
        int length = 0;
        try {
            if (StringUtils.isNotEmpty(value)) {
                bytes = value.getBytes("UTF-8");
                length = bytes.length;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // エラーメッセージを取得
        String defaultConstraintMessageTemplate = context.getDefaultConstraintMessageTemplate();
        // エラーメッセージを保存する
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(defaultConstraintMessageTemplate).addConstraintViolation();
        if (length > max) {
            // BindingResultにはエラー情報が含まれています
            return false;
        }

        return true;
    }
}
