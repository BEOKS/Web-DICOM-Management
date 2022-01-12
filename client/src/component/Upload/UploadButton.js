import * as React from 'react';
import { Button, Snackbar,CircularProgress,IconButton } from '@mui/material';
import CloseIcon from '@mui/icons-material/Close';
import UploadDialog from './UploadDialog/UploadDialog';
import CircularProgressWithLabel from './UploadDialog/CircularProgressWithLabel';

export default function  UploadButton({projects}){
    const [open, setOpen] = React.useState(false);
    const [snackbarInfo,setSnackBarInfo]=React.useState({});
    const handleClickOpen = () => {
        if(projects.projectId){
            setOpen(true);
        }
        else{
            alert('프로젝트를 선택해주세요.')
        }
    };
    const handleClose = () => {
        setSnackBarInfo({...snackbarInfo,'open':false});
      };
    const action = (
    <React.Fragment>
        {snackbarInfo.progress ? <CircularProgressWithLabel value={snackbarInfo.progress}/> : <CircularProgress/>}
        {snackbarInfo.progress===100 && <IconButton
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
            <UploadDialog 
                open={open} 
                setOpen={setOpen}
                snackbarInfo={snackbarInfo}
                setSnackBarInfo={setSnackBarInfo}
                projects={projects} />
            <Snackbar
                key='DataLoadingMessenger'
                open={snackbarInfo.open}
                message={snackbarInfo.message}
                anchorOrigin={{ 'vertical':'bottom', 'horizontal':'right' }}
                action={action}
            />
        </div>
    )
}