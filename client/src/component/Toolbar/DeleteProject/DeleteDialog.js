import * as React from 'react';
import { Dialog,DialogTitle,DialogContent,DialogActions,
        Button, Alert } from '@mui/material';
import axios from 'axios';

const dialogTitleString="프로젝트 삭제"
const dialogContentString="프로젝트를 삭제하면 다시 복구할 수 없습니다.\n정말로 삭제하시겠습니까?"
const dialogContentServity='warning'
const okButtonString="확인"
const deleteButtonString="취소"
const successMessage="프로젝트가 삭제 되었습니다."
const errorMessage="프로젝트 삭제 요청을 실패했습니다."

const refreshPage = ()=>{
    window.location.reload();
 }

const deleteProject=(projectID,setOpen)=>{
    axios.delete(`/api/Project/${projectID}`)
        .then(response=>{
            alert(successMessage)
        })
        .catch(error=>{
            alert(errorMessage+'\n'+error);
        })
        .finally(()=>{
            setOpen(false)
            refreshPage()
        })
    
}

export default function DeleteDialog({projectID,open,setOpen}){

    const haldleOKEvent=()=>deleteProject(projectID,setOpen)
    const handleClearEvent=()=>setOpen(false)
    return(
    <Dialog open={open}>
        <DialogTitle>{dialogTitleString}</DialogTitle>
        <DialogContent>
            <Alert severity={dialogContentServity} style={{whiteSpace: 'pre-wrap'}}>{dialogContentString}</Alert>
        </DialogContent>
        <DialogActions>
            <Button onClick={haldleOKEvent}>{okButtonString}</Button>
            <Button onClick={handleClearEvent}>{deleteButtonString}</Button>
        </DialogActions>
    </Dialog>)
}
