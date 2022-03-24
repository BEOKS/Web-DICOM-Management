package com.knuipalab.dsmp.service.machineLearning;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum MLType {

    TRAIN("train"),VALID("valid"),TEST("test");

    private final String typeString;

    MLType(String typeString){
        this.typeString = typeString;
    }

}
