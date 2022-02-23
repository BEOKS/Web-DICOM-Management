import * as React from 'react';
import { Dialog, DialogContent, DialogActions, Button, Grid, TextField, Stack, Typography } from '@mui/material';
import { useState } from 'react';
import EmailListRow from './EmailListRow';
import '../UpDownloadToolbar.css';
import axios from 'axios'

export default function InviteDialog(props) {
    const { open, setOpen, projectID, project } = props;
    const [email, setEmail] = useState('');
    const [emailArray, setEmailArray] = useState([]);
    const [isEmail, setIsEmail] = useState(true);
    const [alreadyAdded, setAlreadyAdded] = useState(false);
    const [alreadyInvited, setAlreadyInvited] = useState(false);

    const checkEmail = (email) => {
        const emailRegex =
            /^(([^<>()\[\].,;:\s@"]+(\.[^<>()\[\].,;:\s@"]+)*)|(".+"))@(([^<>()[\].,;:\s@"]+\.)+[^<>()[\].,;:\s@"]{2,})$/i;
        const result = emailRegex.test(email);
        setIsEmail(result);
        return result;
    };

    const checkAlreadyAdded = (email) => {
        const added = emailArray.some(i => i === email);
        setAlreadyAdded(added);
        return added;
    };

    const checkAlreadyInvited = (email) => {
        const invited = project.visitor.some(visitor => visitor.email === email);
        setAlreadyInvited(invited);
        return invited;
    };

    const handleAddClick = () => {
        if (checkEmail(email) && !checkAlreadyAdded(email) && !checkAlreadyInvited(email)) {
            const newEmailArray = emailArray.concat(email);
            setEmailArray(newEmailArray);
            setEmail('');
        }
    };

    const handleInviteClick = () => {
        const data = { emailList: emailArray };
        const url = `api/Project/${projectID}/invite`;

        axios.put(url, data)
            .then(response => {
                console.log(response);
            }).catch(error => {
                if (error.response) {
                    alert(error.response.data.message + '\n초대에 실패한 이메일: ' + error.response.data.failList);
                    console.log(error.response.data);
                } else {
                    alert(error.message);
                    console.log(error);
                }
            }).finally(() => {
                setEmailArray([]);
                setEmail('');
                setIsEmail(true);
                setAlreadyAdded(false);
                setAlreadyInvited(false);
                setOpen(false);
                window.location.reload();
            });
    };

    const handleCancelClick = () => {
        setEmailArray([]);
        setEmail('');
        setIsEmail(true);
        setAlreadyAdded(false);
        setAlreadyInvited(false);
        setOpen(false);
    };

    return (
        <Dialog open={open}>
            <DialogContent>
                <Typography variant="subtitle2" gutterBottom component="div" color="#014361">
                    <span className="divider" />이메일로 추가하기
                </Typography>
                <Grid container columnSpacing={1}>
                    <Grid item xs>
                        <TextField
                            autoFocus
                            label="Email"
                            size="small"
                            onChange={e => { setEmail(e.target.value) }}
                            value={email}
                            error={(!isEmail && true) || (alreadyAdded && true) || (alreadyInvited && true)}
                            helperText={(!isEmail && "올바른 이메일 형식이 아닙니다.") || (alreadyAdded && "이미 추가한 계정입니다.")
                                || (alreadyInvited && "이미 초대한 계정입니다.")}
                        />
                    </Grid>
                    <Grid item xs='auto'>
                        <Button onClick={handleAddClick} variant="contained" sx={{ py: '7.75px' }}>추가</Button>
                    </Grid>
                </Grid>
                {emailArray.length > 0 && (
                    <Typography variant="subtitle2" gutterBottom component="div" color="#014361" sx={{ mt: 3 }}>
                        <span className="divider" />프로젝트에 초대할 계정
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
                <Button onClick={handleInviteClick} disabled={emailArray.length === 0}>초대</Button>
                <Button onClick={handleCancelClick}>취소</Button>
            </DialogActions>
        </Dialog>
    );
}