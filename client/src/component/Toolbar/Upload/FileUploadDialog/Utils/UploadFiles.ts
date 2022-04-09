import axios from "axios";
import {parse} from "csv-parse/lib/sync";

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
                                    callback:()=>void,
                                    callError:(error : string)=>void){
    callStart()
    if(csvFile===undefined){
        callError("csv is undefined")
        return
    }
    const csvJson=await loadCSV(csvFile)
    if(!checkValidCsv(csvJson)){
        callError(`CSV 속성에는 ${ANONYMIZED_ID}와 ${IMAGE_NAME} 속성이 존재해야합니다.`)
        return
    }
    await axios.post(`api/MetaDataList/insert/${projectId}`,csvJson)
        .then(response=>{
            callback()
        })
        .catch(error=>{
            callError(error)
        })
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
const uploadFileAPI=async (projectId:string,
    file : File,
    callbackSuccess:(response:any)=>void,
    callbackError : (erorr:any)=>void)=>{
    const formdata=new FormData()
    formdata.append("file",file)
    await axios({
        method: 'post',
        url: `/api/Storage/${projectId}`,
        data: formdata,
        headers: {
          'Content-Type': 'multipart/form-data',
        }
      })
      .then(callbackSuccess)
      .catch(callbackError)
}
export async function uploadImageFile(projectId:string,imageFiles : File[],
    callback:(filename:string, percentage : number)=>void,
    callError:(filename:string,error :any)=>void){

    imageFiles.forEach(async (file,index)=>{
        uploadFileAPI(projectId,file,
            (response:any)=>callback(file.name,(index+1)/imageFiles.length*100),
            (error: any)=>callError(file.name,error))
    })
}