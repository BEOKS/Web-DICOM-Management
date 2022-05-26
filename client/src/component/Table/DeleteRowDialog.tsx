import { useSelector, useDispatch } from 'react-redux';
import { RootState } from '../../store';
import { Alert, Button, Dialog, DialogActions, DialogContent, DialogTitle } from '@mui/material';
import { MetaDataGridAction } from './MetaDataGridReducer';
import { getMetaData, deleteMetaData } from '../../api/metadata';
import { deleteDicom } from '../../api/dicom';

const DeleteRowDialog = () => {
    const dispatch = useDispatch();
    const selectedMetaDataID = useSelector((state: RootState) => state.MetaDataGridReducer.selectedMetaDataID);
    const selectedStudyUID = useSelector((state: RootState) => state.MetaDataGridReducer.selectedStudyUID);
    const deleteRowDialogOpen = useSelector((state: RootState) => state.MetaDataGridReducer.deleteRowDialogOpen);
    const project = useSelector((state: RootState) => state.ProjectDrawerReducer.project);

    const handleSubmit = () => {
        deleteMetaData(project, selectedMetaDataID);
        deleteDicom(selectedStudyUID);
        getMetaData(project, dispatch);
        onClose();
    };

    const onClose = () => {
        dispatch(MetaDataGridAction.closeDeleteRowDialog());
    };

    return (
        <div>
            <Dialog
                open={deleteRowDialogOpen}
                onClose={onClose}
                aria-labelledby="alert-dialog-title"
                aria-describedby="alert-dialog-description"
            >
                <DialogTitle id="alert-dialog-title">
                    Dicom 삭제
                </DialogTitle>
                <DialogContent>
                    <Alert severity="warning">선택한 행의 메타 데이터 및 Dicom 파일을 삭제합니다.</Alert>
                </DialogContent>
                <DialogActions>
                    <Button
                        autoFocus
                        type="submit"
                        onClick={() => handleSubmit()}
                    >
                        확인
                    </Button>
                    <Button onClick={() => onClose()}>취소</Button>
                </DialogActions>
            </Dialog>
        </div>
    );
}

export default DeleteRowDialog;