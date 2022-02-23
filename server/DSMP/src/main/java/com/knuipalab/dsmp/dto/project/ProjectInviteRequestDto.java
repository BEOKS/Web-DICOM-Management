package com.knuipalab.dsmp.dto.project;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ProjectInviteRequestDto {
    private List<String> emailList ;
}