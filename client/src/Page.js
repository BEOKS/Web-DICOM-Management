import * as React from 'react';
import Box from '@mui/material/Box';
import CssBaseline from '@mui/material/CssBaseline';
import DicomTable from "./component/Table/DicomTable";
import UpDownloadToolbar from "./component/Toolbar/UpDownloadToolbar";
import { DrawerHeader } from './component/Drawer/ProjectDrawer';
import ProjectDrawer from './component/Drawer/ProjectDrawer'
import BaseAppBar from './component/AppBar/BaseAppBar';
import axios from 'axios';

axios.defaults.maxRedirects=0;
export default function Page() {
    const [open, setOpen] = React.useState(false);
    const [projects, setProjects] = React.useState([]);
    const [presentProject, setPresentProject] = React.useState({ projectName: 'Dicom' });
    const [metaData, setMetaData] = React.useState([]);
    const [metaDataUpdated, setMetaDataUpdated] = React.useState(false);
    const [checkFirst, setCheckFirst] = React.useState(true);

    const getProjects = () => {
        axios.get('api/Project',{maxRedirects:0})
            .then(response => {
                if (response.data.length !== 0) {
                    setProjects(response.data);
                    if (checkFirst) {
                        setPresentProject(response.data[0]);
                        setCheckFirst(false);
                    }
                }
            }).catch(error => {
                alert('서버가 응답하지 않습니다.')
                console.log(error);
            });
    };

    const getMetaData = (projectId) => {
        const url = `api/MetaData/${projectId}`;
        axios.get(url)
            .then(response => {
                setMetaData(response.data);
            }).catch(error => {
                console.log(error);
            });
    };

    const getNonReferenced = () => {
        const url = 'api/Patient/nonReferenced'
        axios.get(url)
            .then(response => {
                setMetaData(response.data);
            }).catch(error => {
                console.log(error);
            });
    };

    React.useEffect(() => {
        getProjects();
    }, [open]);

    React.useEffect(() => {
        presentProject.projectId 
        ? getMetaData(presentProject.projectId)
        : getNonReferenced();
    }, [presentProject, metaDataUpdated]);


    const handleDrawerOpen = () => {
        setOpen(true);
    };

    const handleDrawerClose = () => {
        setOpen(false);
    };
    
    return (
        <Box sx={{ display: 'flex' }}>
            <CssBaseline />
            <ProjectDrawer
                open={open}
                handleDrawerClose={handleDrawerClose}
                projects={projects}
                others={['Non-Reference Dicom']}
                setPresentProject={setPresentProject}
                setMetaData={setMetaData}
            />
            <BaseAppBar
                open={open}
                handleDrawerOpen={handleDrawerOpen}
                presentProjectName={presentProject.projectName}
            />
            <Box component="main" sx={{ flexGrow: 1, p: 3 }}>
                <DrawerHeader />
                <UpDownloadToolbar projects={presentProject} />
                <DicomTable 
                    data={metaData} 
                    metaDataUpdated={metaDataUpdated}
                    setMetaDataUpdated={setMetaDataUpdated}
                    isNonReferenced={presentProject.projectName === 'Non-Reference Dicom' ? true : false}
                    />
            </Box>
        </Box>
    );
}