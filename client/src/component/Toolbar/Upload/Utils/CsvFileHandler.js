import axios from "axios";
import { parse } from 'csv-parse/lib/sync';
export class CsvFileHandler {
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
        await this.csvJson.data.map(async (data,index)=>{
            this.uploadFileCount=0;
            await axios.post(`api/MetaData/${this.projects.projectId}`,data)
                .then(response=>{
                    callbackList[0]((++this.uploadFileCount/this.csvJson.data.length)*100,'Uploading Metadata...')
                })
                .catch(error=>{
                    console.error(error);
                    callbackList[0](0,'Uploading Metadata error occurred')
                })
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
            console.log('this.csvJson',this.csvJson)
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
    getContentOfColumn(columnName){
        
    }
    getPatientIDList(){
        return this.csvJson.map( json => json.anonymized_id)
    }
    uploadFile(){
        
    }
}
