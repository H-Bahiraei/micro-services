package ir.bki.otpservice.exception;

import ir.bki.otpservice.apects.Loggable;
import ir.bki.otpservice.model.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/27/22
 * <p>
 * /**
 * Controller advice to translate the server side exceptions to client-friendly json structures.
 * The error response follows RFC7807 - Problem Details for HTTP APIs (https://greenbytes.de/tech/webdav/rfc7807.html).
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    //------------- From JHipster------
    private static final String FIELD_ERRORS_KEY = "fieldErrors";
    private static final String MESSAGE_KEY = "message";
    private static final String PATH_KEY = "path";
    private static final String VIOLATIONS_KEY = "violations";
    private final Environment env;
    @Value("${spring.application.name}")
    private String applicationName;

    public GlobalExceptionHandler(Environment env) {
        this.env = env;
    }

    @Loggable
    @ExceptionHandler(value = {BadRequestAlertException.class})
    public ResponseEntity<ResponseDto> handleBadRequestAlertException(BadRequestAlertException ex) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setHttpStatus(HttpStatus.BAD_REQUEST.value());

        if (ex.getMessage() == null || ex.getMessage() == "")
            responseDto.setMessage("Bad request! Something wrong in client request.");
        else
            responseDto.setMessage(ex.getMessage());
        responseDto.setErrorCode(ex.getErrorCode());
        responseDto.setElapsedTime(System.currentTimeMillis() - ex.getStart());
        return ResponseEntity.status(responseDto.getHttpStatus()).body(responseDto);
    }

//    @ExceptionHandler
//    public ResponseEntity<ResponseDto> handleConcurrencyFailure(ConcurrencyFailureException ex, NativeWebRequest request) {
//        Problem problem = Problem.builder().withStatus(Status.CONFLICT).with(MESSAGE_KEY, ErrorConstants.ERR_CONCURRENCY_FAILURE).build();
//        return create(ex, problem, request);
//    }

    private boolean containsPackageName(String message) {
        // This list is for sure not complete
        return StringUtils.containsAny(message, "org.", "java.", "net.", "javax.", "com.", "io.", "de.", "com.example");
    }
}
