package com.knuipalab.dsmp.service.posts;

import com.knuipalab.dsmp.dicom.dicommeta.DicomRepository;
import com.knuipalab.dsmp.web.dto.DicomSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DicomService {
    private final DicomRepository dicomRepository;
    public String save(DicomSaveRequestDto requestDto) {
        return dicomRepository.save(requestDto.toEntity()).getId();
    }
}
