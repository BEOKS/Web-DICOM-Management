import * as React from 'react';
import {Button, CircularProgress, Typography} from '@mui/material';
import UploadBoxRow from './UploadBoxRow';
import { Stack } from '@mui/material';
import {useRef, useState} from "react";
const DicomUploadBox=({csvFile,dicomFiles,setdicomFiles,updatePossibility})=>{
    const [uploadState,setUploadState]=useState("wait");
    const inputFile = useRef(null)
    const handleChangeFile=(event)=>{
        console.log('handleChangeFile!!')
        const newFileArray=[...event.target.files].filter(file => Array.isArray(dicomFiles) && !dicomFiles.some( e => e.name===file.name))
        setUploadState("wait")
        setdicomFiles([...dicomFiles, ...newFileArray])
    }
    const handleDicomButtonClicked=()=>{
        setUploadState("loading")
        inputFile.current.click()
    }
    console.log('DicomUploadBox', updatePossibility);
    const checkSeverity=(index)=>{
        if(updatePossibility===undefined || updatePossibility.errorDicomPathList===undefined){
            return "success"
        }
        else if(updatePossibility.errorDicomPathList[index]){
            return "success"
        }
        else{
            return "error"
        }
    }
    if(csvFile===undefined){
        return(
            <div>
                <Stack borderRadius="5px" style={{alignItems: "center", backgroundColor:'#f5f5f5'}}
                    sx={{marginTop: '8px'}}>
                    <Typography color='#808080' margin>
                        Dicom 파일을 업로드 하기전 메타데이터를 업로드 해야 합니다.
                    </Typography>
                </Stack>
            </div>
        )
    }
    if(uploadState==="wait"){
        return(
            <Stack sx={{marginTop: '8px'}}>
                <Stack borderRadius="5px" style={{alignItems: "center", backgroundColor:'#f5f5f5'}}>
                    {Array.isArray(dicomFiles) && dicomFiles.map((path,index)=> (
                        <UploadBoxRow
                            key={path.name}
                            fileName={path.name}
                            severity={checkSeverity(index)}
                            dicomFiles={dicomFiles}
                            setdicomFiles={setdicomFiles}
                        />))}
                    <input
                        accept=".dcm"
                        style={{ display: 'none' }}
                        id="dicom-upload-input"
                        ref={inputFile}
                        multiple
                        type="file"
                        onChange={handleChangeFile}
                    />
                    <label htmlFor="dicom-upload-input">
                        <Button variant="raised" component="span" onClick={handleDicomButtonClicked}>
                            + Upload Dicoms
                        </Button>
                    </label>
                </Stack>
            </Stack>
        )
    }
    else{
        return (<CircularProgress/>)
    }
}
export default DicomUploadBox;