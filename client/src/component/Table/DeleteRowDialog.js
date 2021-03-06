import * as React from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import axios from 'axios';

export default function DeleteRowDialog(props) {
    const { open, onClose, selected, selectedStudyUIDList, metaDataUpdated, setMetaDataUpdated, project } = props;

    const deleteMetaData = () => {
        const url = `api/MetaDataList/delete/${project.projectId}`;
        const data = selected;
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
        selectedStudyUIDList.forEach(studyUID => {
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
        })
    };

    const handleSubmit = (event) => {
        deleteMetaData();
        deleteDicom();
        setMetaDataUpdated(!metaDataUpdated);
        onClose();
    };

    return (
        <div>
            <Dialog
                open={open}
                onClose={onClose}
                aria-labelledby="alert-dialog-title"
                aria-describedby="alert-dialog-description"
            >
                <DialogTitle id="alert-dialog-title">
                    {"Dicom ??????"}
                </DialogTitle>
                <DialogContent>
                    ????????? ?????? ?????? ????????? ??? Dicom ????????? ???????????????.
                </DialogContent>
                <DialogActions>
                    <Button
                        autoFocus
                        type="submit"
                        onClick={handleSubmit}
                    >
                        ??????
                    </Button>
                    <Button onClick={onClose}>??????</Button>
                </DialogActions>
            </Dialog>
        </div>
    );
}