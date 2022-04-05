import axios from 'axios'
const URL_HEADER='Storage'
const SUCCESS_MSG="success"

const openFile=(filename: string,callBack:(file: Blob)=>{})=>{

}

export const uploadFile=(file : File,dummy=true) : string=>{
    if(dummy){
        return SUCCESS_MSG
    }
    else{
        //TODO Not Yet Implemented
    }
    return "this is never called"
}

export const downloadFile=(filename: string,callbackListener=(file : Blob)=>{},dummy=true): Blob | null=>{
    if(dummy){
        const blob: Blob=new Blob()
        return blob
    }
    else{
        //TODO Not Yet Implemented
    }
    return null
}

