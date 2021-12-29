export class DicomFileListHandler {
    constructor(filePahtList) {
        this.dicomFilePathList=filePahtList
        this.dicomFileList = filePahtList.map(path => this.loadFileFromPath(path));
    }
    loadFileFromPath(filePath) {
    }
    anonymizeDicom(dicomFile) {
    }
    updateDicomFileList(filePath) {
    }
    getPatientID(dicomFile){
        
    }
}
