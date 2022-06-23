import { Chart, Series, Legend, Label, Export, } from 'devextreme-react/chart';

type Data = {
    [key: string]: string | number
};

type BarChartProps = {
    attr: string,
    data: Data[]
};

const BarChart: React.FC<BarChartProps> = ({ attr, data }) => {
    return (
        <Chart
            id={attr}
            title={attr}
            dataSource={data}
        >
            <Series
                argumentField={attr}
                valueField="count"
                type="bar"
            >
                <Label visible={true} backgroundColor="#004c8c" />
            </Series>

            <Legend visible={false} />
            <Export enabled={true} />
        </Chart>
    )
};

export default BarChart;

