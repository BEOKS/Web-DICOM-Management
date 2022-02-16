import * as React from 'react';
import { Button } from '@mui/material';
import InviteDialog from './InviteDialog'
import { useState } from 'react';
import AddIcon from '@mui/icons-material/Add';

export default function InviteButton() {
    const [dialogOpen, setDialogOpen] = useState(false);
    const handleOnClick = () => {setDialogOpen(true)};

    return (
        <div>
            <Button onClick={handleOnClick} variant="contained" startIcon={<AddIcon/>}>Invite</Button>
            <InviteDialog open={dialogOpen} setOpen={setDialogOpen} />
        </div>
    );
}