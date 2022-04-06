import axios from "axios";
import {parse} from "csv-parse/lib/sync";

const DEBUG=true
const print=(msg:any)=>{
    if (DEBUG){
        console.log('uploadCsvFile : ',msg)
    }
}
export async function uploadCsvFile(projectId : string,csvFile: File|undefined,callback:()=>{}){
    if(csvFile===undefined){
        return
    }
    const csvJson=await loadCSV(csvFile)
    print(csvJson)
    axios.post(`api/MetaDataList/insert/${projectId}`,csvJson)
        .then(response=>{
            callback()
        })
        .catch(error=>{
            alert(`update fail ${error}`)
        })
    return
}
function csv2json(csv :any){
    print(csv)
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
export function uploadImageFile(projectId:string,imageFiles : File[],callback:(filename:string, percentage : number)=>void){

}