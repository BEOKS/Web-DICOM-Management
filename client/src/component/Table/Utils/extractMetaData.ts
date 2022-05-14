import { MetaData, Body } from '../MetaDataGrid'

// metaData의 body 안에 있는 실제 data 추출
export const extractBody = (metaData: MetaData[]) => {
    if (metaData.length > 0) {
        return Array.from(metaData).map(e => e.body);
    } else {
        return [];
    }
};

// data로부터 key 추출
export const extractColumns = (data: Body[]) => {
    if (data.length <= 0) {
        return [];
    } else {
        return Object.keys(data[0]);
    }
};