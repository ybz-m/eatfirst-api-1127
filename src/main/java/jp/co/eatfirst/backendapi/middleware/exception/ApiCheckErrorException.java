package jp.co.eatfirst.backendapi.middleware.exception;

import java.util.List;

import org.springframework.validation.ObjectError;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ApiCheckErrorException extends RuntimeException {
    List<ObjectError> errors;
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public ApiCheckErrorException(String message, RuntimeException e) {
        super(message, e);
    }

    public ApiCheckErrorException(List<ObjectError> messages) {
        super();
        errors = messages;
    }
}
