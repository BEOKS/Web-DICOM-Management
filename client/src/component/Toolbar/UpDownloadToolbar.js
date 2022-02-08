import * as React from 'react';
import Box from "@mui/material/Box";
import { Grid } from '@mui/material';
import UploadButton from "./Upload/UploadButton";
import DeleteProjectButton from './DeleteProject/DeleteButton';
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
                sx={{
                    width: '100%',
                    border: 'solid 1px #cfcfcf',
                    borderRadius: '4px',
                    px: 3,
                    py: 1,
                }}
                container 
                justifyContent="flex-end"
            >
                <UploadButton projects={projects} getMetaData={getMetaData} metaData={metaData}/>
                <DeleteProjectButton projectID={projects.projectId}/>
            </Grid>
        </Box>
    );
}