package com.knuipalab.dsmp.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import java.time.LocalDateTime;

@Getter
@EnableMongoAuditing
public abstract class BaseTimeEntitiy {
    @CreatedDate
    private LocalDateTime localTime;
    @LastModifiedDate
    private LocalDateTime modifiedTime;
}
