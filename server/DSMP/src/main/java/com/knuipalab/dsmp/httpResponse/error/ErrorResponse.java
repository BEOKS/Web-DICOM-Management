package com.knuipalab.dsmp.httpResponse.error;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Builder
public class ErrorResponse {
    private final LocalDateTime timestamp;
    private final String path;
    private final int status;
    private final String error;
    private final String code;
    private final String message;

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode, HttpServletRequest request) {
        Optional<String> formattedString = Optional.ofNullable(errorCode.getFormattedString());
        String errorMessage;
        if(formattedString.isPresent()){
            errorMessage = String.format(errorCode.getDetail(),errorCode.getFormattedString());
        } else {
            errorMessage = errorCode.getDetail();
        }
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .path(request.getRequestURI())
                        .status(errorCode.getHttpStatus().value())
                        .error(errorCode.getHttpStatus().name())
                        .code(errorCode.name())
                        .message(errorMessage)
                        .build()
                );
    }
}