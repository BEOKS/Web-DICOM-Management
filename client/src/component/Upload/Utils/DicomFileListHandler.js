import dicomParser from 'dicom-parser'

export class DicomFileListHandler {
    constructor(fileList) {
        this.fileList=fileList
        this.dicomFileList=[]
        fileList.map(file => this.loadFile(file));
        console.log('dicomFileList',this.dicomFileList)
    }
    loadFile(file){
        const storeFile=(dataSet)=>{
            this.dicomFileList.push(dataSet)
            console.log('dicomFileList',this.dicomFileList)
        }
        let reader = new FileReader();
        reader.onload = function(file) {
            var arrayBuffer = reader.result;
            // Here we have the file data as an ArrayBuffer.  dicomParser requires as input a
            // Uint8Array so we create that here
            var byteArray = new Uint8Array(arrayBuffer);
            storeFile(dicomParser.parseDicom(byteArray));
        }
        reader.readAsArrayBuffer(file)
    }
    anonymizeDicom(dicomFile) {

    }
    updateDicomFileList(filePath) {

    }
    getPatientIDof(dicomFile){
        return dicomFile.string('x00100020');
    }
}
