import * as React from 'react';
import Box from "@mui/material/Box";
import { Grid } from '@mui/material';
import UploadButton from "./Upload/UploadButton";
import DeleteProjectButton from './DeleteProject/DeleteButton';
import InviteButton from './InviteUser/InviteButton';
import DeleteButton from './DeleteUser/DeleteButton';
import ParticipantInfo from "./ProjectParticipant/ParticipantInfo";
import SamplingButton from './Sampling/SamplingButton';
import MLButton from './MachineLearning/MLButton';
import { useSelector } from 'react-redux';
import { getMetaData } from '../../api/metadata';

export default function UpDownloadToolbar() {
    const project = useSelector(state => state.ProjectDrawerReducer.project);
    const isInvitedProject = useSelector(state=>state.ProjectDrawerReducer.isInvitedProject);
    const metaData = useSelector(state=>state.MetaDataGridReducer.metaData);

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
                {!isInvitedProject && (
                    <Grid item xs='auto'>
                        <InviteButton projectID={project.projectId} project={project} />
                    </Grid>
                )}
                <Grid item xs='auto'>
                    <DeleteButton project={project} isInvitedProject={isInvitedProject} />
                </Grid>
                <Grid item xs='auto'>
                    <ParticipantInfo />
                </Grid>
                <Grid item xs />
                <Grid item xs='auto'>
                    <UploadButton projects={project} getMetaData={getMetaData} metaData={metaData} />
                </Grid>
                <Grid item xs='auto'>
                    <SamplingButton />
                </Grid>
                <Grid item xs='auto'>
                    <MLButton getMetaData={getMetaData}/>
                </Grid>
                {!isInvitedProject && (
                    <Grid item xs='auto'>
                        <DeleteProjectButton projectID={project.projectId} />
                    </Grid>
                )}
            </Grid>
        </Box>
    );
}