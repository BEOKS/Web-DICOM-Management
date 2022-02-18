package com.knuipalab.dsmp.controller.project.api;

import com.knuipalab.dsmp.dto.project.ProjectInviteRequestDto;
import com.knuipalab.dsmp.dto.project.ProjectOustRequestDto;
import com.knuipalab.dsmp.dto.project.ProjectRequestDto;
import com.knuipalab.dsmp.dto.project.ProjectResponseDto;
import com.knuipalab.dsmp.service.project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProjectApiController {

    @Autowired
    ProjectService projectService;

    // DB에 존재하는 모든 프로젝트 종류를 반환
    @GetMapping("api/Project")
    public List<ProjectResponseDto> findByCreator(){
        return projectService.findByCreator();
    }



    // Project 생성
    @PostMapping("api/Project")
    public void insert(@RequestBody ProjectRequestDto projectRequestDto){
        projectService.insert(projectRequestDto);
    }

    // projectId를 기반으로 ProjectName을 수정
    @PutMapping("api/Project/{projectId}")
    public void update(@PathVariable String projectId , @RequestBody ProjectRequestDto projectRequestDto){
        projectService.update(projectId,projectRequestDto);
    }

    // projectId를 기반으로 Project 삭제 -> 관련 metadata들을 cascade하게 삭제
    @DeleteMapping("api/Project/{projectId}")
    public void delete(@PathVariable String projectId){
        projectService.deleteById(projectId);
    }

    //poject 초대
    @PutMapping("api/Project/{projectId}/invite")
    public void invite(@PathVariable String projectId ,@RequestBody ProjectInviteRequestDto projectInviteRequestDto){
        projectService.invite(projectId,projectInviteRequestDto);
    }

    //poject 방문자 삭제
    @PutMapping ("api/Project/{projectId}/oust")
    public void oust(@PathVariable String projectId ,@RequestBody ProjectOustRequestDto projectOustRequestDto){
        projectService.oust(projectId,projectOustRequestDto);
    }

}
