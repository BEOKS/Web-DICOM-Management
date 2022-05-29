import React from 'react';
import { useSelector } from 'react-redux';
import { RootState } from '../../store';
import { Grid, Stack } from '@mui/material';
import { extractBody, extractKeys, separateData, deduplication, addCount, isNumeric } from './Utils'
import VisualTableOptions from './VisualTableOptions';
import BarChart from './Chart/BarChart';
import DoughnutChart from './Chart/DoughnutChart';

const MetadataStatisticInsightView = () => {
    const metaData = useSelector((state: RootState) => state.MetaDataGridReducer.metaData);
    const options = useSelector((state: RootState) => state.VisualTableReducer.options);
    const data = extractBody(metaData);
    const keys = extractKeys(data);
    const [eachData, freq] = separateData(data, keys);
    const uniqEachData = deduplication(eachData, keys);
    console.log('MetadataStatisticInsightView')
    addCount(uniqEachData, freq, keys);

    // return문 안에서는 반복문 사용이 불가하므로 차트 만드는 함수 따로 생성
    const chartRendering = () => {
        const result = [];

        for (let i = 0; i < keys.length; i++) {
            const key = keys[i];

            if (!options.includes(key)) {
                continue;
            }

            if (isNumeric(uniqEachData[i], key)) { // Bar Chart
                uniqEachData[i].sort((a: any, b: any) => {
                    return parseFloat(a[Object.keys(uniqEachData[i][0])[0]]) - parseFloat(b[Object.keys(uniqEachData[i][0])[0]])
                })
                result.push(
                    <Grid item key={key} xs={12}>
                        <BarChart attr={key} data={uniqEachData[i]} />
                    </Grid>
                );
            }
            else { // Doughnut Chart
                result.push(
                    <Grid item key={key} xs={4}>
                        <DoughnutChart attr={key} data={uniqEachData[i]} />
                    </Grid >
                );
            }
        }
        return result;
    };

    return (
        <Stack mt={3} mx={3}>
            <VisualTableOptions keys={keys} />
            <Grid container rowSpacing={5} pt={5}>
                {chartRendering()}
            </Grid>
        </Stack>
    );
};

export default MetadataStatisticInsightView;