import * as React from 'react';
import { Button, Snackbar,CircularProgress } from '@mui/material';
import UploadDialog from './UploadDialog/UploadDialog';
import CircularProgressWithLabel from './UploadDialog/CircularProgressWithLabel';

export default function  UploadButton(){
    const [open, setOpen] = React.useState(false);
    const [snackbarInfo,setSnackBarInfo]=React.useState({});
    
    const handleClickOpen = () => {
        setOpen(true);
    };
    return(
        <div>
            <Button  onClick={handleClickOpen} variant="outlined">Upload</Button>
            <UploadDialog 
                open={open} 
                setOpen={setOpen}
                snackbarInfo={snackbarInfo}
                setSnackBarInfo={setSnackBarInfo} />
            <Snackbar
                key='DataLoadingMessenger'
                open={snackbarInfo.open}
                message={snackbarInfo.message}
                anchorOrigin={{ 'vertical':'bottom', 'horizontal':'right' }}
                action={snackbarInfo.progress ? <CircularProgressWithLabel value={snackbarInfo.progress}/> : <CircularProgress/>}
            />
        </div>
    )
}