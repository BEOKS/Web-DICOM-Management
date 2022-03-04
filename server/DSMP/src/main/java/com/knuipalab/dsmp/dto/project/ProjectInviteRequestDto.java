package com.knuipalab.dsmp.dto.project;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ProjectInviteRequestDto {

    private List<String> emailList ;

    @Builder
    public ProjectInviteRequestDto(List<String> emailList) {
        this.emailList = emailList;
    }
}