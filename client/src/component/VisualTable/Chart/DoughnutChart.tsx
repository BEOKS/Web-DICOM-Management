import PieChart, { Legend, Export, Series, Label, Font, Connector } from 'devextreme-react/pie-chart';

type Data = {
    [key: string]: string | number
};

type DoughnutChartProps = {
    attr: string,
    data: Data[]
};

const DoughnutChart: React.FC<DoughnutChartProps> = ({ attr, data }) => {
    console.log(data);

    function customizeText(arg: any) {
        return `${arg.valueText} (${arg.percentText})`;
    }

    return (
        <PieChart
            id={attr}
            type="doughnut"
            dataSource={data}
            title={attr}
            sizeGroup="piesGroup"
        >
            <Legend
                orientation="horizontal"
                itemTextPosition="right"
                horizontalAlignment="center"
                verticalAlignment="bottom"
                columnCount={4} />
            <Export enabled={true} />
            <Series argumentField={attr} valueField="count">
            
                <Label
                    visible={true}
                    position="columns"
                    customizeText={customizeText}>
                    <Font size={12} />
                    <Connector visible={true} width={0.5} />
                </Label>
            </Series>
        </PieChart>
    );
};

export default DoughnutChart;