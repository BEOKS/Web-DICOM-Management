import * as React from 'react';
import { Button } from '@mui/material';
import { useState } from 'react';
import DeleteDialog from './DeleteDialog';
export default function DeleteProjectButton({projectID}){
    const [dialogOpen,setDialogOpen]=useState(false);
    const handleOnClick=()=>setDialogOpen(true)
    return(
    <div>
        <Button onClick={handleOnClick} variant="outlined" sx={{ml: 1}}>Delete Project</Button>
        <DeleteDialog projectID={projectID} open={dialogOpen} setOpen={setDialogOpen}/>
    </div>)
}