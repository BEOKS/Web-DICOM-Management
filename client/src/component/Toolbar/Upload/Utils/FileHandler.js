import { CsvFileHandler } from "./CsvHandler/CsvFileHandler"
import { DicomFileListHandler } from "./DicomFileListHandler"
import getUuid from 'uuid-by-string';
/**
 * FileHandler는 Dicom과 메타데이터를 다루기 위한 싱글톤 클래스입니다.
 */
class FileHandler{
    static CSV_NOT_CONTAIN_PATIENT_ID=`메타데이터 CSV파일에 ${CsvFileHandler.ANONYMIZED_ID}속성이 포함되어 있지 않습니다.`
    static CSV_NOT_CONTAIN_STUDY_ID=`메타데이터 CSV파일에 ${CsvFileHandler.STUDY_UID}속성이 포함되어 있지 않습니다.`
    static CSV_HEADER_INCONSISTENTY=`CSV 파일 속성이 일치하지 않습니다.`

    constructor(dicomFiles,csvFile,projects){
        this.dicomFileListHandler=new DicomFileListHandler(dicomFiles)
        this.csvFileHandler=new CsvFileHandler(csvFile,projects)
    }
    updateFilePath(dicomFiles,csvFile,projects){
        this.dicomFileListHandler.updateFileList(dicomFiles)
        this.csvFileHandler.updateFile(csvFile,projects)
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
    async checkUpdatePossibility(csvFile,dicomFileList,metaData){
        if(!csvFile.data[0].hasOwnProperty(CsvFileHandler.ANONYMIZED_ID)){
            return {'state':FileHandler.CSV_NOT_CONTAIN_PATIENT_ID,'errorDicomPathList':[]}
        }
        if(!csvFile.data[0].hasOwnProperty(CsvFileHandler.STUDY_UID)){
            return {'state':FileHandler.CSV_NOT_CONTAIN_STUDY_ID,'errorDicomPathList':[]}
        }
        if(Array.isArray(metaData)&& metaData.length!==0 && Object.keys(metaData[0].body).join('')!==Object.keys(csvFile.data[0]).join('')){
            return {'state': FileHandler.CSV_HEADER_INCONSISTENTY,'errorDicomPathList':[]}
        }
        if(dicomFileList.length===0){
            console.log('dicomFileList empty')
            return {'state':'success', 'errorDicomPathList':[]}; //메타데이터만 업로드 하도록 허용
        }
        const dicomFilePatientIdsList=dicomFileList.map( file =>
            DicomFileListHandler.getPatientIDof(file)
        )
        const csvFilePatientIdsList=csvFile.data.map( json => json.anonymized_id)
        const errorDicomPathList=dicomFilePatientIdsList.map(id => 
            csvFilePatientIdsList.includes(id))
        const state= errorDicomPathList.includes(false)? 'error':'success';
        return {'state': state, 'errorDicomPathList':errorDicomPathList};
    }
    async uploadFiles(onloadEachFileCallBack){
        await this.anonymizeIDinDicomAndCSV();
        console.log('anonymizeUID',this.anonymizeUID('1.3.12.2.1107.5.1.4.54191.30000008101905312148400002618'))
        await this.csvFileHandler.uploadToServer(onloadEachFileCallBack);
        this.dicomFileListHandler.uploadToServer(onloadEachFileCallBack);
        //this.dicomFileListHandler.uploadToServer(onloadEachFileCallBack)
    }
    async anonymizeIDinDicomAndCSV(){
        const anonymizedIdList=this.anonymizePatientID(this.csvFileHandler.getPatientIDList());
        console.log('this.csvFileHandler.getStudyUIDList()',this.csvFileHandler.getStudyUIDList())
        const anonymizedStudyUIDList=this.csvFileHandler.getStudyUIDList().map(uid=>this.anonymizeUID(uid));
        this.csvFileHandler.csvJson.data=this.csvFileHandler.csvJson.data.map((json,index)=>{
            return {...json , 'anonymized_id' : anonymizedIdList[index], 'StudyInstanceUID' : anonymizedStudyUIDList[index]};
        })
        await this.dicomFileListHandler.anonymizeIDs(this.anonymizePatientID,this.anonymizeUID)

    }
    anonymizePatientID(patientIdList){
        return patientIdList.map(id => {
            const APP_UUID=process.env.REACT_APP_APP_UUID;
            return getUuid(id+APP_UUID)
        });
    }
    anonymizeUID(uid){
        /**
         * UID는 IOS식별자.ANSI식별자.국가코드.OID코드.제품분류코드.시리얼번호.검사ID.시리즈번호.인스턴스번호.획득시간 (10개)
         * 형식으로 정해져있다. (ex.1.2.410.xxxxx.3.152.235.2.12.187636473)
         * ex. STUDY : 1.2.410.2000010.82.242.1018932208290001
         *  series : 1.3.12.2.1107.5.1.4.54191.30000008101905312148400002618
         *  instance : 1.3.12.2.1107.5.1.4.54191.30000008101905312148400002628
         *
         *  여기서는 익명화를 위해서 REACT_APP_ENCODE_DIGIT을 기준으로 암호화를 진행한다.
         */
        const ENCODE_DIGIT=135612217;
        const uidSlice=uid.split('.')
        const uidSliceLength=uidSlice.length;
        return uidSlice.map((digit,index) =>{
            return index < 2 || index===uidSliceLength-1? digit: ((parseInt(digit)*ENCODE_DIGIT)%(Math.pow(10,digit.length-1)))
                .toLocaleString('fullwide', { useGrouping: false })
        }).join('.')
    }
    
}
export default FileHandler;

