import * as React from 'react';
import { Button } from '@mui/material';
import { useState } from 'react';
import PersonRemoveAlt1Icon from '@mui/icons-material/PersonRemoveAlt1';
import DeleteDialog from './DeleteDialog';
import ExitDialog from './ExitDialog';

export default function InviteButton(props) {
    const { project, isInvitedProject } = props;
    const [dialogOpen, setDialogOpen] = useState(false);
    const handleOnClick = () => { setDialogOpen(true) };

    return (
        <div>
            <Button onClick={handleOnClick} variant="contained" startIcon={<PersonRemoveAlt1Icon />} sx={!isInvitedProject && { ml: 1 }}>
                {isInvitedProject ? "Exit" : "Delete"}
            </Button>
            {isInvitedProject ? (
                <ExitDialog open={dialogOpen} setOpen={setDialogOpen} />
            ) : (
                <DeleteDialog open={dialogOpen} setOpen={setDialogOpen} project={project} />
            )}
        </div>
    );
}