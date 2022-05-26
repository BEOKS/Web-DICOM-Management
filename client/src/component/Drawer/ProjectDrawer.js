import { styled } from '@mui/material/styles';
import MuiDrawer from '@mui/material/Drawer';
import List from '@mui/material/List';
import Divider from '@mui/material/Divider';
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import { ListItemButton } from '@mui/material';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import FolderOpenIcon from '@mui/icons-material/FolderOpen';
// import MoreIcon from '@mui/icons-material/More';
import AddCircleIcon from '@mui/icons-material/AddCircle';
import IconButton from '@mui/material/IconButton';
import { useTheme } from '@mui/material/styles';
import { Grid } from '@mui/material';
import { Dialog,DialogTitle,DialogContent,DialogContentText,DialogActions,TextField,Button,Typography } from '@mui/material';
import React, {useRef, useState} from 'react';
import { createProject } from './Utils/ProjectUtils';
import './ProjectDrawer.css';
import { useDispatch, useSelector } from 'react-redux';
import { MetaDataGridAction } from './../Table/MetaDataGridReducer';
import { ProjectDrawerAction } from './ProjectDrawerReducer';
import { VisualTableAction } from './../VisualTable/VisualTableReducer';

const SUCCESS=1,FAIL=0;

export const drawerWidth = 270;

const openedMixin = (theme) => ({
    width: drawerWidth,
    transition: theme.transitions.create('width', {
        easing: theme.transitions.easing.sharp,
        duration: theme.transitions.duration.enteringScreen,
    }),
    overflowX: 'hidden',
});

const closedMixin = (theme) => ({
    transition: theme.transitions.create('width', {
        easing: theme.transitions.easing.sharp,
        duration: theme.transitions.duration.leavingScreen,
    }),
    overflowX: 'hidden',
    width: `calc(${theme.spacing(7)} + 1px)`,
    [theme.breakpoints.up('sm')]: {
        width: `calc(${theme.spacing(9)} + 1px)`,
    },
});

export const DrawerHeader = styled('div')(({ theme }) => ({
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'flex-end',
    padding: theme.spacing(0, 1),
    // necessary for content to be below app bar
    ...theme.mixins.toolbar,
}));

export const Drawer = styled(MuiDrawer, { shouldForwardProp: (prop) => prop !== 'open' })(
    ({ theme, open }) => ({
        width: drawerWidth,
        flexShrink: 0,
        whiteSpace: 'nowrap',
        boxSizing: 'border-box',
        ...(open && {
            ...openedMixin(theme),
            '& .MuiDrawer-paper': openedMixin(theme),
        }),
        ...(!open && {
            ...closedMixin(theme),
            '& .MuiDrawer-paper': closedMixin(theme),
        }),
    })
);


export default function ProjectDrawer({open,setOpen,openCreateProjectDialog}) {
    const theme = useTheme();
    const [dialogOpen,setDialogOpen]=useState(openCreateProjectDialog);
    const [projectName,setProjectName]=useState();
    const dispatch = useDispatch();

    const presentProject = useSelector(state=>state.ProjectDrawerReducer.project);
    const createdProjects = useSelector(state=>state.ProjectDrawerReducer.createdProjects);
    const invitedProjects = useSelector(state=>state.ProjectDrawerReducer.invitedProjects);

    React.useEffect(()=>{
        setDialogOpen(openCreateProjectDialog)
    },[openCreateProjectDialog])
    let okButtonRef=useRef()
    const preventAdditionalClick=(action)=>{
        if(okButtonRef.current){
            okButtonRef.current.setAttribute("disabled", "disabled");
        }
        action()
    }
    const handleProjectCreateRequest=(status,message='')=>{
        if(status===FAIL){
            alert(message)
        }
        if(status===SUCCESS){
            setDialogOpen(false)
            setOpen(false)
            setOpen(true)
        }
    }

    return (
        <div>
            <Drawer variant="permanent" open={open}>
            <DrawerHeader>
                <IconButton onClick={()=>setOpen(false)}>
                    {theme.direction === 'rtl' ? <ChevronRightIcon /> : <ChevronLeftIcon />}
                </IconButton>
            </DrawerHeader>
            <Divider />
            {open && createdProjects.length > 0 && (
            <Typography className="category" variant="subtitle2" component="div">Created Projects</Typography>
            )}
            
            <List sx={{py: 0}}>
                {createdProjects.map((project) => (
                    <ListItemButton 
                    selected={presentProject.projectId===project.projectId}
                    key={project.projectId}
                    onClick={()=>{
                        dispatch(MetaDataGridAction.setSelectedRow([]));
                        dispatch(MetaDataGridAction.setSelectedMetaDataID([]));
                        dispatch(MetaDataGridAction.setSelectedStudyUID([]));
                        dispatch(ProjectDrawerAction.setProject(project));
                        dispatch(ProjectDrawerAction.markInvitedProject(false));
                        dispatch(VisualTableAction.setOptions([]));
                        }}>
                        <ListItemIcon>
                            <FolderOpenIcon />
                        </ListItemIcon>
                        <ListItemText primary={project.projectName} />
                    </ListItemButton>
                ))}
            </List>
            {createdProjects.length > 0 && <Divider />}
            {open && invitedProjects.length > 0 && (
            <Typography className="category" variant="subtitle2" component="div">Invited Projects</Typography>
            )}
            <List sx={{py: 0}}>
                {invitedProjects.map((project) => (
                    <ListItemButton 
                    selected={presentProject.projectId===project.projectId}
                    key={project.projectId}
                    onClick={()=>{
                        dispatch(MetaDataGridAction.setSelectedRow([]));
                        dispatch(MetaDataGridAction.setSelectedMetaDataID([]));
                        dispatch(MetaDataGridAction.setSelectedStudyUID([]));
                        dispatch(ProjectDrawerAction.setProject(project));
                        dispatch(ProjectDrawerAction.markInvitedProject(true));
                        dispatch(VisualTableAction.setOptions([]));

                        }}>
                        <ListItemIcon>
                            <FolderOpenIcon />
                        </ListItemIcon>
                        <ListItemText primary={project.projectName} />
                    </ListItemButton>
                ))}
            </List>
            {invitedProjects.length > 0 && <Divider />}
            <Grid
                container
                height='100%'
                alignItems='flex-end'
            >
                <List
                    sx={{
                        width: '100%',
                        color: 'primary.main',
                        fontWeight: 'bold'
                    }}
                >
                    <ListItemButton key='Add Project' onClick={()=>setDialogOpen(true)}>
                        <ListItemIcon>
                            <AddCircleIcon color='primary' />
                        </ListItemIcon>
                        <ListItemText primary='Add Project' />
                    </ListItemButton>
                </List>
            </Grid>
        </Drawer>
        <Dialog open={dialogOpen} onClose={()=>setDialogOpen(false)}>
            <DialogTitle>Create Project</DialogTitle>
            <DialogContent>
                <DialogContentText>
                    프로젝트 이름을 입력해주세요
                </DialogContentText>
                <TextField
                    autoFocus
                    margin="dense"
                    id="name"
                    label="Project name"
                    type="text"
                    fullWidth
                    onChange={(e)=>setProjectName(String(e.target.value))}
                    variant="standard"
                />
            </DialogContent>
            <DialogActions>
                <Button ref={okButtonRef} onClick={()=>preventAdditionalClick(()=>{createProject(projectName,handleProjectCreateRequest)})}>확인</Button>
                <Button onClick={()=>setDialogOpen(false)}>취소</Button>
            </DialogActions>
        </Dialog>
        </div>
    );
}