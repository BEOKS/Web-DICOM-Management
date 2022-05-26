import { useSelector, useDispatch } from 'react-redux';
import { RootState } from '../../store';
import { Alert, Button, Dialog, DialogActions, DialogContent, DialogTitle } from '@mui/material';
import axios from 'axios';
import { MetaDataGridAction } from './MetaDataGridReducer';
import { Project } from '../Drawer/ProjectDrawerReducer';

const DeleteRowDialog = () => {
    const dispatch = useDispatch();
    const selectedMetaDataID = useSelector((state: RootState) => state.MetaDataGridReducer.selectedMetaDataID);
    const selectedStudyUID = useSelector((state: RootState) => state.MetaDataGridReducer.selectedStudyUID);
    const deleteRowDialogOpen = useSelector((state: RootState) => state.MetaDataGridReducer.deleteRowDialogOpen);
    const project = useSelector((state: RootState) => state.ProjectDrawerReducer.project);

    const deleteMetaData = () => {
        const url = `api/MetaDataList/delete/${project.projectId}`;
        const data = selectedMetaDataID;
        axios.post(url, data)
            .then(response => console.log(response))
            .catch(error => {
                if (error.response) {
                    alert(error.response.data.message);
                    console.log(error.response.data);
                } else {
                    alert(error.message);
                    console.log(error);
                }
            });
    };

    const deleteDicom = () => {
        console.log(selectedStudyUID.length);
        console.log(selectedStudyUID);
        if (selectedStudyUID.length > 0) {
            selectedStudyUID.forEach(studyUID => {
                const url = `api/study/${studyUID}`;
                axios.delete(url)
                    .then(response => console.log(response))
                    .catch(error => {
                        if (error.response) {
                            alert(error.response.data.message);
                            console.log(error.response.data);
                        } else {
                            alert(error.message);
                            console.log(error);
                        }
                    });
            });
        }
    };

    const handleSubmit = () => {
        deleteMetaData();
        deleteDicom();
        //setMetaDataUpdated(!metaDataUpdated);
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