import { Alert, Button, Dialog, DialogActions, DialogContent, DialogTitle } from '@mui/material';
import * as React from 'react';

export default function ExitDialog(props) {
    const { open, setOpen } = props;
    const handleClickExit = () => {
        setOpen(false);
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