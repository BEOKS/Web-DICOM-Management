package com.knuipalab.dsmp.controller.patient.api;

import com.knuipalab.dsmp.dto.patient.PatientResponseDto;
import com.knuipalab.dsmp.service.patient.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<PatientResponseDto> findNonReferencedPatients(){
        return patientService.findNonReferencedPatients();
    }

    @DeleteMapping("api/Patient/nonReferenced/{patientId}")
    public void deleteById(@PathVariable String patientId){
        patientService.deleteById(patientId);
    }

}
