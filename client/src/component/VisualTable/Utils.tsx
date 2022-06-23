import _ from "lodash";
import { Body, MetaData } from "../Table/MetaDataGridReducer";

// metaData의 body 안에 있는 실제 data 추출
// 단, metaData === 'loading'일 경우 제외
export const extractBody = (metaData: MetaData[] | string) => {
    if (typeof metaData !== 'string') {
        return Array.from(metaData).map(e => e.body);
    } else {
        return [];
    }
};

// data로부터 key 추출
export const extractKeys = (data: Body[]) => {
    if (data.length <= 0) {
        return [];
    } else {
        return Object.keys(data[0]);
    }
};

// data를 각 속성별로 분리하고, value frequency count
export const separateData = (data: Body[], keys: string[]) => {
    const eachData: any[] = [];
    const freq: any = {};

    for (let i = 0; i < keys.length; i++) {
        const key = keys[i];

        eachData.push(data.map(e => {
            const value = e[key];

            if (freq[key + value]) {
                freq[key + value] += 1;
            } else {
                freq[key + value] = 1;
            }
            return { [key]: value };
        }));
    }
    return [eachData, freq];
};

// 속성별로 분리한 eachData에서 데이터 중복 제거
export const deduplication = (eachData: any[], keys: string[]) => {
    const uniqEachData: any[] = [];
    for (let i = 0; i < eachData.length; i++) {
        uniqEachData.push(_.uniqBy(eachData[i], keys[i]));
    }
    return uniqEachData;
};

// 속성마다 각 데이터의 count 추가
export const addCount = (uniqEachData:any[], freq:any, keys:string[]) => {
    for (let i = 0; i < uniqEachData.length; i++) {
        if (uniqEachData[i]) {
            uniqEachData[i].forEach((e: any) => {
                const count: number = freq[keys[i] + e[keys[i]]];
                e.count = count;
            })
        }
    }
}

// 특정 속성에 대해 모든 데이터가 number인지 검사
export function isNumeric(allData: any, key: string) {
    for (let i = 0; i < allData.length; i++) {
        if (isNaN(parseFloat(allData[i][key])))
            return false;
    }
    return true;
}