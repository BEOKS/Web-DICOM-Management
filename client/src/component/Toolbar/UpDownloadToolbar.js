import * as React from 'react';
import Box from "@mui/material/Box";
import { Grid } from '@mui/material';
import UploadButton from "./Upload/UploadButton";
import DeleteProjectButton from './DeleteProject/DeleteButton';
import InviteButton from './Invite/InviteButton';

export default function UpDownloadToolbar({projects,getMetaData,metaData}) {
    return (
        <Box
            sx={{
                width: '100%',
                minWidth: 750,
                mt: 3,
                px: 3
            }}
        >
            <Grid
                container
                sx={{
                    width: '100%',
                    border: 'solid 1px #cfcfcf',
                    borderRadius: '4px',
                    padding: 1
                }}
            >
                <Grid item xs='auto'>
                    <InviteButton />
                </Grid>
                <Grid item xs></Grid>
                <Grid item xs='auto'>
                    <UploadButton projects={projects} getMetaData={getMetaData} metaData={metaData}/>
                </Grid>
                <Grid item xs='auto'>
                    <DeleteProjectButton projectID={projects.projectId}/>
                </Grid>
            </Grid>
        </Box>
    );
}