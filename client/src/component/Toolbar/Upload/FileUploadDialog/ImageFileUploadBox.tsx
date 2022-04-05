import * as React from 'react'
import {Button, CircularProgress, Stack, Typography} from "@mui/material";
import {useState} from "react";
import UploadBoxRow from "../UploadDialog/DicomUploadBox/UploadBoxRow";
import {useDispatch} from "react-redux";
import {SnackbarAction} from "../SnackbarReducer";
const IntroduceMessage=()=>{
    return(
        <div>
            <Stack borderRadius="5px" style={{alignItems: "center", backgroundColor:'#f5f5f5'}}
                   sx={{marginTop: '8px'}}>
                <Typography color='#808080' margin={1}>
                    이미지 파일을 업로드 하기전 메타데이터를 업로드 해야 합니다.
                </Typography>
            </Stack>
        </div>
    )
}
type FileListBoxInput={
    imageFiles : File[],
    setImageFiles:any,
    setFileLoadingFromLocalStatus : any
}
enum FileLoadingFromLocalStatusType{WAIT , PROCESSING}

const FileListBox: React.FC<FileListBoxInput>=({imageFiles ,setImageFiles,setFileLoadingFromLocalStatus})=>{
    const handleChangeFile=(event:any)=>{
        const newFileArray=[...event.target.files].filter(
            file => Array.isArray(imageFiles) && !imageFiles.some( e => e.name===file.name))
        setFileLoadingFromLocalStatus("wait")
        setImageFiles([...imageFiles, ...newFileArray])
        setFileLoadingFromLocalStatus(FileLoadingFromLocalStatusType.WAIT)
    }
    return(
        <Stack sx={{marginTop: '8px'}}>
            <Stack borderRadius="5px" style={{alignItems: "center", backgroundColor:'#f5f5f5'}}>
                {Array.isArray(imageFiles) && imageFiles.map((path,index)=> (
                    <UploadBoxRow
                        key={path.name}
                        fileName={path.name}
                        severity={"success"}
                        dicomFiles={imageFiles}
                        setdicomFiles={setImageFiles}
                    />))}
                <input
                    accept="image/png, image/jpeg"
                    style={{ display: 'none' }}
                    id="image-upload-input"
                    multiple
                    type="file"
                    onChange={handleChangeFile}
                />
                <label htmlFor="image-upload-input">
                    <Button  component="span" onClick={()=>setFileLoadingFromLocalStatus(
                        FileLoadingFromLocalStatusType.PROCESSING)}>
                        + Upload Images
                    </Button>
                </label>
            </Stack>
        </Stack>
    )
}
type BoxInput ={
    csvFile : File | undefined,
    imageFiles : File[],
    setImageFiles : any
}
const ImageFileUploadBox: React.FC<BoxInput>=({csvFile,imageFiles,setImageFiles})=>{
    const [fileLoadingFromLocalStatus,setFileLoadingFromLocalStatus]=useState(FileLoadingFromLocalStatusType.WAIT)
    const dispatch=useDispatch()
    if (fileLoadingFromLocalStatus===FileLoadingFromLocalStatusType.PROCESSING){
        dispatch(SnackbarAction.setMessage("Loading Image Files..."))
        dispatch(SnackbarAction.openSnackbar())
    }
    else{
        dispatch(SnackbarAction.closeSnackbar())
    }
    if (csvFile===undefined){
        return(<IntroduceMessage/>)
    }
    else{
        return (
            <FileListBox
                imageFiles={imageFiles}
                setImageFiles={setImageFiles}
                setFileLoadingFromLocalStatus={setFileLoadingFromLocalStatus}
            />
        )
    }
}

export default ImageFileUploadBox;