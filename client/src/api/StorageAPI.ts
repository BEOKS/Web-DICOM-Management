import axios from 'axios'

export const uploadFileAPI=async (projectId:string,
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

export const getFileListAPI=async (projectId: string,
                                callbackSuccess:(fileList:string[])=>void,
                                callbackError:(error: any)=>void)=>{
    axios.get(`/api/Storage/${projectId}`)
        .then((response)=>callbackSuccess((response.data.body)))
        .catch((error)=>callbackError(error))

}

export const downloadFile=(projectId: string,
                           filename: string,
                           callbackListener=(image : string)=>{},
                           callbackError:(error:any)=>void)=>{
    axios.get(`api/Storage/${projectId}/${filename}`)
        .then((response)=>{
            callbackListener(Buffer.from(response.data, 'binary').toString('base64'))
        })
        .catch(error=>{
            callbackError(error)
        })
}

