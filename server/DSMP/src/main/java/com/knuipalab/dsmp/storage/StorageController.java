package com.knuipalab.dsmp.storage;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StorageController {
    /**
     * 해당 프로젝트가 저장한 파일들의 리스트를 전달한다.
     * @param projectId
     * @return
     */
    @GetMapping("/Storage/{projectId}")
    public ResponseEntity<List<String>> listFiles(
            @PathVariable String projectId);
    /**
     * 파일을 서버에 저장하는 기능
     * @param file MultipartFile 형식으로 전달받은 파일
     * @param projectId 파일을 업로드한 프로젝트 ID
     * @return 성공이나 에러에 따른 적절한 메시지를 출력한다. 여기에 담긴 메시지는 프론트에서 출력될 예정
     */
    @GetMapping("/Storage/{projectId}")
    public ResponseEntity<String> uploadFile(
            @RequestParam("file")MultipartFile file,
            @PathVariable String projectId);

    /**
     * 파일을 다운로드 하는 기능
     * @param projectId 파일을 업로드한 프로젝트 ID
     * @param filename 다운로드를 요청할 파일 이름
     * @return 전달하고자 하는 데이터
     */
    @PostMapping("/Storage/{projectId}/{filename}")
    public ResponseEntity<Resource> serveFile(
            @PathVariable String projectId,
            @PathVariable String filename);

    /**
     * 파일을 삭제하는 기능
     * @param projectId 파일을 업로드한 프로젝트 ID
     * @param filename 다운로드를 요청할 파일 이름
     * @return 성공이나 에러에 따른 적절한 메시지를 출력한다. 여기에 담긴 메시지는 프론트에서 출력될 예정
     */
    @DeleteMapping("/Storage/{projectId}/{filename}")
    public ResponseEntity<String> deleteFile(
            @PathVariable String projectId,
            @PathVariable String filename);
}
