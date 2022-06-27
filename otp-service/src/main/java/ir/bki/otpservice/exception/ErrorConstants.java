package ir.bki.otpservice.exception;

import java.net.URI;

/**
 * @author H-Bahiraei
 * Created on 6/27/2022
 */
public final class ErrorConstants {

    public static final String ERR_CONCURRENCY_FAILURE = "error.concurrencyFailure";
    public static final String ERR_VALIDATION = "error.validation";
    public static final String PROBLEM_BASE_URL = "https://github.com/ma-sharifi/minibankc";
    public static final URI DEFAULT_TYPE = URI.create(PROBLEM_BASE_URL + "/problem-with-message");
    public static final URI CONSTRAINT_VIOLATION_TYPE = URI.create(PROBLEM_BASE_URL + "/constraint-violation");

    private ErrorConstants() {}
}
