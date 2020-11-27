package jp.co.eatfirst.backendapi.middleware.exception;

import java.text.MessageFormat;

public class BusinessException extends RuntimeException {

    /** serialVersionUID */
    private static final long serialVersionUID = 7372983367982990333L;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, String... args) {
        super(format(message, args));
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(String message, Throwable cause, String... args) {
        super(format(message, args), cause);
    }

    static String format(String message, String... args) {
        return new MessageFormat(message).format(args);
    }
}
