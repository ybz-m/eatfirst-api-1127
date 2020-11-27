package jp.co.eatfirst.backendapi.middleware.exception;

public class BusinessCheckErrorException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    String code;

    public BusinessCheckErrorException(String code) {
        this.code = code;
    }
    public String getCode(){
        return code;
    }

    public BusinessCheckErrorException() {
        super();
    }
}
