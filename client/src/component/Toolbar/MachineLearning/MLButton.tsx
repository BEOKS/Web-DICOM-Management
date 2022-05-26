import React from 'react';
import { Button, CircularProgress, IconButton, Snackbar } from '@mui/material';
import { useDispatch, useSelector } from 'react-redux';
import { MLAction } from './MLReducer';
import MLDialog from './MLDialog';
import { RootState } from '../../../store';
import CloseIcon from '@mui/icons-material/Close';
import { getModelList } from '../../../api/MachineLearning/Management';

const MLButton = () => {
    const dispatch = useDispatch();
    const snackbarInfo = useSelector((state: RootState) => state.MLReducer.snackbarInfo);

    const handleCloseSnackbar = () => {
        dispatch(MLAction.closeSnackbar());
    };

    const handleMLButtonClick = () => {
        dispatch(MLAction.openDialog());
        getModelList((modelList: string[]) => { dispatch(MLAction.setModelList(modelList)) });
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
            <Button onClick={handleMLButtonClick} variant="outlined" sx={{ ml: 1 }}>ML</Button>
            <MLDialog />
            <Snackbar
                key='MLMessenger'
                open={snackbarInfo.open}
                message={snackbarInfo.message}
                anchorOrigin={{ 'vertical': 'bottom', 'horizontal': 'right' }}
                action={action} />
        </div>
    );
}
export default MLButton