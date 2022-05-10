package com.knuipalab.dsmp.project.controller;

import com.knuipalab.dsmp.project.dto.ProjectInviteRequestDto;
import com.knuipalab.dsmp.project.dto.ProjectOustRequestDto;
import com.knuipalab.dsmp.project.dto.ProjectRequestDto;
import com.knuipalab.dsmp.project.dto.ProjectResponseDto;
import com.knuipalab.dsmp.http.httpResponse.BasicResponse;
import com.knuipalab.dsmp.http.httpResponse.success.SuccessDataResponse;
import com.knuipalab.dsmp.http.httpResponse.success.SuccessResponse;
import com.knuipalab.dsmp.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProjectApiController {


    private final ProjectService projectService;

    // DB에 존재하는 모든 프로젝트 종류를 반환
    @GetMapping("api/Project")
    public ResponseEntity<? extends  BasicResponse> findByCreator(){
        List<ProjectResponseDto> projectResponseDtos = projectService.findByCreator();
        return ResponseEntity.ok().body(new SuccessDataResponse<List<ProjectResponseDto>>(projectResponseDtos));
    }

    @GetMapping("api/Project/invited")
    public ResponseEntity<? extends  BasicResponse> findInvisitedProject(){
        List<ProjectResponseDto> projectResponseDtos = projectService.findInvitedProject();
        return ResponseEntity.ok().body(new SuccessDataResponse<List<ProjectResponseDto>>(projectResponseDtos));
    }

    // Project 생성
    @PostMapping("api/Project")
    public ResponseEntity<? extends  BasicResponse> insert(@RequestBody ProjectRequestDto projectRequestDto){
        projectService.insert(projectRequestDto);
        return ResponseEntity.ok().body(new SuccessResponse());
    }

    // projectId를 기반으로 project 정보 및 생성자 참여자 정보
    @GetMapping("api/Project/{projectId}")
    public ResponseEntity<? extends  BasicResponse> findById(@PathVariable String projectId) {
        ProjectResponseDto projectResponseDtos = projectService.findById(projectId);
        return ResponseEntity.ok().body(new SuccessDataResponse<ProjectResponseDto>(projectResponseDtos));
    }

    // projectId를 기반으로 ProjectName을 수정
    @PutMapping("api/Project/{projectId}")
    public ResponseEntity<? extends  BasicResponse> update(@PathVariable String projectId , @RequestBody ProjectRequestDto projectRequestDto){
        projectService.update(projectId,projectRequestDto);
        return ResponseEntity.ok().body(new SuccessResponse());
    }

    // projectId를 기반으로 Project 삭제 -> 관련 metadata들, 이미지 파일들을 cascade하게 삭제
    @DeleteMapping("api/Project/{projectId}")
    public ResponseEntity<? extends  BasicResponse> delete(@PathVariable String projectId){
        projectService.deleteById(projectId);
        return ResponseEntity.ok().body(new SuccessResponse());
    }

    //poject 초대
    @PutMapping("api/Project/invite/{projectId}")
    public ResponseEntity<? extends  BasicResponse> invite(@PathVariable String projectId ,@RequestBody List<String> emailList){
        ProjectInviteRequestDto projectInviteRequestDto = new ProjectInviteRequestDto(projectId,emailList);
        projectService.invite(projectInviteRequestDto);
        return ResponseEntity.ok().body(new SuccessResponse());
    }

    //poject 방문자 삭제 리스트
    @PutMapping ("api/Project/oust/list/{projectId}")
    public ResponseEntity<? extends  BasicResponse> oust(@PathVariable String projectId ,@RequestBody List<String> emailList){
        ProjectOustRequestDto projectOustRequestDto = new ProjectOustRequestDto(projectId,emailList);
        projectService.oustByEmailList(projectOustRequestDto);
        return ResponseEntity.ok().body(new SuccessResponse());
    }

    //poject 방문자에서 본인 삭제
    @PutMapping ("api/Project/oust/{projectId}")
    public ResponseEntity<? extends BasicResponse> oust(@PathVariable String projectId){
        projectService.oust(projectId);
        return ResponseEntity.ok().body(new SuccessResponse());
    }


}
