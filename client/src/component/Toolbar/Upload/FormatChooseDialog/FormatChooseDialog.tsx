import React from 'react'
import {Button, Dialog, DialogActions, DialogContent, DialogTitle} from "@mui/material";
import {useDispatch, useSelector} from "react-redux";
import {RootState} from "../../../../store";
import {FORMAT_TYPE, FormatChooseAction} from "./FormatChooseDialogReducer";
import FileUploadDialog from "../FileUploadDialog/FileUploadDialog";
import {FileUploadDialogAction} from "../FileUploadDialog/FileUploadDialogReducer";

const FormatChooseDialog = ()=>{
    const open: boolean=useSelector((state : RootState )=>state.FormatChooseReducer.open)
    const selectedFormat : FORMAT_TYPE | null = useSelector((state: RootState)=>state.FormatChooseReducer.selectedFormat)
    const dispatch=useDispatch()
    const handleButtonClick=(format : FORMAT_TYPE)=>()=>{
        dispatch(FormatChooseAction.selectFormat(format))
        if(format===FORMAT_TYPE.IMAGE){
            dispatch(FileUploadDialogAction.openDialog())
        }
        if(format===FORMAT_TYPE.DICOM){
            //TODO open dicom dialog
            alert('현재 이미지 파일 시스템 개발을 위해 일시적으로 Dicom 서비스를 중단하였습니다. 추후 복구 예정입니다.')
        }
        dispatch(FormatChooseAction.closeDialog())
    }
    const handleClose=()=>{
        dispatch(FormatChooseAction.closeDialog())
    }
    return (
        <div>
            <Dialog
                open={open}
                onClose={handleClose}
            >
                <DialogTitle>
                    {"Choose Upload Format"}
                </DialogTitle>
                <DialogContent>
                    <Button onClick={handleButtonClick(FORMAT_TYPE.DICOM)}>Dicom</Button>
                    <Button onClick={handleButtonClick(FORMAT_TYPE.IMAGE)}>JPEG,PNG</Button>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose}>취소</Button>
                </DialogActions>
            </Dialog>
            <FileUploadDialog/>
        </div>
    )
    // if (selectedFormat===null){
    //     return(
    //         <Dialog
    //             open={open}
    //             onClose={handleClose}
    //         >
    //             <DialogTitle>
    //                 {"Choose Upload Format"}
    //             </DialogTitle>
    //             <DialogContent>
    //                 <Button onClick={handleButtonClick(FORMAT_TYPE.DICOM)}>Dicom</Button>
    //                 <Button onClick={handleButtonClick(FORMAT_TYPE.IMAGE)}>JPEG,PNG</Button>
    //             </DialogContent>
    //             <DialogActions>
    //                 <Button onClick={handleClose}>취소</Button>
    //             </DialogActions>
    //         </Dialog>)
    // }
    // else if(selectedFormat===FORMAT_TYPE.DICOM){
    //     alert("dicom!")
    //     return(
    //         <div/>
    //     )
    // }
    // else{
    //     alert("image!")
    //     return(
    //         <FileUploadDialog/>
    //     )
    // }
}
export default FormatChooseDialog