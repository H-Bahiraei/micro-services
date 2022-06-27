package ir.bki.otpservice.exception;

import ir.bki.otpservice.apects.Loggable;
import ir.bki.otpservice.repository.model.ResponseDto;
import ir.bki.otpservice.service.ELS.ResponseDtoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static ir.bki.otpservice.exception.ErrorCodeConstants.Invalid_Body;
import static ir.bki.otpservice.exception.ErrorCodeConstants.Invalid_Constraints;

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
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    //------------- From JHipster------
    private static final String FIELD_ERRORS_KEY = "fieldErrors";
    private static final String MESSAGE_KEY = "message";
    private static final String PATH_KEY = "path";
    private static final String VIOLATIONS_KEY = "violations";
    private final Environment env;
    private final HttpServletRequest httpServletRequest;
    @Value("${spring.application.name}")
    private String applicationName;
    @Autowired
    private ResponseDtoService responseDtoService;

    public GlobalExceptionHandler(Environment env, HttpServletRequest httpServletRequest) {
        this.env = env;
        this.httpServletRequest = httpServletRequest;
    }


    @Loggable
    @ExceptionHandler(value = {BadRequestAlertException.class})
    public ResponseEntity<ResponseDto> handleBadRequestAlertException(BadRequestAlertException ex) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setHttpStatus(HttpStatus.BAD_REQUEST.value());
        responseDto.setTime(LocalDateTime.now() + "");
        if (ex.getMessage() == null || ex.getMessage() == "")
            responseDto.setMessage("Bad request! Something wrong in client request.");
        else
            responseDto.setMessage(ex.getMessage());

        responseDto.setStatus(ex.getStatusCode());
        String path = httpServletRequest.getMethod() + " "
                + httpServletRequest.getServletPath();
        responseDto.setPath(path);

        Map reqParams = new HashMap<String, String>();

        if (httpServletRequest.getHeader("Authorization") != null)
            reqParams.put("Authorization", httpServletRequest.getHeader("Authorization"));

        if (httpServletRequest.getHeader("Pair-Data") != null)
            reqParams.put("Pair-Data", httpServletRequest.getHeader("Pair-Data"));

        if (httpServletRequest.getHeader("Otp-Length") != null)
            reqParams.put("Otp-Length", httpServletRequest.getHeader("Otp-Length"));

        if (httpServletRequest.getHeader("Timeout") != null)
            reqParams.put("Timeout", httpServletRequest.getHeader("Timeout"));

        if (httpServletRequest.getHeader("Hash-Key") != null)
            reqParams.put("Hash-Key", httpServletRequest.getHeader("Hash-Key"));

        if (httpServletRequest.getHeader("Code") != null)
            reqParams.put("Code", httpServletRequest.getHeader("Code"));

        responseDto.setReqParams(reqParams);

        responseDto.setElapsedTime(System.currentTimeMillis() - ex.getStart());
        responseDtoService.createRdtoIndex(responseDto);
        return ResponseEntity.status(responseDto.getHttpStatus()).body(responseDto);
    }


    @Loggable
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        System.out.println("Handling method argument not valid exception");
        ResponseDto responseDto = new ResponseDto();
        responseDto.setHttpStatus(HttpStatus.BAD_REQUEST.value());
        responseDto.setTime(LocalDateTime.now() + "");
        responseDto.setMessage(ex.getMessage());
        responseDto.setStatus(Invalid_Body);
//        responseDto.setElapsedTime(System.currentTimeMillis() - ex.getStart()); //TODO Mehrad
        String path = httpServletRequest.getMethod() + " "
                + httpServletRequest.getServletPath();
        responseDto.setPath(path);

        Map reqParams = new HashMap<String, String>();

        if (httpServletRequest.getHeader("Authorization") != null)
            reqParams.put("Authorization", httpServletRequest.getHeader("Authorization"));

        if (httpServletRequest.getHeader("Pair-Data") != null)
            reqParams.put("Pair-Data", httpServletRequest.getHeader("Pair-Data"));

        if (httpServletRequest.getHeader("Otp-Length") != null)
            reqParams.put("Otp-Length", httpServletRequest.getHeader("Otp-Length"));

        if (httpServletRequest.getHeader("Timeout") != null)
            reqParams.put("Timeout", httpServletRequest.getHeader("Timeout"));

        if (httpServletRequest.getHeader("Hash-Key") != null)
            reqParams.put("Hash-Key", httpServletRequest.getHeader("Hash-Key"));

        if (httpServletRequest.getHeader("Code") != null)
            reqParams.put("Code", httpServletRequest.getHeader("Code"));

        responseDto.setReqParams(reqParams);


        responseDtoService.createRdtoIndex(responseDto);

        return ResponseEntity.status(responseDto.getHttpStatus()).body(responseDto);
    }


    @Loggable
    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setHttpStatus(HttpStatus.BAD_REQUEST.value());
        responseDto.setTime(LocalDateTime.now() + "");
        responseDto.setMessage(e.getMessage());
        responseDto.setStatus(Invalid_Constraints);
//        responseDto.setElapsedTime(System.currentTimeMillis() - ex.getStart()); //TODO Mehrad
        String path = httpServletRequest.getMethod() + " "
                + httpServletRequest.getServletPath();
        responseDto.setPath(path);

        Map reqParams = new HashMap<String, String>();

        if (httpServletRequest.getHeader("Authorization") != null)
            reqParams.put("Authorization", httpServletRequest.getHeader("Authorization"));

        if (httpServletRequest.getHeader("Pair-Data") != null)
            reqParams.put("Pair-Data", httpServletRequest.getHeader("Pair-Data"));

        if (httpServletRequest.getHeader("Otp-Length") != null)
            reqParams.put("Otp-Length", httpServletRequest.getHeader("Otp-Length"));

        if (httpServletRequest.getHeader("Timeout") != null)
            reqParams.put("Timeout", httpServletRequest.getHeader("Timeout"));

        if (httpServletRequest.getHeader("Hash-Key") != null)
            reqParams.put("Hash-Key", httpServletRequest.getHeader("Hash-Key"));

        if (httpServletRequest.getHeader("Code") != null)
            reqParams.put("Code", httpServletRequest.getHeader("Code"));

        responseDto.setReqParams(reqParams);

        responseDtoService.createRdtoIndex(responseDto);

        return ResponseEntity.status(responseDto.getHttpStatus()).body(responseDto);
    }


//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
//                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
//        System.out.println("HHHHHHandling method argument not valid exception");
//        ResponseDto responseDto = new ResponseDto();
//        responseDto.setMessage(ex.getMessage());
//        return ResponseEntity.status(responseDto.getHttpStatus()).body(responseDto);
//    }

//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(
//            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        System.out.println("ex = " + ex + ", hhhhhhhhhhhhhhhhhhhhhhhheaders = ");
//        ResponseDto responseDto = new ResponseDto();
//        responseDto.setMessage(ex.getMessage());
//        return ResponseEntity.status(responseDto.getHttpStatus()).body(responseDto);
//
//    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ResponseDto> handleMethodArgumentNotValid(
//            MethodArgumentNotValidException ex) {
//        System.out.println("ex = " + ex + ", hhhhhhhhhhhhhhhhhhhhhhhheaders = ");
//        ResponseDto responseDto = new ResponseDto();
//        responseDto.setMessage(ex.getMessage());
//        return ResponseEntity.status(responseDto.getHttpStatus()).body(responseDto);
////                "Validation error. Check 'errors' field for details."
//    }


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
