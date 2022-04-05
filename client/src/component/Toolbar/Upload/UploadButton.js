import * as React from 'react';
import { Button, Snackbar,CircularProgress,IconButton } from '@mui/material';
import CloseIcon from '@mui/icons-material/Close';
import DicomUploadDialog from './UploadDialog/DicomUploadDialog';
import {RootState} from "../../../store";
import CircularProgressWithLabel from './UploadDialog/CircularProgressWithLabel';
import ColumnTypeDecisionDialog from "./MetadataColumnTypeDecision/ColumnTypeDesicionDialog";
import FormatChooseDialog from "./FormatChooseDialog/FormatChooseDialog";
import {useDispatch, useSelector} from "react-redux";
import {FormatChooseAction} from "./FormatChooseDialog/FormatChooseDialogReducer";
import {SnackbarAction} from "./SnackbarReducer";

let fileHandler;
export default function  UploadButton({projects,getMetaData,metaData}){
    const snackbarOpen=useSelector((state)=>state.SnackbarReducer.open)
    const snackbarMessage=useSelector((state)=>state.SnackbarReducer.message)
    const snackbarProgress=useSelector((state)=>state.SnackbarReducer.progress)
    const snackbarCloseButtonOpen=useSelector((state)=>state.SnackbarReducer.closeButtonOpen)
    const dispatch=useDispatch()
    const handleClickOpen = () => {
        if(snackbarOpen){
            alert('업로드 과정이 진행중입니다.')
        }
        else if(projects.projectId){
            //setOpen(true);
            dispatch(FormatChooseAction.openDialog())
        }
        else{
            alert('프로젝트를 선택해주세요.')
        }
    };
    const handleClose = () => {
        //setSnackBarInfo({...snackbarInfo,'open':false});
        dispatch(SnackbarAction.closeSnackbar())
      };
    const action = (
    <React.Fragment>
        {snackbarProgress ?
            <CircularProgressWithLabel value={snackbarProgress}/>
            : snackbarProgress===false ? <div/> :<CircularProgress/>}
        {(snackbarProgress===100 || snackbarCloseButtonOpen) && <IconButton
        size="small"
        aria-label="close"
        color="inherit"
        onClick={handleClose}
        >
            <CloseIcon fontSize="small" />
        </IconButton>}
    </React.Fragment>
    );
    return(
        <div>
            <Button  onClick={handleClickOpen} variant="outlined">Upload</Button>
            {/*<DicomUploadDialog */}
            {/*    open={open} */}
            {/*    setOpen={setOpen}*/}
            {/*    snackbarInfo={snackbarInfo}*/}
            {/*    setSnackBarInfo={setSnackBarInfo}*/}
            {/*    fileHandler={fileHandler}*/}
            {/*    projects={projects}*/}
            {/*    getMetaData={getMetaData}*/}
            {/*    metaData={metaData}*/}
            {/*/>*/}
            <FormatChooseDialog/>
            <Snackbar
                key='DataLoadingMessenger'
                open={snackbarOpen}
                message={snackbarMessage}
                anchorOrigin={{ 'vertical':'bottom', 'horizontal':'right' }}
                action={action}
            />
            <ColumnTypeDecisionDialog/>
        </div>
    )
}