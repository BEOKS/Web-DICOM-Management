import { styled } from '@mui/material/styles';
import MuiDrawer from '@mui/material/Drawer';
import List from '@mui/material/List';
import Divider from '@mui/material/Divider';
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import ListItem from '@mui/material/ListItem';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import FolderOpenIcon from '@mui/icons-material/FolderOpen';
// import MoreIcon from '@mui/icons-material/More';
import AddCircleIcon from '@mui/icons-material/AddCircle';
import IconButton from '@mui/material/IconButton';
import { useTheme } from '@mui/material/styles';
import { Grid } from '@mui/material';
import { Dialog,DialogTitle,DialogContent,DialogContentText,DialogActions,TextField,Button,Typography } from '@mui/material';
import React, { useState } from 'react';
import { createProject } from './Utils/ProjectUtils';
import './ProjectDrawer.css';

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


export default function ProjectDrawer({open,setOpen,projects,invitedProjects,setIsInvitedProject,others,presentProject,setPresentProject,setMetaData,openCreateProjectDialog}) {
    const theme = useTheme();
    const [dialogOpen,setDialogOpen]=useState(openCreateProjectDialog);
    const [projectName,setProjectName]=useState();
    React.useEffect(()=>{
        setDialogOpen(openCreateProjectDialog)
    },[openCreateProjectDialog])
    const handleProjectCreateRequset=(status,message='')=>{
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
            {open && projects.length > 0 && (
            <Typography className="category" variant="subtitle2" component="div">Created Projects</Typography>
            )}
            
            <List sx={{py: 0}}>
                {projects.map((project) => (
                    <ListItem 
                    selected={presentProject===project}
                    button 
                    key={project.projectName}
                    onClick={()=>{
                        setPresentProject(project);
                        setIsInvitedProject(false);
                        }}>
                        <ListItemIcon>
                            <FolderOpenIcon />
                        </ListItemIcon>
                        <ListItemText primary={project.projectName} />
                    </ListItem>
                ))}
            </List>
            {projects.length > 0 && <Divider />}
            {open && invitedProjects.length > 0 && (
            <Typography className="category" variant="subtitle2" component="div">Invited Projects</Typography>
            )}
            <List sx={{py: 0}}>
                {invitedProjects.map((project) => (
                    <ListItem 
                    selected={presentProject===project}
                    button 
                    key={project.projectName}
                    onClick={()=>{
                        setPresentProject(project);
                        setIsInvitedProject(true);
                        }}>
                        <ListItemIcon>
                            <FolderOpenIcon />
                        </ListItemIcon>
                        <ListItemText primary={project.projectName} />
                    </ListItem>
                ))}
            </List>
            {invitedProjects.length > 0 && <Divider />}
            {/* <Divider />
            <List>
                {others.map((text) => (
                    <ListItem 
                        button 
                        key={text}
                        onClick={()=>setPresentProject({projectName: 'Non-Reference Dicom'})}
                        >
                        <ListItemIcon>
                            <MoreIcon />
                        </ListItemIcon>
                        <ListItemText primary={text} />
                    </ListItem>
                ))}
            </List> */}
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
                    <ListItem button key='Add Project' onClick={()=>setDialogOpen(true)}>
                        <ListItemIcon>
                            <AddCircleIcon color='primary' />
                        </ListItemIcon>
                        <ListItemText primary='Add Project' />
                    </ListItem>
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
                <Button onClick={()=>createProject(projectName,handleProjectCreateRequset)}>확인</Button>
                <Button onClick={()=>setDialogOpen(false)}>취소</Button>
            </DialogActions>
        </Dialog>
        </div>
    );
}