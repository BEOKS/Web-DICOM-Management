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
        this.patient=this.patient.concat(csvSlice.patient)
        this.study=this.study.concat(csvSlice.study)
        this.series=this.series.concat(csvSlice.series)
        this.instance=this.instance.concat(csvSlice.instance)
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
            case LABEL.PATIENT:
            case DICOM_ID.PATIENT:
                patientCsv[key]=csvData[key]
                break
            case LABEL.STUDY:
            case DICOM_ID.STUDY:
                studyCsv[key]=csvData[key]
                break
            case LABEL.SERIES:
            case DICOM_ID.SERIES:
                seriesCsv[key]=csvData[key]
                break
            case LABEL.INSTANCE:
            case DICOM_ID.INSTANCE:
                instanceCsv[key]=csvData[key]
                break
            default:
                throw TypeError(`스키마의 타입이 올바르지 않습니다. \ninput type : ${type}`)
        }
    })
    csvSlices.patient=[patientCsv]
    csvSlices.study=[studyCsv]
    csvSlices.series=[seriesCsv]
    csvSlices.instance=[instanceCsv]
    return csvSlices
}
export default function divideCsvWithLevel(csvData : CsvRow[],schema : Schema) : CsvSlices{
    const result : CsvSlices=new CsvSlices()
    csvData.forEach((csvRow: CsvRow)=>{
        const csvSlice=divideCsvRowWithLevel(csvRow,schema)
        result.appendSlice(csvSlice)
    })
    return result
}
