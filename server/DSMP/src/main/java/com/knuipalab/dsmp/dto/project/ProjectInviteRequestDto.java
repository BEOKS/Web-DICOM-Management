package com.knuipalab.dsmp.dto.project;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ProjectInviteRequestDto {

    private String projectId;
    private List<String> emailList ;

    @Builder
    public ProjectInviteRequestDto(String projectId,List<String> emailList) {
        this.projectId = projectId;
        this.emailList = emailList;
    }
}