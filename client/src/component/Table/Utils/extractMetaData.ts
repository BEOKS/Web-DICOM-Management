import { MetaData, Body } from '../MetaDataGrid'

// metaData의 body 안에 있는 실제 data 추출
// Row Selection을 위해 body에 metadataId도 추가
export const extractBody = (metaData: MetaData[]) => {
    if (metaData.length > 0) {
        return Array.from(metaData).map(e => ({ metadataId: e.metadataId, ...e.body }));
    } else {
        return [];
    }
};

// data로부터 key 추출
export const extractColumns = (data: Body[]) => {
    if (data.length <= 0) {
        return [];
    } else {
        // Data Grid Column에 metadataId는 보이지 않도록 함
        return Object.keys(data[0]).filter(column => column !== 'metadataId');
    }
};