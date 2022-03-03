import {csvJsonExample, csvSlicesExample} from "./Sample_data";
import {MergeCsvSlices, MergeCsvSlicesImpl} from "./CsvLabelMerger";

test('Merge Csv Slice to one',()=>{
    const mergeCsvSlices : MergeCsvSlices=new MergeCsvSlicesImpl()
    expect(mergeCsvSlices.merge(csvSlicesExample)).toStrictEqual(csvJsonExample)
})