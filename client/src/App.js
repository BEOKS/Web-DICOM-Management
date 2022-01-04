import * as React from 'react';
import Box from '@mui/material/Box';
import CssBaseline from '@mui/material/CssBaseline';
import DicomTable from "./component/Table/DicomTable";
import UpDownloadToolbar from "./component/UpDownloadToolbar/UpDownloadToolbar";
import { DrawerHeader } from './component/Drawer/ProjectDrawer';
import ProjectDrawer from './component/Drawer/ProjectDrawer'
import BaseAppBar from './component/AppBar/BaseAppBar';

// 지금은 우선 로컬에서 메타데이터 불러오기
// import 후에 자동으로 JSON.parse 함수가 적용된 것처럼 동작함 (JavaScript Object type)
// import metadata from './metadata.json'
import metadata2 from './metadata2.json'

export default function Page() {
    const [open, setOpen] = React.useState(false);
    const [selectedPatientInfo, setSelectedPatientInfo] = React.useState([]);

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
            />
            <BaseAppBar 
                open={open}
                handleDrawerOpen={handleDrawerOpen}
            />
            <Box component="main" sx={{ flexGrow: 1, p: 3 }}>
                <DrawerHeader />
                <UpDownloadToolbar />
                <DicomTable
                    data={metadata2}
                    setSelectedRow={setSelectedPatientInfo}
                />
                { console.log(selectedPatientInfo) }
            </Box>
        </Box>
    );
}