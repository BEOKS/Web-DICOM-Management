package com.knuipalab.dsmp.httpResponse.error;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Getter
@Builder
public class ErrorDataResponse {

    private final LocalDateTime timestamp;
    private final String path;
    private final int status;
    private final String error;
    private final String code;
    private final String message;
    private final List<Object> failList;

    public static ResponseEntity<ErrorDataResponse> toResponseEntity(ErrorCode errorCode, HttpServletRequest request) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorDataResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .path(request.getRequestURI())
                        .status(errorCode.getHttpStatus().value())
                        .error(errorCode.getHttpStatus().name())
                        .code(errorCode.name())
                        .message(errorCode.getDetail())
                        .failList(errorCode.getFailList())
                        .build()
                );
    }
}