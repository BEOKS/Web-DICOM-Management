import { CsvFileHandler } from "./CsvFileHandler"
import { DicomFileListHandler } from "./DicomFileListHandler"
/**
 * FileHandler는 Dicom과 메타데이터를 다루기 위한 싱글톤 클래스입니다.
 */
class FileHandler{
    constructor(dicomFiles,csvFile){
        this.dicomFileListHandler=new DicomFileListHandler(dicomFiles)
        this.csvFileHandler=new CsvFileHandler(csvFile)
    }
    updateFilePath(dicomFiles,csvFile){
        this.dicomFileListHandler.updateFileList(dicomFiles)
        this.csvFileHandler.updateFile(csvFile)
    }
    async loadFile(onloadCallBack,dicomOnLoadAllCallBack=()=>{}){
        await this.csvFileHandler.loadCsv(()=>{});
        await this.dicomFileListHandler.loadFile(()=>{},dicomOnLoadAllCallBack);
        onloadCallBack(this.csvFileHandler.csvJson,this.dicomFileListHandler.dicomFileList);
    }
    /**
     * @returns : {
     *  state : "success" or "error" string, 
     *  errorDicomPathList : 메타 데이터의 PatientID 속성에 포함되어 있지 않은 dicom 파일의 리스트입니다.
     * }
     */
    async checkUpdatePossibility(csvFile,dicomFileList){
        if(dicomFileList.length===0){
            console.log('dicomFileList empty')
            return {'state':'warning', 'errorDicomPathList':[]};
        }
        const dicomFilePatientIdsList=dicomFileList.map( file =>
            DicomFileListHandler.getPatientIDof(file)
        )
        const csvFilePatientIdsList=csvFile.data.map( json => json.anonymized_id)
        const errorDicomPathList=dicomFilePatientIdsList.map(id => 
            csvFilePatientIdsList.includes(id))
        const state= errorDicomPathList.includes(false)? 'error':'success';
        console.log('checkUpdatePossibility',{'state': state, 'errorDicomPathList':errorDicomPathList})
        return {'state': state, 'errorDicomPathList':errorDicomPathList};
    }
    async uploadFiles(onloadEachFileCallBack){
        await this.csvFileHandler.uploadToServer(onloadEachFileCallBack);
        await this.dicomFileListHandler.uploadToServer(onloadEachFileCallBack);
        //this.dicomFileListHandler.uploadToServer(onloadEachFileCallBack)
    }
    
}
export default FileHandler;

