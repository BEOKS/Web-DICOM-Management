import * as React from 'react';
import { Button } from '@mui/material';
import { useState } from 'react';
import PersonRemoveAlt1Icon from '@mui/icons-material/PersonRemoveAlt1';
import DeleteDialog from './DeleteDialog';

export default function InviteButton(props) {
    const { project } = props;
    const [dialogOpen, setDialogOpen] = useState(false);
    const handleOnClick = () => { setDialogOpen(true) };

    return (
        <div>
            <Button onClick={handleOnClick} variant="contained" startIcon={<PersonRemoveAlt1Icon />} sx={{ ml: 1 }}>Delete</Button>
            <DeleteDialog open={dialogOpen} setOpen={setDialogOpen} project={project} />
        </div>
    );
}