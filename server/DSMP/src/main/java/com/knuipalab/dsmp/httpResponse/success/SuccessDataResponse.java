package com.knuipalab.dsmp.httpResponse.success;

import com.knuipalab.dsmp.httpResponse.BasicResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class SuccessDataResponse<T> extends BasicResponse {

    private LocalDateTime timestamp ;

    private int count;

    private int status;

    private T body;

    public SuccessDataResponse(T body) {
        this.timestamp =  LocalDateTime.now();
        this.body = body;
        this.status = HttpStatus.OK.value();
        if(body instanceof List) {
            this.count = ((List<?>)body).size();
        } else {
            this.count = 1;
        }
    }
}