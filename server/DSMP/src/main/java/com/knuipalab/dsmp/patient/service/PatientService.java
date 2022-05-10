package com.knuipalab.dsmp.patient.service;

import com.knuipalab.dsmp.user.auth.dto.SessionUser;
import com.knuipalab.dsmp.patient.domain.Patient;
import com.knuipalab.dsmp.patient.domain.PatientRepository;
import com.knuipalab.dsmp.patient.dto.PatientResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private HttpSession httpSession;

    @Transactional(readOnly = true)
    public List<Patient> findByUserId(String userId){
        List<Patient> patientList = patientRepository.findByUserId(userId);
        return patientList;
    }

    @Transactional
    public void insert(Patient patient){
        patientRepository.save(patient);
    }

    @Transactional
    public void addProjectCount(String patientId){
        Patient patient = getPatient(patientId);
        patient.addReferencedCount();
        insert(patient);
    }

    @Transactional
    public void minusProjectCount(String patientId){
        Patient patient = getPatient(patientId);
        patient.minusReferencedCount();
        insert(patient);
    }

    @Transactional
    public Optional<Patient> findById(String patientId) {
        Optional<Patient> patient = patientRepository.findById(patientId);
        return patient;
    }

    @Transactional
    public void deleteById(String patientId){
        patientRepository.findById(patientId)
                .orElseThrow(()-> new IllegalArgumentException("해당 patientId값을 가진 환자 정보가 없습니다."));
        patientRepository.deleteById(patientId);
    }

    @Transactional
    public List<PatientResponseDto> findNonReferencedPatients(){
        int zeroCount = 0;
        SessionUser sessionUser = (SessionUser)httpSession.getAttribute("user");
        Query query = new Query(
                Criteria.where("userId").is(sessionUser.getUserId())
                .and("referencedCount").is(zeroCount));
        List<Patient> patientList = mongoTemplate.find(query,Patient.class,"patient");
        List<PatientResponseDto> patientResponseDtoList = new ArrayList<PatientResponseDto>();
        for(Patient patient : patientList){
            patientResponseDtoList.add(new PatientResponseDto(patient));
        }
        return patientResponseDtoList;
    }

    //없는 환자이면 객체 생성해서 반환하고, 있으면 projectCount Up 해서 patient 객체 반환
    public Patient getPatient(String patientId){
        SessionUser sessionUser = (SessionUser)httpSession.getAttribute("user");
        Patient patient;
        Optional<Patient> optionalPatient = findById(patientId);
        if(optionalPatient.isPresent()){
            patient = optionalPatient.get();
        } else {
            patient = new Patient().builder()
                    .patientId(patientId)
                    .userId(sessionUser.getUserId())
                    .build();
        }
        return patient;
    }

}
