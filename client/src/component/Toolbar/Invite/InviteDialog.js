import * as React from 'react';
import { Dialog, DialogContent, DialogActions, Button, Grid, TextField, Stack } from '@mui/material';
import { useState } from 'react';
import EmailListRow from './EmailListRow';

export default function InviteDialog(props) {
    const { open, setOpen } = props;
    const [email, setEmail] = useState('');
    const [emailArray, setEmailArray] = useState([]);

    const handleInviteClick = () => {
        const newEmailArray = emailArray.concat(email);
        setEmailArray(newEmailArray);
        setEmail('');
    };

    const handleOKClick = () => {
        setEmailArray([]);
        setEmail('');
        setOpen(false);
    };

    const handleCancelClick = () => {
        setEmailArray([]);
        setEmail('');
        setOpen(false);
    };

    return (
        <Dialog open={open}>
            <DialogContent>
                <Grid container columnSpacing={1} sx={{ pt: 1 }}>
                    <Grid item xs>
                        <TextField
                            autoFocus
                            label="Email"
                            helperText="이메일로 초대하기"
                            size="small"
                            onChange={e => { setEmail(e.target.value) }}
                            value={email}
                        />
                    </Grid>
                    <Grid item xs='auto'>
                        <Button onClick={handleInviteClick} variant="contained" sx={{ py: '7.75px' }}>초대</Button>
                    </Grid>
                </Grid>
                <Stack sx={{mt: 1}}>
                    {emailArray.map((email) => {
                        return (
                            <EmailListRow key={email} email={email} emailArray={emailArray} setEmailArray={setEmailArray}></EmailListRow>
                        );
                    })}
                </Stack>
            </DialogContent>
            <DialogActions>
                <Button onClick={handleOKClick}>확인</Button>
                <Button onClick={handleCancelClick}>취소</Button>
            </DialogActions>
        </Dialog>
    );
}