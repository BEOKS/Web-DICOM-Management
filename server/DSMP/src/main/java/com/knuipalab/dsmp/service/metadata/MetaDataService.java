package com.knuipalab.dsmp.service.metadata;
import com.knuipalab.dsmp.domain.metadata.MetaData;
import com.knuipalab.dsmp.domain.metadata.MetaDataRepository;
import com.knuipalab.dsmp.dto.metadata.MetaDataResponseDto;
import com.knuipalab.dsmp.dto.metadata.MetaDataRequestDto;

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
    public List<MetaDataResponseDto> findAll(){

        List <MetaDataResponseDto> metaData_Response_Dto_list = new ArrayList<MetaDataResponseDto>();
        List <MetaData> metaData_list = metaDataRepository.findAll();

        for(MetaData metaData: metaData_list){ // metaDataRepository로 MeataData 정보 받아와서 Dto로 전환 -> 접근성 제한 목적
            metaData_Response_Dto_list.add(new MetaDataResponseDto(metaData));
        }

        return metaData_Response_Dto_list;
    }

    @Transactional (readOnly = true)
    public MetaDataResponseDto findById(String id) {

        MetaData metaData = metaDataRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("해당 Id값을 가진 데이터 베이스 정보가 없습니다."));

        return new MetaDataResponseDto(metaData);
    }

    @Transactional
    public void insert(MetaDataRequestDto metaDataRequestDto){

        MetaData metaData = new MetaData().builder().body(metaDataRequestDto.getBody()).build();

        metaDataRepository.save(metaData); // Id가 DB에 존재하면 update, 없으면 save
    }

    @Transactional
    public void update(String id, MetaDataRequestDto metaDataRequestDto){

        MetaData metaData = metaDataRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("해당 Id값을 가진 데이터 베이스 정보가 없습니다."));

        metaData.update(metaDataRequestDto.getBody());

        metaDataRepository.save(metaData); // Id가 DB에 존재하면 update, 없으면 save
    }

    @Transactional
    public void deleteById(String id){
        metaDataRepository.deleteById(id);
    }

}
