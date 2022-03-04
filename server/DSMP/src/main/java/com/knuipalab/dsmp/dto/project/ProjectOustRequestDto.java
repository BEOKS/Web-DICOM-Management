package com.knuipalab.dsmp.dto.project;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
@Getter
@NoArgsConstructor
public class ProjectOustRequestDto {

    private List<String> emailList ;

    @Builder
    public ProjectOustRequestDto(List<String> emailList) {
        this.emailList = emailList;
    }
}
