package ir.bki.otpservice.exception;

import lombok.Getter;


import java.util.HashMap;
import java.util.Map;

/**
 * When request is not valid throw this exception
 * I extracted this class from JHispter
 */
@Getter
public class BadRequestAlertException extends RuntimeException {

    private static final long serialVersionUID = 1L;


    private String thrower;
    private String message;
    private final Long errorCode;
    private Long start;


    public BadRequestAlertException(String thrower, Long errorKey, String message, long start) {
        super("BadRequestAlertException");
        this.thrower = thrower;
        this.message = message;
        this.errorCode = errorKey;
        this.start = start;
    }

    private static Map<String, Object> getAlertParameters(String entityName, String errorKey) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("message", "error." + errorKey);
        parameters.put("params", entityName);
        return parameters;
    }
}
