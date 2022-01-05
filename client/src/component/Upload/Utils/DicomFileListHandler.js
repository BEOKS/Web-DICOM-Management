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
