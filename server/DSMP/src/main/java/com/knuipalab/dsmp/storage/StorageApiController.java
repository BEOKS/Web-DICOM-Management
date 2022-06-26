package com.knuipalab.dsmp.storage;

import com.knuipalab.dsmp.http.httpResponse.BasicResponse;
import com.knuipalab.dsmp.http.httpResponse.success.SuccessDataResponse;
import com.knuipalab.dsmp.http.httpResponse.success.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class StorageApiController {

    private final StorageService storageService;

    /**
     * 해당 프로젝트가 저장한 파일들의 리스트를 전달한다.
     * @param projectId
     * @return
     */
    @GetMapping("api/Storage/{projectId}")
    public ResponseEntity< ? extends BasicResponse> getFileList(@PathVariable String projectId){
        List<String> files = storageService.getFileList(projectId);
        return ResponseEntity.ok().body(new SuccessDataResponse<List<String>>(files));
    }
    /**
     * 파일을 서버에 저장하는 기능
     * @param file MultipartFile 형식으로 전달받은 파일
     * @param projectId 파일을 업로드한 프로젝트 ID
     * @return 성공이나 에러에 따른 적절한 메시지를 출력한다. 여기에 담긴 메시지는 프론트에서 출력될 예정
     */
    @PostMapping("api/Storage/{projectId}")
    public ResponseEntity< ? extends BasicResponse> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable String projectId){
        storageService.uploadFile(projectId,file);
        return ResponseEntity.ok().body(new SuccessResponse());
    }

    /**
     * 파일을 다운로드 하는 기능
     * @param projectId 파일을 업로드한 프로젝트 ID
     * @param fileName 다운로드를 요청할 파일 이름
     * @return 전달하고자 하는 데이터
     */
    @GetMapping("api/Storage/{projectId}/{fileName}")
    public ResponseEntity< ? extends BasicResponse> serveFile(@PathVariable String projectId, @PathVariable String fileName , HttpServletRequest request , HttpServletResponse response){
        storageService.serveFile(projectId,fileName,request,response);
        return ResponseEntity.ok().body(new SuccessResponse());
    }

    /**
     * 프로젝트 폴더 내 모든 파일을 삭제하는 기능
     * @param projectId 삭제 파일들이 있는 프로젝트 ID
     * @return 성공이나 에러에 따른 적절한 메시지를 출력한다. 여기에 담긴 메시지는 프론트에서 출력될 예정
     */
    @DeleteMapping("api/Storage/{projectId}")
    public ResponseEntity< ? extends BasicResponse>deleteAllFile(@PathVariable String projectId){
        storageService.deleteAll(projectId);
        return ResponseEntity.ok().body(new SuccessResponse());
    }

    /**
     * 파일을 삭제하는 기능
     * @param projectId 삭제 파일이 있는 프로젝트 ID
     * @param fileName 삭제 요청할 파일 이름
     * @return 성공이나 에러에 따른 적절한 메시지를 출력한다. 여기에 담긴 메시지는 프론트에서 출력될 예정
     */
    @DeleteMapping("api/Storage/{projectId}/{fileName}")
    public ResponseEntity< ? extends BasicResponse>deleteFile(@PathVariable String projectId, @PathVariable String fileName){
        storageService.deleteByFileName(projectId,fileName);
        return ResponseEntity.ok().body(new SuccessResponse());
    }


}
