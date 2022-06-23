package com.knuipalab.dsmp.http.httpResponse.success;

import com.knuipalab.dsmp.http.httpResponse.BasicResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class SuccessDataResponse<T> extends BasicResponse {

    private LocalDateTime timestamp ;

    private int count; // 바디 값이 리스트인 경우 리스트 개수

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