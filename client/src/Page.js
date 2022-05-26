import * as React from 'react';
import Box from '@mui/material/Box';
import CssBaseline from '@mui/material/CssBaseline';
import { ButtonGroup, CircularProgress,Stack,Typography,Button } from '@mui/material';
import UpDownloadToolbar from "./component/Toolbar/UpDownloadToolbar";
import { DrawerHeader } from './component/Drawer/ProjectDrawer';
import ProjectDrawer from './component/Drawer/ProjectDrawer'
import BaseAppBar from './component/AppBar/BaseAppBar';
import LoadingPage from './component/Login/Loading';
import axios from 'axios';
import {useDispatch, useSelector} from "react-redux";
import {ParticipantInfoAction} from "./component/Toolbar/ProjectParticipant/ParticipantInfoReducer";
import VisualTable from './component/VisualTable/VisualTable';
import MetaDataGrid from './component/Table/MetaDataGrid';
import { getMetaData } from './api/metadata';
import { getCreatedProjects, getInvitedProjects } from './api/project';
axios.defaults.maxRedirects=0;

const VIEW_NAME={
    DICOM_TABLE: 'DicomTable',
    CHART: 'chart'
}
export default function Page() {
    const [open, setOpen] = React.useState(false);
    const [checkFirst, setCheckFirst] = React.useState(true);
    const [loading,setLoading] =React.useState(true);
    const [selectedView,setSelectedView]=React.useState(VIEW_NAME.DICOM_TABLE)

    const dispatch=useDispatch()
    const project = useSelector(state => state.ProjectDrawerReducer.project);
    const metaData = useSelector(state=>state.MetaDataGridReducer.metaData);
    const isInvitedProject = useSelector(state=>state.ProjectDrawerReducer.isInvitedProject);

    React.useEffect(() => {
        getCreatedProjects(dispatch, checkFirst, setCheckFirst, setLoading);
        getInvitedProjects(dispatch, setLoading);
    }, [open]);
    
    React.useEffect(() => {
        if(project.projectId){
            getMetaData(project, dispatch);
            dispatch(ParticipantInfoAction.setProjectId(project.projectId));
        }
    }, [project]);

    const handleDrawerOpen = () => {
        setOpen(true);
    };

    const handleDrawerClose = () => {
        setOpen(false);
    };
    if(loading){
        return <LoadingPage message={'사용자 정보를 가져오는 중입니다.'}/>
    }
    return (
        <Box sx={{ display: 'flex' }}>
            <CssBaseline />
            <ProjectDrawer
                open={open}
                setOpen={setOpen}
                openCreateProjectDialog={project.projectName==='현재 선택된 프로젝트가 없습니다.'}
            />
            <BaseAppBar
                open={open}
                handleDrawerOpen={handleDrawerOpen}
            />
            <Box component="main" sx={{ flexGrow: 1, p: 3 }}>
                <DrawerHeader/>
                <ButtonGroup variant="outlined" aria-label="outlined button group">
                    <Button onClick={()=>setSelectedView(VIEW_NAME.DICOM_TABLE)}>Data</Button>
                    <Button onClick={()=>setSelectedView(VIEW_NAME.CHART)}>Graph</Button>
                </ButtonGroup>
                {
                    selectedView===VIEW_NAME.CHART?
                    <VisualTable />
                    :
                        project.projectId ?
                        <div>
                            <UpDownloadToolbar projects={project} metaData={metaData} isInvitedProject={isInvitedProject}/>
                            {
                                metaData==='loading'?
                                <Stack alignItems="center" marginTop={2}>
                                    <CircularProgress margin={2}/>
                                    <Typography margin={2}>
                                        {'Loading Metadata...'}
                                    </Typography>
                                </Stack>
                                :
                                <MetaDataGrid />
                            }
                        </div>
                        :<div></div>
                }
            </Box>
        </Box>
    );
}