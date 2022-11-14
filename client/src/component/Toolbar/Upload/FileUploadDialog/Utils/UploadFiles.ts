import axios from "axios";
import {parse} from "csv-parse/lib/sync";
import { concurrencyPOSTHandler } from "./UploadController";
import { uploadFileAPI } from "../../../../../api/StorageAPI";

const DEBUG=true
const print=(msg:any)=>{
    if (DEBUG){
        console.log('uploadCsvFile : ',msg)
    }
}
const IMAGE_NAME='image_name'
const ANONYMIZED_ID='anonymized_id'
export async function uploadCsvFile(projectId : string,
                                    csvFile: File|undefined,
                                    callStart:()=>void,
                                    callback:(additionalProgress : number)=>void,
                                    callError:(error : string)=>void){
    callStart()
    console.log('start')
    if(csvFile===undefined){
        callError("csv is undefined")
        return
    }
    const csvJson=await loadCSV(csvFile)
    if(!checkValidCsv(csvJson)){
        callError(`CSV 속성에는 ${ANONYMIZED_ID}와 ${IMAGE_NAME} 속성이 존재해야합니다.`)
        return 
    }
    const chunkSize=1000
    const concurrency=10
    console.log('csvJson',csvJson)
    concurrencyPOSTHandler(csvJson,
        (data : any[])=> axios.post(`api/MetaDataList/insert/${projectId}`,data),
        (completeAPICount :number,apiNumber : number)=>{
            console.log('success!')
            callback(1/apiNumber*100)
        },
        (apiNumber : number, error : any)=>{
            callError(error)
        },
        concurrency,chunkSize)
}
function checkValidCsv(csvJson:any): boolean{
    if(csvJson.length===0){
        return true
    }
    else{
        if(!csvJson[0].hasOwnProperty(IMAGE_NAME) || !csvJson[0].hasOwnProperty(ANONYMIZED_ID)){
            return false
        }
        else{
            return true
        }
    }
}
function csv2json(csv :any){
    const data= parse (csv,{
        columns: true,
        cast: true,
        skip_empty_lines: true
    });
    return data
}

async function loadCSV(csvFile : File){
    let result = await new Promise((resolve) => {
        let fileReader = new FileReader();
        fileReader.onload = (e) => resolve(fileReader.result);
        fileReader.readAsText(csvFile);
    });
    const csvJson=csv2json(result)
    return csvJson
}

export async function uploadImageFile(projectId:string,imageFiles : File[],
    callbackBegin:()=>void,
    callbackSuccess:(filename:string, additionalProgress : number)=>void,
    callError:(filename:string,error :any)=>void){
    console.log(projectId,imageFiles)
    callbackBegin()
    const chunkSize=1
    const concurrency=10
    concurrencyPOSTHandler(imageFiles,
        (data : any[])=> {
            const formdata=new FormData()
            formdata.append("file",data[0])
            return axios({
                method: 'post',
                url: `/api/Storage/${projectId}`,
                data: formdata,
                headers: {
                    'Content-Type': 'multipart/form-data',
                }
            })
        },
        (completeAPICount :number,apiNumber : number)=>{
            callbackSuccess(imageFiles[completeAPICount-1].name,1/apiNumber*100)
        },
        (apiNumber : number, error : any)=>{
            callError(imageFiles[apiNumber].name,error)
        },
        concurrency,chunkSize)
}