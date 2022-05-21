package ir.bki.otpservice.apects;

import ir.bki.otpservice.util.HeaderUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 5/1/22
 * Handle log request and response of request + headers when put Loggable on the method.
 */

@Aspect //let know Spring that this is an Aspect class
@Component //Spring will consider this class as a Spring bean
@Order(0)
@Slf4j
public class LoggableAspectHandler {

    private final HttpServletRequest httpServletRequest;
    private final HttpServletResponse httpServletResponse;

    public LoggableAspectHandler(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        this.httpServletRequest = httpServletRequest;
        this.httpServletResponse = httpServletResponse;
    }

    @Around("@annotation(Loggable)") //define the logic to execute
    public Object trace(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        String classMethodArgs = className + "." + methodName;

        Object[] args= joinPoint.getArgs();

        for(Object arg : args){
            classMethodArgs =classMethodArgs + " , " + arg.toString();
        }

        Map<String, String> mapRequest = HeaderUtil.getHeadersInfo(httpServletRequest);

        String rest = httpServletRequest.getMethod() + " " + httpServletRequest.getServletPath();

        String clientIp = HeaderUtil.getRequestClientIpComplete(httpServletRequest);
        Object result = joinPoint.proceed();

        Map<String, String> mapResponse = HeaderUtil.getHeadersInfo(httpServletResponse);

        // Error for Ex and why debug?

        log.info("["
                +" #time: " + LocalDateTime.now() // get time
                +" "+ String.format("%-6d", (System.currentTimeMillis() - startTime)) + " ms] "
                + " ;#IP: " + clientIp
                + " ;#rest: " + rest
                + " ;#classMethodArgs: " + String.format("%-50s", classMethodArgs)
                + " ;#result: " + result
                + " ;#headers.request: " + mapRequest
                + " ;#header.response: " + mapResponse);

        return result;
    }

}
