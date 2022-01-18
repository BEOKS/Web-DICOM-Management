import * as React from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import axios from 'axios';

export default function DeleteNonReferenceDialog(props) {
    const { open, onClose, selected, metaDataUpdated, setMetaDataUpdated } = props;

    const deleteNonReferencedDicom = () => {
        selected.forEach(patientId => {
            const url = `api/Patient/nonReferenced/${patientId}`;
            axios.delete(url)
                .then(response => console.log(response))
                .catch(error => console.log(error));
        })
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
                    {"Dicom 파일 삭제"}
                </DialogTitle>
                <DialogContent>
                    메타 데이터와 매핑되지 않는 Dicom 파일을 삭제합니다.
                </DialogContent>
                <DialogActions>
                    <Button
                        autoFocus
                        onClick={() => {
                            deleteNonReferencedDicom();
                            setMetaDataUpdated(!metaDataUpdated);
                            onClose();
                        }}
                    >
                        확인
                    </Button>
                    <Button onClick={onClose}>취소</Button>
                </DialogActions>
            </Dialog>
        </div>
    );
}