import * as React from 'react';
import { Button } from '@mui/material';
import { useState } from 'react';
import PersonRemoveAlt1Icon from '@mui/icons-material/PersonRemoveAlt1';

export default function InviteButton() {
    const [dialogOpen, setDialogOpen] = useState(false);
    const handleOnClick = () => {setDialogOpen(true)};

    return (
        <div>
            <Button onClick={handleOnClick} variant="contained" startIcon={<PersonRemoveAlt1Icon/>} sx={{ml: 1}}>Delete</Button>
            {/* 여기 다이얼로그 추가 예정 */}
        </div>
    );
}