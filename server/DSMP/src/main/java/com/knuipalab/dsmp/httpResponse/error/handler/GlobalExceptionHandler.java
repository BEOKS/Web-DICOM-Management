package com.knuipalab.dsmp.httpResponse.error.handler;

import com.knuipalab.dsmp.httpResponse.error.ErrorCode;
import com.knuipalab.dsmp.httpResponse.error.ErrorResponse;
import com.knuipalab.dsmp.httpResponse.error.handler.exception.MetaDataNotFoundException;
import com.knuipalab.dsmp.httpResponse.error.handler.exception.ProjectNotFoundException;
import com.knuipalab.dsmp.httpResponse.error.handler.exception.UnAuthorizedAccessException;
import com.knuipalab.dsmp.httpResponse.error.handler.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
@RestController
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    //  ErrorCode 401 : Un Authorized ----------------------------------
    @ExceptionHandler(value = UnAuthorizedAccessException.class)
    protected ResponseEntity<ErrorResponse> handleUnAuthorizedAccessException() {
        log.error("handleUnAuthorizedAccessException throw Exception : {}", ErrorCode.UNAUTHORIZED_ACCESS);
        return ErrorResponse.toResponseEntity(ErrorCode.UNAUTHORIZED_ACCESS);
    }

    //  ErrorCode 404 : Not Found ----------------------------------

    @ExceptionHandler(value = UserNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleUserNotFoundException() {
        log.error("handleUserNotFoundException throw Exception : {}", ErrorCode.USER_NOT_FOUND);
        return ErrorResponse.toResponseEntity(ErrorCode.USER_NOT_FOUND);
    }

    @ExceptionHandler(value = MetaDataNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleMetaDataNotFoundException() {
        log.error("handleMetaDataNotFoundException throw Exception : {}", ErrorCode.METADATA_NOT_FOUND);
        return ErrorResponse.toResponseEntity(ErrorCode.METADATA_NOT_FOUND);
    }

    @ExceptionHandler(value = ProjectNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleProjectNotFoundException() {
        log.error("handleProjectNotFoundException throw Exception : {}", ErrorCode.PROJECT_NOT_FOUND);
        return ErrorResponse.toResponseEntity(ErrorCode.PROJECT_NOT_FOUND);
    }

    //  ErrorCode 500 : Internal Server error --------------------------
    @ExceptionHandler(value = NullPointerException.class)
    protected ResponseEntity<ErrorResponse> handleNullPointerException() {
        log.error("handleNullPointerException throw Exception : {}", ErrorCode.NULL_POINTER);
        return ErrorResponse.toResponseEntity(ErrorCode.NULL_POINTER);
    }



}