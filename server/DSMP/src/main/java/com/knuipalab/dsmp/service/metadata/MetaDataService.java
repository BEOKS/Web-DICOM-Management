package com.knuipalab.dsmp.service.metadata;
import com.knuipalab.dsmp.domain.metadata.US_MetaData;
import com.knuipalab.dsmp.domain.metadata.US_MetaDataRepository;
import com.knuipalab.dsmp.dto.metadata.US_MetaDataResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service //IoC 대상이다.
public class MetaDataService {

    @Autowired
    private US_MetaDataRepository USMetaDataRepository;

    @Transactional (readOnly = true)
    public List<US_MetaDataResponseDto> findAll_US_MetatData(){

        List <US_MetaDataResponseDto> US_MetaData_Response_Dto_list = new ArrayList<US_MetaDataResponseDto>();
        List <US_MetaData> US_Metadata_list = USMetaDataRepository.findAll();

        for(US_MetaData us_metaData: US_Metadata_list){ // metaDataRepository로 MeataData 정보 받아와서 Dto로 전환 -> 접근성 제한 목적
            US_MetaData_Response_Dto_list.add(new US_MetaDataResponseDto(us_metaData));
        }

        return US_MetaData_Response_Dto_list;
    }
}
