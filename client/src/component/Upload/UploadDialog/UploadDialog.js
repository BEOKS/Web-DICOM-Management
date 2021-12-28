import * as React from 'react'
import { Button, Dialog, DialogTitle, DialogContent,DialogActions,
     Stack,DialogContentText } from "@mui/material";
import { useState } from 'react';
import DicomUploadBox from './DicomUploadBox';
import MetaUploadBox from './MetaUploadBox';

const dialogContentDescrptionText="Dicom 파일과 메타데이터를 업로드 할 수 있습니다. 메타데이터는 csv파일형태로 업로드 하며 csv에는 'Filename' 속성에 파일 이름을 명시해야합니다. "

function UploadComponent(){

}


const handleDicomAndCsvSync=(dicomFilePathList,csvFilePath)=>{

}

export default function UploadDialog(props){
    const [dicomFilePathList, setdicomFilePathList]=useState([]);
    const [csvFilePath, setCsvFilePath]=useState();
    
    return(
        <Dialog open={props.open}>
            <DialogTitle>Dicom 파일 업로드</DialogTitle>
            <DialogContent>
                <DialogContentText> 
                    {dialogContentDescrptionText}
                </DialogContentText>
                <DicomUploadBox/>
                <MetaUploadBox/>
            </DialogContent>
            <DialogActions>
                <Button onClick={()=>handleDicomAndCsvSync(dicomFilePathList,csvFilePath)}>확인</Button>
                <Button onClick={()=>props.setOpen(false)}>취소</Button>
            </DialogActions>
        </Dialog>
    )
}