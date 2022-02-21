package com.knuipalab.dsmp.httpResponse.error.handler.exception;

import com.knuipalab.dsmp.httpResponse.error.ErrorCode;
import lombok.Getter;

@Getter
public class UserEmailBadRequestException extends RuntimeException{

    private ErrorCode errorCode;

    public UserEmailBadRequestException(ErrorCode errorCode, String formattedString){
        this.errorCode = errorCode;
        this.errorCode.setFormattedString(formattedString);
    }
}
