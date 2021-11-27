package com.knuipalab.dsmp.web;

import com.knuipalab.dsmp.service.posts.DicomService;
import com.knuipalab.dsmp.web.dto.DicomSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class DicomAPIController {
    private final DicomService dicomService;
    @PostMapping("/api/v1/dicom")
    public String save(@RequestBody DicomSaveRequestDto requestDto){
        return dicomService.save(requestDto);
    }
}
