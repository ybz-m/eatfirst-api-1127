package jp.co.eatfirst.backendapi.middleware.exception;

import java.text.MessageFormat;

public class SystemException extends RuntimeException {

    /** serialVersionUID */
    private static final long serialVersionUID = 7372983367982990333L;

    public SystemException(String message) {
        super(message);
    }

    public SystemException(String message, String... args) {
        super(format(message, args));
    }

    public SystemException(Throwable cause) {
        super(cause);
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public SystemException(String message, Throwable cause, String... args) {
        super(format(message, args), cause);
    }

    static String format(String message, String... args) {
        return new MessageFormat(message).format(args);
    }
}
