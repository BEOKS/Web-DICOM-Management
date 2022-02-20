package com.knuipalab.dsmp.httpResponse.error.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
@RestController
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

//    @ExceptionHandler(value = AuthenticationForbiddenException.class)
//    protected ResponseEntity<ErrorResponse> handleAuthenticationForbiddenException() {
//        log.error("handleAuthenticationForbiddenException throw Exception : {}", ErrorCode.FORBIDDEN_AUTHENTICATION);
//        return ErrorResponse.toResponseEntity(ErrorCode.FORBIDDEN_AUTHENTICATION);
//    }

}