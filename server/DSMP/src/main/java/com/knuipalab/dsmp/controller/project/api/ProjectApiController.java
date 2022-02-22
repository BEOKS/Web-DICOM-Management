package com.knuipalab.dsmp.controller.project.api;

import com.knuipalab.dsmp.dto.project.*;
import com.knuipalab.dsmp.httpResponse.BasicResponse;
import com.knuipalab.dsmp.httpResponse.success.SuccessDataResponse;
import com.knuipalab.dsmp.httpResponse.success.SuccessResponse;
import com.knuipalab.dsmp.service.project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProjectApiController {

    @Autowired
    ProjectService projectService;

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

    // projectId를 기반으로 ProjectName을 수정
    @PutMapping("api/Project/{projectId}")
    public ResponseEntity<? extends  BasicResponse> update(@PathVariable String projectId , @RequestBody ProjectRequestDto projectRequestDto){
        projectService.update(projectId,projectRequestDto);
        return ResponseEntity.ok().body(new SuccessResponse());
    }

    // projectId를 기반으로 Project 삭제 -> 관련 metadata들을 cascade하게 삭제
    @DeleteMapping("api/Project/{projectId}")
    public ResponseEntity<? extends  BasicResponse> delete(@PathVariable String projectId){
        projectService.deleteById(projectId);
        return ResponseEntity.ok().body(new SuccessResponse());
    }

    //poject 초대
    @PutMapping("api/Project/{projectId}/invite")
    public ResponseEntity<? extends  BasicResponse> invite(@PathVariable String projectId ,@RequestBody ProjectInviteRequestDto projectInviteRequestDto){
        projectService.invite(projectId,projectInviteRequestDto);
        return ResponseEntity.ok().body(new SuccessResponse());
    }

    //poject 방문자 삭제 리스트
    @PutMapping ("api/Project/{projectId}/oust/list")
    public ResponseEntity<? extends  BasicResponse> oust(@PathVariable String projectId ,@RequestBody ProjectOustRequestDto projectOustRequestDto){
        projectService.oustByEmailList(projectId,projectOustRequestDto);
        return ResponseEntity.ok().body(new SuccessResponse());
    }

    //poject 방문자에서 본인 삭제
    @PutMapping ("api/Project/{projectId}/oust")
    public ResponseEntity<? extends BasicResponse> oust(@PathVariable String projectId){
        projectService.oust(projectId);
        return ResponseEntity.ok().body(new SuccessResponse());
    }


}
