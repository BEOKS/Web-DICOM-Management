import { CsvFileHandler } from "./CsvFileHandler"
import { DicomFileListHandler } from "./DicomFileListHandler"
/**
 * FileHandler는 Dicom과 메타데이터를 다루기 위한 싱글톤 클래스입니다.
 */
class FileHandler{
    constructor(dicomFiles,csvFile){
        this.updateFilePath(dicomFiles,csvFile)
    }
    updateFilePath(dicomFiles,csvFile){
        this.dicomFiles=dicomFiles;
        this.csvFile=csvFile;
        this.dicomFileListHandler=new DicomFileListHandler(dicomFiles)
        this.csvFileHandler=new CsvFileHandler(csvFile)
    }
    /**
     * @returns : {
     *  state : "success" or "error" string, 
     *  errorDicomPathList : 메타 데이터의 PatientID 속성에 포함되어 있지 않은 dicom 파일의 리스트입니다.
     * }
     */
    checkUpdatePossibility(){
        const dicomFilePatientIdsList=this.dicomFileListHandler.dicomFileList.map( file =>{
            const ret={[file.name] : this.dicomFileListHandler.getPatientIDof(file)};
            return ret;
        })
        console.log('checkUpdatePossibility',dicomFilePatientIdsList)
        const csvFilePatientIdsList=this.csvFileHandler.getPatientIDList()
        console.log('checkUpdatePossibility',csvFilePatientIdsList)
        console.log('checkUpdatePossibility',dicomFilePatientIdsList.map((key,value)=>[key,value]))
        return {'state':'error', 'errorDicomPathList':[]};
    }
    
}
export default FileHandler;