package jp.co.eatfirst.backendapi.middleware.frequentlycheck;

import org.springframework.validation.ObjectError;

import java.util.List;

public class FrequentlyCheckException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public FrequentlyCheckException(String message, RuntimeException e) {
        super(message, e);
    }

    public FrequentlyCheckException() {
        super();
    }
}
