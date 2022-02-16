import * as React from 'react';
import { Alert, IconButton } from '@mui/material';
import CloseIcon from '@mui/icons-material/Close';

export default function EmailListRow(props) {
    const { email, emailArray, setEmailArray } = props;

    return (
        <Alert severity="success"
            action={
                <IconButton
                    aria-label="close"
                    color="inherit"
                    size="small"
                    onClick={() => {
                        setEmailArray(emailArray.filter(i => i !== email));
                    }}
                >
                    <CloseIcon fontSize="inherit" />
                </IconButton>
            }
        >
            {email}

        </Alert>
    );
}