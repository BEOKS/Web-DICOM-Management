package com.knuipalab.dsmp.service.orthanc

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.*
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.postForEntity
import org.springframework.web.multipart.MultipartFile
import java.util.*

class OrthancRestClient {
    private val GET_TOOLS_ENDPOINT_URL = "http://orthanc:8042/tools/find"
    private val DELETE_PATIENT_ENDPOINT_URL = "http://orthanc:8042/patients/{id}"
    private val UPLOAD_DICOM_ENDPOINT_URL = "http://orthanc:8042/instances"
    private val DELETE_STUDY_ENDPOINT_URL = "http://orthanc:8042/studies/{id}"
    private val GET_STUDY_ENDPOINT_URL = "http://orthanc:8042/studies"

    private val restTemplate = RestTemplate()
    private val objectMapper=ObjectMapper()

    private fun ResponseEntity<String>.parse2Json(): JsonNode{
        return try {
            objectMapper.readTree(this.body);
        } catch (e: Exception){
            System.err.println("Error parsing to Json format")
            objectMapper.readTree(this.toString())
        }
    }

    fun uploadDicom(@RequestPart("dicomfile") file: MultipartFile?): JsonNode{
        file?: throw IllegalArgumentException("Dicom file is null")
        val headers = HttpHeaders()
        headers["Content-Type"] = "application/dicom"
        val requestEntity = HttpEntity(file.bytes, headers)
        return restTemplate.postForEntity<String>(UPLOAD_DICOM_ENDPOINT_URL, requestEntity).parse2Json()
    }

    fun getStudyInfo(studyID : String):JsonNode{
        fun createRequestEntity():HttpEntity<Map<String, Any>>{
//            val param = HashMap<String, Any>()
            val query = objectMapper.readTree("{\"StudyInstanceUID\":\"$studyID\"}")
//            param.put("Expand", true)
//            param.put("Full", true)
//            param.put("Level", "Study")
//            param.put("Query", objectMapper.readTree("{\"StudyInstanceUID\":\"$studyID\"}"))
            // header setting

            // header setting
            val headers = HttpHeaders()
            headers.contentType = MediaType.APPLICATION_JSON
            // post api call
            return HttpEntity<Map<String, Any>>(param, headers)
        }
        fun HttpEntity<Map<String, Any>>.postEntity(): JsonNode{

        }
        // post api call
        
        val requestEntity = HttpEntity<Map<String, Any>>(param, headers)
        return restTemplate.postForEntity<String>(GET_TOOLS_ENDPOINT_URL,requestEntity).parse2Json();
    }
}