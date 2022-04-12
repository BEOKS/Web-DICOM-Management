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
    axios.get<Blob>(`api/Storage/${projectId}/${filename}`,{'responseType':'blob'})
        .then((response)=>{
            const myFile = new File([response.data], 'imageName')
            const reader = new FileReader()
            reader.onload = ev => {
                const previewImage = String(ev.target?.result)
                callbackListener(previewImage) // myImage라는 state에 저장
              }
              reader.readAsDataURL(myFile)
        })
        .catch(error=>{
            callbackError(error)
        })
}

