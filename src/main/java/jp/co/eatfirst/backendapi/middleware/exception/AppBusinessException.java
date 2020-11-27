package jp.co.eatfirst.backendapi.middleware.exception;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppBusinessException extends RuntimeException {

    /** serialVersionUID */
    private static final long serialVersionUID = 7372983367982990333L;

    Map<String, List<String>> msgHash;

    public AppBusinessException(Map<String, List<String>> msgHash) {
        this.msgHash = msgHash;
    }

    public AppBusinessException(String message) {
        this(createMessage(message));
    }

    public AppBusinessException(String message, String... args) {
        this(createMessage(message, args));
    }

    static Map<String, List<String>> createMessage(String message, String... args) {
        Map<String, List<String>> msgHash = new HashMap<String, List<String>>();

        List<String> paras = null;
        if (args != null) {
            paras = Arrays.asList(args);
        }
        msgHash.put(message, paras);

        return msgHash;
    }

    public Map<String, List<String>> getMsgHash() {
        return msgHash;
    }
}
