import * as React from 'react';
import { Button } from '@mui/material';
import InviteDialog from './InviteDialog'
import { useState } from 'react';
import PersonAddAlt1Icon from '@mui/icons-material/PersonAddAlt1';

export default function InviteButton({projectID}) {
    const [dialogOpen, setDialogOpen] = useState(false);
    const handleOnClick = () => {setDialogOpen(true)};

    return (
        <div>
            <Button onClick={handleOnClick} variant="contained" startIcon={<PersonAddAlt1Icon/>}>Invite</Button>
            <InviteDialog open={dialogOpen} setOpen={setDialogOpen} projectID={projectID} />
        </div>
    );
}