package com.knuipalab.dsmp.project.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
@Getter
@NoArgsConstructor
public class ProjectOustRequestDto {

    private String projectId;
    private List<String> emailList ;

    @Builder
    public ProjectOustRequestDto(String projectId,List<String> emailList) {
        this.projectId = projectId;
        this.emailList = emailList;
    }
}
