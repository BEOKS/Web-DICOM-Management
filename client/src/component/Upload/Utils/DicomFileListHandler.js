import axios from 'axios'
import dicomParser from 'dicom-parser'

export class DicomFileListHandler {
    constructor(fileList) {
        this.fileList=fileList
        this.dicomFileList=[]
        // if(Array.isArray(fileList)){
        //     fileList.map(file => this.loadFile(file));
        // }
        // console.log('dicomFileList',this.dicomFileList)
    }
    uploadToServer(onloadEachFileCallBack){
        this.uploadCount=0;
        this.fileListLength=this.fileList.length;
        const url='/api/dicom'
        for(let index=0;index<this.fileList.length;index++){
            const file=this.fileList[index]
            const formData = new FormData();
            formData.append('dicomfile',file)
            const config = {
                headers: {
                    'content-type': 'multipart/form-data'
                }
            }
            axios.post(url,formData,config)
                .then(response=>{
                    this.uploadCount+=1;
                    if(this.uploadCount===this.fileListLength){
                        onloadEachFileCallBack(100,
                            "Finish Uploading Dicom Files!")
                    }
                    else{
                        onloadEachFileCallBack((this.uploadCount/this.fileListLength)*100,
                        "Uploading "+file.name)
                    }
                })
                .catch(error=>{
                    console.log(url,formData,config)
                    console.error(error)
                    onloadEachFileCallBack((this.uploadCount/this.fileListLength)*100,"error")
                })
        }
        onloadEachFileCallBack(0,'',false);
    }
    updateFileList(fileList){
        this.fileList=fileList
        this.dicomFileList=[]
    }
    async loadFile(onloadEachCallBack,onLoadAllCallBack){
        const storeFile=(dataSet)=>{
            this.dicomFileList.push(dataSet)
        }
        for(let index=0;index<this.fileList.length;index++){
            const file=this.fileList[index]
            if(file instanceof File){
                let result = await new Promise((resolve) => {
                    let fileReader = new FileReader();
                    fileReader.onload = (e) => resolve(fileReader.result);
                    fileReader.readAsArrayBuffer(file);
                });
                let byteArray = new Uint8Array(result)
                storeFile(dicomParser.parseDicom(byteArray));
                onloadEachCallBack(this.dicomFileList[index]);
            }
        }
        onLoadAllCallBack(this.dicomFilelist);
    }
    anonymizeDicom(dicomFile) {

    }
    updateDicomFileList(filePath) {

    }
    static getPatientIDof(dicomFile){
        return dicomFile.string('x00100020');
    }
    uploadFile(){
        
    }
}
