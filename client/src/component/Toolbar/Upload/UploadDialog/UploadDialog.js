import * as React from 'react'
import { Button, Dialog, DialogTitle, DialogContent,
    DialogActions,Alert  } from "@mui/material";
import { useState } from 'react';
import DicomUploadBox from './DicomUploadBox/DicomUploadBox';
import MetaUploadBox from './MetaUploadBox';
import FileHandler from '../Utils/FileHandler';

const dialogContentDescrptionText="메타데이터는 csv의 'PatientID' 속성에는 업로드하려는 Dicom 파일의 ID가 존재해야 합니다. "
const dicomUploadErrorMsg="업로드한 Dicom 파일을 확인해주세요 "

export default function UploadDialog({open,setOpen,snackbarInfo,setSnackBarInfo,fileHandler,projects,getMetaData}){
    const [dicomFiles, setdicomFiles]=useState([]);
    const [csvFile, setCsvFile]=useState();
    const [updatePossibility,setUpdatePossibility]=useState();
    if(fileHandler===undefined){
        fileHandler=new FileHandler(dicomFiles,csvFile,projects)
    }
    else{
        fileHandler.updateFilePath(dicomFiles,csvFile,projects);
    }
    // console.log('UploadDialog data',fileHandler)
    const haldleOKEvent= ()=>{
        setSnackBarInfo({...snackbarInfo,'open':true,'message':'Checking Upload Possibility ...','progress':undefined})
        fileHandler.loadFile(
            async (csvFile,dicomFileList)=>{
                const updatePossibility=await fileHandler.checkUpdatePossibility(csvFile,dicomFileList);
                setUpdatePossibility(updatePossibility);
                
                if( updatePossibility!==undefined && updatePossibility.state==='success'){
                    setOpen(false);
                    setSnackBarInfo({...snackbarInfo,'open':true,'message':'Uploading Files ...','progress':23})
                    await fileHandler.uploadFiles((progress,message,open=true)=>setSnackBarInfo({'message':message,'open':open,'progress':progress}));
                    getMetaData();
                    handleClearEvent();
                }   //123
                else if(updatePossibility.state=FileHandler.CSV_NOT_CONTAIN_PATIENT_ID){
                    setSnackBarInfo({...snackbarInfo,'open':true,'message':FileHandler.CSV_NOT_CONTAIN_PATIENT_ID,
                    'progress':false,'closeButtonOpen':true})
                }
                else{
                    setSnackBarInfo({...snackbarInfo,'open':false})
                }
            }
        );
    }
    const handleClearEvent=()=>{
        setOpen(false)
        setCsvFile(undefined)
        setdicomFiles([])
        setUpdatePossibility(undefined)
        if(snackbarInfo.progress===undefined){
            setSnackBarInfo({})
        }
    }
    return(
        <Dialog open={open}>
            <DialogTitle>Dicom 파일 업로드</DialogTitle>
            <DialogContent>
                <Alert severity="info" > 
                    {dialogContentDescrptionText}
                </Alert>
                {
                    updatePossibility!==undefined && updatePossibility.state==='error' && <Alert severity="error">{dicomUploadErrorMsg}</Alert>
                }
                <MetaUploadBox 
                    csvFile={csvFile}
                    setCsvFile={setCsvFile}
                    setdicomFiles={setdicomFiles}/>
                <DicomUploadBox
                    csvFile={csvFile}
                    dicomFiles={dicomFiles}
                    setdicomFiles={setdicomFiles}
                    updatePossibility={updatePossibility}/>
            </DialogContent>
            <DialogActions>
                <Button onClick={haldleOKEvent}>확인</Button>
                <Button onClick={handleClearEvent}>취소</Button>
            </DialogActions>
        </Dialog>
    )
}

