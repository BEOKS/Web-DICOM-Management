import * as React from 'react';
import { Dialog, DialogContent, DialogActions, Button, Grid, TextField } from '@mui/material';

export default function InviteDialog(props) {
    const { open, setOpen } = props;

    const handleOKClick = () => {};
    const handleCancelClick = () => { setOpen(false) };

    return (
        <Dialog open={open}>
            <DialogContent>
                <Grid container columnSpacing={1} sx={{ pt: 1 }}>
                    <Grid item xs>
                        <TextField
                            label="Email"
                            helperText="이메일로 초대하기"
                            size="small"
                        />
                    </Grid>
                    <Grid item xs='auto'>
                        <Button variant="contained" sx={{ py: '7.75px' }}>초대</Button>
                    </Grid>
                </Grid>
            </DialogContent>
            <DialogActions>
                <Button onClick={handleOKClick}>확인</Button>
                <Button onClick={handleCancelClick}>취소</Button>
            </DialogActions>
        </Dialog>
    );
}