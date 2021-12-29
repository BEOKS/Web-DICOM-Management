import * as React from 'react'
import { Button, Dialog, DialogTitle, DialogContent,
    DialogActions, DialogContentText } from "@mui/material";
import { useState } from 'react';
import DicomUploadBox from './DicomUploadBox/DicomUploadBox';
import MetaUploadBox from './MetaUploadBox';
import FileHandler from '../Utils/FileHandler';
import ErrorDescriptionBox from './ErrorDescriptionBox';

const dialogContentDescrptionText="메타데이터는 csv의 'PatientID' 속성에는 업로드하려는 Dicom 파일의 ID가 존재해야 합니다. "

let fileHandler=null
export default function UploadDialog(props){
    const [dicomFilePathList, setdicomFilePathList]=useState([]);
    const [csvFilePath, setCsvFilePath]=useState();
    const [nonSyncDicomFilePatientIDs,setNonSyncDicomFilePatientIDs]=useState();
    const [nonSyncCSVFilePatientIDs,setNonSyncCSVFilePatientIDs]=useState();

    function handleDicomAndCsvSync(dicomFilePathList,csvFilePath){
        if(fileHandler===null){
            fileHandler=new FileHandler(dicomFilePathList,csvFilePath);
        }
        else{
            fileHandler.update(dicomFilePathList,csvFilePath);
        }
        const [nonSyncDicomFilePatientIDs,nonSyncCSVFilePatientIDs]=fileHandler.checkDicomAndCsvPatientIDEqual();
        setNonSyncDicomFilePatientIDs(nonSyncDicomFilePatientIDs);
        setNonSyncCSVFilePatientIDs(nonSyncCSVFilePatientIDs);
    }
    
    return(
        <Dialog open={props.open}>
            <DialogTitle>Dicom 파일 업로드</DialogTitle>
            <DialogContent>
                <DialogContentText> 
                    {dialogContentDescrptionText}
                </DialogContentText>
                <MetaUploadBox 
                    csvFilePath={csvFilePath}
                    setCsvFilePath={setCsvFilePath}/>
                <DicomUploadBox 
                    dicomFilePathList={dicomFilePathList}
                    setdicomFilePathList={setdicomFilePathList}/>
                <ErrorDescriptionBox 
                    nonSyncDicomFilePatientIDs={nonSyncDicomFilePatientIDs}
                    nonSyncCSVFilePatientIDs={nonSyncCSVFilePatientIDs}/>
            </DialogContent>
            <DialogActions>
                <Button onClick={()=>handleDicomAndCsvSync(dicomFilePathList,csvFilePath)}>확인</Button>
                <Button onClick={()=>props.setOpen(false)}>취소</Button>
            </DialogActions>
        </Dialog>
    )
}