package com.knuipalab.dsmp.controller.patient.api;

import com.knuipalab.dsmp.dto.patient.PatientResponseDto;
import com.knuipalab.dsmp.httpResponse.BasicResponse;
import com.knuipalab.dsmp.httpResponse.success.SuccessDataResponse;
import com.knuipalab.dsmp.httpResponse.success.SuccessResponse;
import com.knuipalab.dsmp.service.patient.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PatientApiController {

    @Autowired
    PatientService patientService;

    @GetMapping("api/Patient/nonReferenced")
    public ResponseEntity<? extends BasicResponse> findNonReferencedPatients(){
        List<PatientResponseDto> patientResponseDtos = patientService.findNonReferencedPatients();
        return ResponseEntity.ok().body(new SuccessDataResponse<List<PatientResponseDto>>(patientResponseDtos));
    }

    @DeleteMapping("api/Patient/nonReferenced/{patientId}")
    public ResponseEntity<? extends BasicResponse> deleteById(@PathVariable String patientId){
        patientService.deleteById(patientId);
        return ResponseEntity.ok().body(new SuccessResponse());
    }

}
