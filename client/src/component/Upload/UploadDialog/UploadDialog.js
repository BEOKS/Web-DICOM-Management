import * as React from 'react'
import { Button, Dialog, DialogTitle, DialogContent,
    DialogActions, DialogContentText } from "@mui/material";
import { useState } from 'react';
import DicomUploadBox from './DicomUploadBox';
import MetaUploadBox from './MetaUploadBox';
import FileHandler from '../Utils/FileHandler';
import ErrorDescriptionBox from './ErrorDescriptionBox';

const dialogContentDescrptionText="Dicom 파일과 메타데이터를 업로드 할 수 있습니다. 메타데이터는 csv파일형태로 업로드 하며 csv에는 'Filename' 속성에 파일 이름을 명시해야합니다. "

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
                <DicomUploadBox setdicomFilePathList={setdicomFilePathList}/>
                <MetaUploadBox setCsvFilePath={setCsvFilePath}/>
                <ErrorDescriptionBox 
                    nonSyncDicomFilePatientIDs={nonSyncDicomFilePatientIDs}
                    nonSyncCSVFilePatientIDs={nonSyncCSVFilePatientIDs}
                />
            </DialogContent>
            <DialogActions>
                <Button onClick={()=>handleDicomAndCsvSync(dicomFilePathList,csvFilePath)}>확인</Button>
                <Button onClick={()=>props.setOpen(false)}>취소</Button>
            </DialogActions>
        </Dialog>
    )
}