import divideCsvWithLevel from "./CsvLabelDivider";
import {csvSlicesExample, csvJsonExample, schemaExample} from "./Sample_data";
test('Divide csv data',()=>{
    expect(divideCsvWithLevel(csvJsonExample,schemaExample)).toStrictEqual(csvSlicesExample)
})

