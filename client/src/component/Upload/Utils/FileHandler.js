import { CsvFileHandler } from "./CsvFileHandler"
import { DicomFileListHandler } from "./DicomFileListHandler"

class FileHandler{
    constructor(dicomFilePathList,csvFilePath){
        this.dicomFileListHandler=new DicomFileListHandler(dicomFilePathList)
        this.csvFileHandler=new CsvFileHandler(csvFilePath)
    }
    /**
     * @Description : Dicom 파일들의 Patient ID가 csv파일의 'Patient_ID'와 일치하는지 확인
     * @return : 일치하지 않는 Patient ID List를 전달 (Dicom - CSV, CSV - Dicom)
     */
    checkDicomAndCsvPatientIDEqual(){
        const dicomFilePatientIDList=this.dicomFileListHandler
                                .dicomFileList()
                                .map(dicom => this.dicomFileListHandler.getPatientID(dicom))
        const csvPatientIDList=this.csvFileHandler.getContentOfColumn()
        return this.arrayEquals(dicomFilePatientIDList,csvPatientIDList)
    }
    arrayEquals(a, b) {
        return Array.isArray(a) &&
            Array.isArray(b) &&
            a.length === b.length &&
            a.every((val, index) => val === b[index]);
    }
    update(dicomFilePathList,csvFilePath){

    }
}
export default FileHandler;