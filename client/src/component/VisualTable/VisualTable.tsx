import React from 'react';
import _ from "lodash";
import { Grid } from '@mui/material';
import { Chart, BarSeries, ArgumentAxis, ValueAxis, Title, Tooltip, PieSeries } from '@devexpress/dx-react-chart-material-ui';
import { EventTracker, HoverState, Animation } from '@devexpress/dx-react-chart';

type Body = {
    [key: string]: string | number
};

type MetaData = {
    metadataId: string,
    projectId: string,
    body: Body
};

type VisualTableProps = {
    metaData: MetaData[] | string // metaData가 'loading'일 경우엔 string type
};

const VisualTable: React.FC<VisualTableProps> = ({ metaData }) => {

    // metaData의 body 안에 있는 실제 data 추출
    // 단, metaData === 'loading'일 경우 제외
    const extractData = () => {
        if (typeof metaData !== 'string') {
            return Array.from(metaData).map(e => e.body);
        } else {
            return [];
        }
    };
    const data = extractData();

    // data로부터 key 추출
    const getKeysFromData = () => {
        if (data.length <= 0) {
            return [];
        } else {
            return Object.keys(data[0]);
        }
    };
    const keys = getKeysFromData();

    const eachData: any[] = [];
    const freq: any = {};


    for (let i = 0; i < keys.length; i++) {
        const key = keys[i];

        // data를 key별로 분리
        eachData.push(data.map(e => {
            const value = e[key];

            // value frequency count
            if (freq[key + value]) {
                freq[key + value] += 1;
            } else {
                freq[key + value] = 1;
            }
            return { [key]: value };
        }));
    }
    console.log(freq);

    // object 중복 제거
    const uniqEachData: any[] = [];
    for (let i = 0; i < eachData.length; i++) {
        uniqEachData.push(_.uniqBy(eachData[i], keys[i]));
    }

    // key마다 각 value의 percent 계산
    const addPercent = () => {
        for (let i = 0; i < uniqEachData.length; i++) {
            if (uniqEachData[i]) {
                uniqEachData[i].forEach((e: Body) => {
                    const percent: number = freq[keys[i] + e[keys[i]]] / eachData[i].length * 100;
                    e.percent = percent;
                })
            }
        }
    }
    addPercent();


    // return문 안에서는 반복문 사용이 불가하므로 차트 만드는 함수 따로 생성
    const chartRendering = () => {
        const result = [];

        for (let i = 0; i < keys.length; i++) {
            const key = keys[i];

            if (key === 'age') {
                result.push(
                    <Grid item xs={12}>
                        <Chart key={key} data={uniqEachData[i]}>
                            <ArgumentAxis />
                            <ValueAxis />
                            <BarSeries
                                name={key}
                                valueField='percent'
                                argumentField={key}
                            />
                            <Title text={key} />
                            <EventTracker />
                            <HoverState />
                            <Animation />
                            <Tooltip />
                        </Chart>
                    </Grid>
                );
            } else if (key === 'StudyInstanceUID' || key === 'anonymized_id' || key === 'image_name') {
                continue;
            } else {
                result.push(
                    <Grid item xs={4}>
                        <Chart key={key} data={uniqEachData[i]}>
                            <PieSeries
                                name={key}
                                valueField='percent'
                                argumentField={key}
                                innerRadius={0.6}
                            />
                            <Title text={key} />
                            <EventTracker />
                            <HoverState />
                            <Animation />
                            <Tooltip />
                        </Chart>
                    </Grid>
                );
            }

        }
        return result;
    };

    return (
        <Grid container rowSpacing={10}>
            {chartRendering()}
        </Grid>
    );
};

export default VisualTable;