package com.knuipalab.dsmp.orthanc

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.*
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.postForEntity
import org.springframework.web.multipart.MultipartFile
import java.io.IOException

/**
 *
 */
private fun ResponseEntity<String>.parse2Json(): JsonNode{
    val objectMapper=ObjectMapper()
    return try {
        objectMapper.readTree(this.body);
    } catch (e: Exception){
        System.err.println("Error parsing to Json format")
        objectMapper.readTree(this.toString())
    }
}

fun requestFindToOrthancWithQuery(idType : String, query : String,link : String= OrthancRestClient.URL.GET_TOOLS_ENDPOINT.LINK): JsonNode{
    val objectMapper=ObjectMapper()
    val restTemplate = RestTemplate()
    val param= mapOf<String,Any>(
        "Expand" to true,
        "Full" to true,
        "Level" to "Study",
        "Query" to objectMapper.readTree("{\"$idType\":\"$query\"}")
    )
    val header=HttpHeaders()
    header.contentType = MediaType.APPLICATION_JSON
    return restTemplate.postForEntity<String>(link,HttpEntity(param,header)).parse2Json();
}

class OrthancRestClient {
    private val restTemplate = RestTemplate()
    enum class URL(val LINK: String){
        GET_TOOLS_ENDPOINT("http://orthanc:8042/tools/find"),
        DELETE_PATIENT_ENDPOINT("http://orthanc:8042/patients/{id}"),
        UPLOAD_DICOM_ENDPOINT("http://orthanc:8042/instances"),
        DELETE_STUDY_ENDPOINT("http://orthanc:8042/studies/{id}"),
        GET_STUDY_ENDPOINT("http://orthanc:8042/studies")
    }

    @Throws(IOException::class)
    fun uploadDicom(@RequestPart("dicomfile") file: MultipartFile?): JsonNode{
        file?: throw IllegalArgumentException("Dicom file is null")
        val headers = HttpHeaders()
        headers["Content-Type"] = "application/dicom"
        val requestEntity = HttpEntity(file.bytes, headers)
        return restTemplate.postForEntity<String>(URL.UPLOAD_DICOM_ENDPOINT.LINK, requestEntity)
                .parse2Json()
    }

    @Throws(IOException::class)
    fun getStudyInfo(studyID : String):JsonNode{
        return requestFindToOrthancWithQuery("StudyInstanceUID",studyID)
    }

    @Throws(IOException::class)
    fun getPatientInfo(patientID: String): JsonNode{
        return requestFindToOrthancWithQuery("PatientID",patientID)
    }

    @Throws(IOException::class)
    fun getStudies(): JsonNode{
        return restTemplate.getForEntity<String>(URL.GET_STUDY_ENDPOINT.LINK,String::class.java)
                .parse2Json();
    }

    @Throws(IOException::class)
    fun deletePatient(patientUUID: String?): ResponseEntity<*>{
        patientUUID ?: return ResponseEntity<Any?>(HttpStatus.BAD_REQUEST);
        restTemplate.delete(URL.DELETE_PATIENT_ENDPOINT.LINK, mapOf<String,String>("id" to patientUUID));
        return ResponseEntity<Any?>(HttpStatus.OK)
    }

    @Throws(IOException::class)
    fun deleteStudy(studyID: String?): ResponseEntity<*>{
        studyID ?: return ResponseEntity<Any?>(HttpStatus.BAD_REQUEST);
        restTemplate.delete(URL.DELETE_STUDY_ENDPOINT.LINK, mapOf<String,String>("id" to studyID))
        return ResponseEntity<Any?>(HttpStatus.OK)
    }
}
