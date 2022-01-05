import * as React from 'react'
import { Button, Dialog, DialogTitle, DialogContent,
    DialogActions,Alert, Snackbar, CircularProgress  } from "@mui/material";
import { useState } from 'react';
import DicomUploadBox from './DicomUploadBox/DicomUploadBox';
import MetaUploadBox from './MetaUploadBox';
import FileHandler from '../Utils/FileHandler';

const dialogContentDescrptionText="메타데이터는 csv의 'PatientID' 속성에는 업로드하려는 Dicom 파일의 ID가 존재해야 합니다. "
const dicomUploadErrorMsg="업로드한 Dicom 파일을 확인해주세요 "

let fileHandler;
export default function UploadDialog(props){
    console.log('Build UploadDialog Component.')
    const [dicomFiles, setdicomFiles]=useState([]);
    const [csvFile, setCsvFile]=useState();
    const [updatePossibility,setUpdatePossibility]=useState();
    const [snackbarInfo,setSnackBarInfo]=useState({});
    if(fileHandler===undefined){
        fileHandler=new FileHandler(dicomFiles,csvFile)
    }
    else{
        fileHandler.updateFilePath(dicomFiles,csvFile);
    }

    const haldleOKEvent=()=>{
        setSnackBarInfo({...snackbarInfo,'open':true,'message':'Checking Upload Possibility ...'})
        fileHandler.loadFile(
            (csvFile,dicomFileList)=>{
                setUpdatePossibility(fileHandler.checkUpdatePossibility(csvFile,dicomFileList))
                setSnackBarInfo({...snackbarInfo,'open':false})
            }
        );
    }
    const handleClearEvent=()=>{
        props.setOpen(false)
        setCsvFile(undefined)
        setdicomFiles(new Set([]))
        setUpdatePossibility(undefined)
        setSnackBarInfo({})
    }
    return(
        <Dialog open={props.open}>
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
            <Snackbar
                open={snackbarInfo.open}
                message={snackbarInfo.message}
                anchorOrigin={{ 'vertical':'bottom', 'horizontal':'right' }}
                key={'bottomright'}
                action={<CircularProgress />}
            />
        </Dialog>
    )
}