package com.knuipalab.dsmp.httpResponse.error.handler.exception;

import com.knuipalab.dsmp.httpResponse.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UnAuthorizedAccessException extends RuntimeException{
    private final ErrorCode errorCode;
}
