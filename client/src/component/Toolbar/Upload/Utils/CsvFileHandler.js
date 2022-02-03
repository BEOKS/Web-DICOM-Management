import axios from "axios";
import { parse } from 'csv-parse/lib/sync';
/**
 * CSV 파일을 관리하기 위한 클래스입니다.
 * Json 변환, 파일 로드, 서버 업로드 등의 메서드가 포함되어 있습니다.
 */
export class CsvFileHandler {
    static ANONYMIZED_ID='anonymized_id'
    constructor(csvFile,projects) {
        this.csvFile=csvFile;
        this.projects=projects;
    }
    updateFile(csvFile,projects){
        this.csvFile=csvFile;
        this.projects=projects;
    }
    async uploadToServer(...callbackList){
        console.log('callbackList[0]',callbackList[0])
        callbackList[0](0,'Uploading Metadata...')
        this.uploadFileCount=0
        await axios.post(`api/MetaDataList/${this.projects.projectId}`,this.csvJson.data)
            .then(response=>{
                callbackList[0](0,'Uploading Complete!')
            })
            .catch(error=>{
                alert(`update fail ${error}`)
            })
        callbackList[0](0,'',false);
    }
    /**
     *  this.csvJson에 로드한 csv 정보를 json 형태로 저장
     * @param {*} file 
     * @returns 
     */
    async loadCsv(onloadCallBack) {
        if(this.csvFile instanceof File){
            let result = await new Promise((resolve) => {
                let fileReader = new FileReader();
                fileReader.onload = (e) => resolve(fileReader.result);
                fileReader.readAsText(this.csvFile);
            });
            this.csvJson=this.csv2json(result)           
            onloadCallBack(this.csvJson)
        }
    }
    csv2json(csv){
        const data= parse (csv,{
            columns: true,
            skip_empty_lines: true
          });
        return {'data':data}
    }
    getPatientIDList(){
        return this.csvJson.map( json => json.anonymized_id)
    }
}
