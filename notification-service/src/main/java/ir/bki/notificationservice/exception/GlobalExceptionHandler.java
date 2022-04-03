package ir.bki.notificationservice.exception;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/3/2022
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @Value("${data.exception.message1}")
    private String message1;
    @Value(value = "${data.exception.message2}")
    private String message2;
    @Value(value = "${data.exception.message3}")
    private String message3;
    @Value("${data.exception.notfound}")
    private String messageNotfound;
//
//    @ExceptionHandler(NotFoundException.class)
//    public ResponseEntity<?> throwNotfoundEception(NotFoundException exception){
//        return ResponseEntity.status(exception.getHttpStatus()).body(messageNotfound);
//    }
//
//    @ExceptionHandler(value = BlogNotFoundException.class)
//    public ResponseEntity blogNotFoundException(BlogNotFoundException blogNotFoundException) {
//        System.out.println("blogNotFoundException = " + blogNotFoundException);
////        return new ResponseEntity(message2, HttpStatus.NOT_FOUND);
//        return new ResponseEntity(blogNotFoundException.getMessage(),blogNotFoundException.getHttpStatus());
//    }
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> databaseConnectionFailsException(Exception exception) {
        return new ResponseEntity<>(message3, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
