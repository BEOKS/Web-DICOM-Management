import * as React from 'react';
import { Box, CssBaseline, ButtonGroup, CircularProgress, Stack, Typography, Button } from '@mui/material';
import UpDownloadToolbar from "./component/Toolbar/UpDownloadToolbar";
import { DrawerHeader } from "./component/Drawer/ProjectDrawerStyling";
import ProjectDrawer from './component/Drawer/ProjectDrawer'
import BaseAppBar from './component/AppBar/BaseAppBar';
import LoadingPage from './component/Login/Loading';
import axios from 'axios';
import { useDispatch, useSelector } from "react-redux";
import { ParticipantInfoAction } from "./component/Toolbar/ProjectParticipant/ParticipantInfoReducer";
import MetadataStatisticInsightView from './component/VisualTable/VisualTable';
import MetaDataGrid from './component/Table/MetaDataGrid';
import { MetaDataGridAction } from './component/Table/MetaDataGridReducer';
import { getMetaData } from './api/metadata';
import { getCreatedProjects, getInvitedProjects } from './api/project';

axios.defaults.maxRedirects = 0;

const VIEW_NAME = {
    DICOM_TABLE: 'DicomTable',
    CHART: 'chart'
}
export default function Page() {
    const [checkFirst, setCheckFirst] = React.useState(true);
    const [loading, setLoading] = React.useState(true);
    const [selectedView, setSelectedView] = React.useState(VIEW_NAME.DICOM_TABLE)

    const dispatch = useDispatch()
    const openProjectDrawer = useSelector(state => state.ProjectDrawerReducer.openProjectDrawer);
    const project = useSelector(state => state.ProjectDrawerReducer.project);
    const metaData = useSelector(state => state.MetaDataGridReducer.metaData);

    React.useEffect(() => {
        getCreatedProjects(dispatch, checkFirst, setCheckFirst, setLoading);
        getInvitedProjects(dispatch, setLoading);
    }, [openProjectDrawer]);

    React.useEffect(() => {
        if (project.projectId) {
            getMetaData(project, (metaData)=>dispatch(MetaDataGridAction.setMetaData(metaData)));
            dispatch(ParticipantInfoAction.setProjectId(project.projectId));
        }
    }, [project]);

    if (loading) {
        return <LoadingPage message={'사용자 정보를 가져오는 중입니다.'} />
    }
    return (
        <Box sx={{ display: 'flex' }}>
            <CssBaseline />
            <ProjectDrawer />
            <BaseAppBar />
            <Box component="main" sx={{ flexGrow: 1, p: 3 }}>
                <DrawerHeader />
                <ButtonGroup variant="outlined" aria-label="outlined button group">
                    <Button onClick={() => setSelectedView(VIEW_NAME.DICOM_TABLE)}>Data</Button>
                    <Button onClick={() => setSelectedView(VIEW_NAME.CHART)}>Graph</Button>
                </ButtonGroup>
                {
                    selectedView === VIEW_NAME.CHART ?
                        <MetadataStatisticInsightView />
                        : project.projectId && <MetadataTableView metaData={metaData}/>
                }
            </Box>
        </Box>
    );
}
function MetadataTableView({metaData}) {
    return <div>
        <UpDownloadToolbar />
        {metaData === 'loading' ?
            <LoadingMessageView message={'Loading Metadata...'} />
            :
            <MetaDataGrid />}
    </div>;
}
function LoadingMessageView({message}) {
    return <Stack alignItems="center" marginTop={2}>
        <CircularProgress margin={2} />
        <Typography margin={2}>
            {message}
        </Typography>
    </Stack>;
}