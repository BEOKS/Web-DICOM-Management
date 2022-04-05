import * as React from 'react'
import {Alert, Dialog, DialogActions, DialogContent, DialogTitle, Button} from "@mui/material";
import {useDispatch, useSelector} from "react-redux";
import {RootState} from "../../../../store";
import MetaUploadBox from "../UploadDialog/MetaUploadBox";
import {useState} from "react";
import {FileUploadDialogAction} from "./FileUploadDialogReducer";
import ImageFileUploadBox from "./ImageFileUploadBox";
import {uploadCsvFile, uploadImageFile} from "./Utils/UploadFiles";
import {SnackbarAction} from "../SnackbarReducer";

/**
 * 이미지 파일을 업르드하기 위한 컴퍼넌트입니다.
 * 메타데이터와 이미지 파일 리스트를 입력받으면
 * 메타데이터에 image_name 속성이 존재하는지 확인하고
 * 이미지 파일들의 이름이 image_name 속성에 존재하지 않다면
 * 업로드를 제한합니다. 위 조건이 충족되면 메타데이터와 이미지 파일이
 * 같이 업로드 됩니다.
 * @constructor
 */
export default function FileUploadDialog(){
    const open : boolean=useSelector((state: RootState)=>state.FileUploadDialogReducer.open)
    const [csvFile,setCsvFile]= useState(undefined)
    const [imageFiles,setImageFiles]=useState([])
    const dispatch=useDispatch()
    const projectId=useSelector((state:RootState)=> state.ParticipantInfoReducer.participants.projectId)
    const DEBUG=true
    const print=(msg : any)=>{
        if(DEBUG){
            console.log("FileUploadDialog",msg)
        }
    }

    print(csvFile)
    const uploadImageHandler=(filename:string, percentage : number)=>{
        dispatch(SnackbarAction.setProgress(percentage))
        if(percentage===100){
            dispatch(SnackbarAction.setMessage("Upload Image Complete"))
        }
        else{
            dispatch(SnackbarAction.setMessage(`Uploading ${filename}...`))
        }
    }
    const handleOk=()=>{
        uploadCsvFile(projectId,csvFile,()=>dispatch(SnackbarAction.setMessage("Upload CSV complete!")))
        uploadImageFile(projectId,imageFiles,uploadImageHandler)
        dispatch(FileUploadDialogAction.closeDialog())
        dispatch(SnackbarAction.closeSnackbar())
    }
    const handleClose=()=>{
        dispatch(SnackbarAction.closeSnackbar())
        setCsvFile(undefined)
        setImageFiles([])
        dispatch(FileUploadDialogAction.closeDialog())
    }
    return(
        <Dialog open={open}>
            <DialogTitle>
                이미지 파일 업로드
            </DialogTitle>
            <DialogContent>
                <Alert severity={"info"}>CSV에는 이미지 파일명을 명시하는 image_name 속성이 있어야 합니다.</Alert>
                <MetaUploadBox
                    csvFile={csvFile}
                    setCsvFile={setCsvFile}
                    setdicomFiles={setImageFiles}
                />
                <ImageFileUploadBox csvFile={csvFile} imageFiles={imageFiles} setImageFiles={setImageFiles}/>
            </DialogContent>
            <DialogActions>
                <Button onClick={handleOk}>확인</Button>
                <Button onClick={handleClose}>취소</Button>
            </DialogActions>
        </Dialog>
    )
}