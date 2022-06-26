package com.knuipalab.dsmp.http.httpResponse.error.handler.exception;

import com.knuipalab.dsmp.http.httpResponse.error.ErrorCode;
import lombok.Getter;

import java.util.List;

@Getter
public class UserEmailBadRequestException extends RuntimeException{

    private ErrorCode errorCode;

    public UserEmailBadRequestException(ErrorCode errorCode, List<Object> failList){
        this.errorCode = errorCode;
        this.errorCode.setFailList(failList);
    }
}
