import { MetaData } from './VisualTable';

// metaData의 body 안에 있는 실제 data 추출
// 단, metaData === 'loading'일 경우 제외
export const extractData = (metaData: MetaData[] | string) => {
    if (typeof metaData !== 'string') {
        return Array.from(metaData).map(e => e.body);
    } else {
        return [];
    }
};
// data로부터 key 추출
export const getKeysFromData = (data: any[]) => {
    if (data.length <= 0) {
        return [];
    } else {
        return Object.keys(data[0]);
    }
};
// 특정 속성에 대해 모든 데이터가 number인지 검사
export function isNumeric(allData: any, key: string) {
    for(let i = 0; i < allData.length; i++){
        if (isNaN(parseFloat(allData[i][key])))
            return false;
    }
    return true;
}