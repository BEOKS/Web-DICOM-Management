import * as React from 'react';
import { Button } from '@mui/material';
import DownloadDialog from './DownloadDialog/DownlaodDialog';
export default DownloadButton=()=>{
    const [open, setOpen] = React.useState(false);

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };
    return(
        <div>
            <Button  onClick={handleClickOpen}>Upload</Button>
            <DownloadDialog open={open}/>
        </div>
    )
}