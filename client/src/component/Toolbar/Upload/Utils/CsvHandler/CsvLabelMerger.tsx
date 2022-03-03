import {CsvRow, CsvSlices} from "./CsvLabelDivider";

export interface MergeCsvSlices {
    merge: (csvSlice: CsvSlices) => CsvRow[]
}

export class MergeCsvSlicesImpl implements MergeCsvSlices {
    merge(csvSlice: CsvSlices): CsvRow[] {
        return [];
    }
}