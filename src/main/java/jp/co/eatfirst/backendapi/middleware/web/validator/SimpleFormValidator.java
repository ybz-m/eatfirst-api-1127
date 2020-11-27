package jp.co.eatfirst.backendapi.middleware.web.validator;

import java.lang.reflect.ParameterizedType;
import java.util.Locale;

import jp.co.eatfirst.backendapi.middleware.web.form.BaseApiForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import jp.co.eatfirst.backendapi.middleware.exception.ApiCheckErrorException;

/**
 * 業務チェックの親クラス
 *
 */
public abstract class SimpleFormValidator<T extends BaseApiForm> implements Validator {
    private static final String CORRELATION_CHECK_RESULT = "correlationCheckResult";
    Class<?> persistentClass;
    @Autowired
    MessageSource messageSource;

    public SimpleFormValidator() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return persistentClass.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object obj, Errors err) {
        if (!err.hasErrors()) {
            doValidate((T) obj, err);

        }
        if (err.hasErrors()) {
            throw new ApiCheckErrorException(err.getAllErrors());
        }
    }

    public abstract void doValidate(T form, Errors err);

    public void error(Errors err, String key, Object... errorArgs) {
        err.rejectValue(CORRELATION_CHECK_RESULT, key, errorArgs, toMessage(key, errorArgs));
    }

    public void error(Errors err, String key) {
        err.rejectValue(CORRELATION_CHECK_RESULT, key, null, toMessage(key, null));
    }

    private String toMessage(String key, Object... arrays) {
        return messageSource.getMessage(key, arrays, Locale.JAPANESE);
    }
}