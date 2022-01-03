package com.knuipalab.dsmp.service.metadata;
import com.knuipalab.dsmp.domain.metadata.MetaData;
import com.knuipalab.dsmp.domain.metadata.MetaDataRepository;
import com.knuipalab.dsmp.dto.metadata.MetaDataResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service //IoC 대상이다.
public class MetaDataService {

    @Autowired
    private MetaDataRepository metaDataRepository;

    @Transactional (readOnly = true)
    public List<MetaDataResponseDto> findAll_MetaData(){

        List <MetaDataResponseDto> metaData_Response_Dto_list = new ArrayList<MetaDataResponseDto>();
        List <MetaData> metaData_list = metaDataRepository.findAll();

        for(MetaData metaData: metaData_list){ // metaDataRepository로 MeataData 정보 받아와서 Dto로 전환 -> 접근성 제한 목적
            metaData_Response_Dto_list.add(new MetaDataResponseDto(metaData));
        }

        return metaData_Response_Dto_list;
    }
}
