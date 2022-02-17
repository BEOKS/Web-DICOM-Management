import * as React from 'react';
import { Dialog, DialogContent, DialogActions, Button, Grid, TextField, Stack, Typography } from '@mui/material';
import { useState } from 'react';
import EmailListRow from './EmailListRow';
import './InviteDialog.css';

export default function InviteDialog(props) {
    const { open, setOpen } = props;
    const [email, setEmail] = useState('');
    const [emailArray, setEmailArray] = useState([]);
    const [isEmail, setIsEmail] = useState(true);
    const [alreadyExist, setAlreadyExist] = useState(false);

    const checkEmail = (email) => {
        const emailRegex =
            /^(([^<>()\[\].,;:\s@"]+(\.[^<>()\[\].,;:\s@"]+)*)|(".+"))@(([^<>()[\].,;:\s@"]+\.)+[^<>()[\].,;:\s@"]{2,})$/i;
        const result = emailRegex.test(email);
        setIsEmail(result);
        return result;
    };

    const checkAlreadyExist = (email) => {
        const exist = emailArray.some(i => i === email);
        setAlreadyExist(exist);
        return exist;
    };

    const handleInviteClick = () => {
        if (checkEmail(email) && !checkAlreadyExist(email)) {
            const newEmailArray = emailArray.concat(email);
            setEmailArray(newEmailArray);
            setEmail('');
        }
    };

    const handleOKClick = () => {
        setEmailArray([]);
        setEmail('');
        setIsEmail(true);
        setAlreadyExist(false);
        setOpen(false);
    };

    const handleCancelClick = () => {
        setEmailArray([]);
        setEmail('');
        setIsEmail(true);
        setAlreadyExist(false);
        setOpen(false);
    };

    return (
        <Dialog open={open}>
            <DialogContent>
                <Typography variant="subtitle2" gutterBottom component="div" color="#014361">
                    <span className="divider" />이메일로 초대하기
                </Typography>
                <Grid container columnSpacing={1}>
                    <Grid item xs>
                        <TextField
                            autoFocus
                            label="Email"
                            size="small"
                            onChange={e => { setEmail(e.target.value) }}
                            value={email}
                            error={(!isEmail && true) || (alreadyExist && true)}
                            helperText={(!isEmail && "이메일 형식이 아닙니다.") || (alreadyExist && "이미 초대한 계정입니다.")}
                        />
                    </Grid>
                    <Grid item xs='auto'>
                        <Button onClick={handleInviteClick} variant="contained" sx={{ py: '7.75px' }}>초대</Button>
                    </Grid>
                </Grid>
                {emailArray.length > 0 && (
                    <Typography variant="subtitle2" gutterBottom component="div" color="#014361" sx={{ mt: 3 }}>
                        <span className="divider" />프로젝트에 초대된 계정
                    </Typography>
                )}
                <Stack>
                    {emailArray.map((email, index) => {
                        return (
                            <EmailListRow key={email} email={email} index={index} emailArray={emailArray} setEmailArray={setEmailArray}></EmailListRow>
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