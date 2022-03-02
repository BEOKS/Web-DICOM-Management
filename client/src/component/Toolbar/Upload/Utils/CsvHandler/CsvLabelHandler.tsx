import {LABEL} from "../../MetadataColumnTypeDecision/ColumnTypeDesicionDialog";
import {DICOM_ID} from "../../MetadataColumnTypeDecision/ColumnTypeDesicionDialog";
import {Schema} from "../../MetadataColumnTypeDecision/ColumnTypeDesicionDialogReducer";
export type CsvRow = object
export type CsvSlice={
    id : string,
    data : object
}
export class CsvSlices{
    patient : CsvRow[] = []
    study : CsvRow[] = []
    series : CsvRow[] = []
    instance : CsvRow[] = []

    appendSlice(csvSlice: CsvSlices) {
        this.patient.concat(csvSlice.patient)
        this.study.concat(csvSlice.study)
        this.series.concat(csvSlice.series)
        this.instance.concat(csvSlice.instance)
    }
}
function divideCsvRowWithLevel(csvData : any,schema : Schema): CsvSlices{
    const csvSlices : CsvSlices=new CsvSlices()
    const csvDataKeySet=new Set(Object.keys(csvData))
    const schemaKeySet=new Set(Object.keys(schema))

    let areSetsEqual = (a: Set<string>, b: Set<string>) => a.size === b.size && Array.from(a).every(value => b.has(value));
    if(!areSetsEqual(csvDataKeySet,schemaKeySet)){
        throw new Error(`입력된 CSV의 키와 스키마의 키가 다릅니다.\n 
            csvData : ${Array.from(csvDataKeySet).join('')} \n
            schema : ${Array.from(schemaKeySet).join('')}`)
    }
    const patientCsv: any={}
    const studyCsv: any={}
    const seriesCsv: any={}
    const instanceCsv: any={}
    Object.entries(schema).forEach(i=>{
        const [key,type] = i
        switch (type){
            case DICOM_ID.PATIENT || LABEL.PATIENT:
                patientCsv[key]=csvData[key]
                return
            case DICOM_ID.STUDY || LABEL.STUDY:
                return;
                studyCsv[key]=csvData[key]
            case DICOM_ID.SERIES || LABEL.SERIES:
                seriesCsv[key]=csvData[key]
                return;
            case DICOM_ID.INSTANCE || LABEL.INSTANCE:
                instanceCsv[key]=csvData[key]
                return;
            default:
                throw TypeError(`스키마의 타입이 올바르지 않습니다. input type : ${type}`)
        }
    })
    csvSlices.patient=patientCsv
    csvSlices.study=studyCsv
    csvSlices.series=seriesCsv
    csvSlices.instance=instanceCsv
    return csvSlices
}
export default function divideCsvWithLevel(csvData : CsvRow[],schema : Schema) : CsvSlices{
    const csvSlices : CsvSlices=new CsvSlices()
    csvData.forEach((csvRow: CsvRow)=>{
        const csvSlice=divideCsvRowWithLevel(csvRow,schema)
        csvSlice.appendSlice(csvSlice)
    })
    return csvSlices
}
