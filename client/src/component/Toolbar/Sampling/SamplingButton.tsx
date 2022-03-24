import React from 'react';
import { Button, CircularProgress, IconButton, Snackbar } from '@mui/material';
import { useDispatch, useSelector } from 'react-redux';
import { SamplingAction } from './SamplingReducer';
import SamplingDialog from './SamplingDialog';
import { RootState } from '../../../store';
import CloseIcon from '@mui/icons-material/Close';

interface SamplingButtonProps{
    getMetaData: ()=>{}
}
const SamplingButton : React.FC<SamplingButtonProps>=({getMetaData})=>{
    const dispatch = useDispatch();
    const snackbarInfo = useSelector((state: RootState) => state.SamplingReducer.snackbarInfo);

    const handleCloseSnackbar = () => {
        dispatch(SamplingAction.closeSnackbar());
    };

    const handleSamplingButtonClick = () => {
        dispatch(SamplingAction.openDialog());
    };

    const action = (
        <React.Fragment>
            {snackbarInfo.progress === 100 ?
                <IconButton
                    size="small"
                    aria-label="close"
                    color="inherit"
                    onClick={handleCloseSnackbar}
                >
                    <CloseIcon fontSize="small" />
                </IconButton>
                : <CircularProgress />}
        </React.Fragment>
    );

    return (
        <div>
            <Button onClick={handleSamplingButtonClick} variant="outlined" sx={{ ml: 1 }}>Sampling</Button>
            <SamplingDialog />
            <Snackbar
                key='SamplingMessenger'
                open={snackbarInfo.open}
                message={snackbarInfo.message}
                anchorOrigin={{ 'vertical': 'bottom', 'horizontal': 'right' }}
                action={action} />
        </div>
    );
}
export default SamplingButton