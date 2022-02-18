import { Dialog, DialogActions, DialogContent, Button } from '@mui/material';
import * as React from 'react';

export default function DeleteDialog(props) {
    const {open, setOpen} = props;

    const handleDeleteClick = () => {
        // axios delete
        setOpen(false);
    };

    const handleCancelClick = () => {
        setOpen(false);
    };

    return (
        <Dialog open={open}>
            <DialogContent>
                선택할 수 있는 이메일 목록 보여줄 예정
            </DialogContent>
            <DialogActions>
                <Button onClick={handleDeleteClick}>삭제</Button>
                <Button onClick={handleCancelClick}>취소</Button>
            </DialogActions>
        </Dialog>
    );
}