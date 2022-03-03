import { Alert, Button, Dialog, DialogActions, DialogContent, DialogTitle } from '@mui/material';
import * as React from 'react';
import axios from 'axios';

export default function ExitDialog(props) {
    const { open, setOpen, project } = props;
    const handleClickExit = () => {
        const url = `api/Project/${project.projectId}/oust`;

        axios.put(url)
            .then(response => {
                console.log(response);
            }).catch(error => {
                if (error.response) {
                    alert(error.response.data.message);
                    console.log(error.response.data);
                } else {
                    alert(error.message);
                    console.log(error);
                }
            }).finally(() => {
                setOpen(false);
                window.location.reload();
            });
    };

    const handleClickCancel = () => {
        setOpen(false);
    }

    return (
        <Dialog open={open}>
            <DialogTitle>프로젝트 나가기</DialogTitle>
            <DialogContent>
                <Alert severity='info'>현재 프로젝트를 나갑니다. 프로젝트 생성자에게 요청 시 다시 초대 받을 수 있습니다.</Alert>
            </DialogContent>
            <DialogActions>
                <Button onClick={handleClickExit}>나가기</Button>
                <Button onClick={handleClickCancel}>취소</Button>
            </DialogActions>
        </Dialog>
    );
}