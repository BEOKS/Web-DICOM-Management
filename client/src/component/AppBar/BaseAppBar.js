import { styled } from '@mui/material/styles';
import MuiAppBar from '@mui/material/AppBar';
import { Box, Toolbar, Typography, IconButton, Button } from '@mui/material';
import MenuIcon from '@mui/icons-material/Menu';
import logo from './logo.png'
import { drawerWidth } from "../Drawer/ProjectDrawerStyling";
import { useDispatch, useSelector } from 'react-redux';
import { ProjectDrawerAction } from './../Drawer/ProjectDrawerReducer';

export const AppBar = styled(MuiAppBar, {
    shouldForwardProp: (prop) => prop !== 'open',
})(({ theme, open }) => ({
    zIndex: theme.zIndex.drawer + 1,
    transition: theme.transitions.create(['width', 'margin'], {
        easing: theme.transitions.easing.sharp,
        duration: theme.transitions.duration.leavingScreen,
    }),
    ...(open && {
        marginLeft: drawerWidth,
        width: `calc(100% - ${drawerWidth}px)`,
        transition: theme.transitions.create(['width', 'margin'], {
            easing: theme.transitions.easing.sharp,
            duration: theme.transitions.duration.enteringScreen,
        }),
    }),
}));

export default function BaseAppBar() {
    const dispatch = useDispatch();
    const project = useSelector(state => state.ProjectDrawerReducer.project);
    const openProjectDrawer = useSelector(state => state.ProjectDrawerReducer.openProjectDrawer);

    const onClickMenuButton = () => {
        dispatch(ProjectDrawerAction.openProjectDrawer());
    };

    return (
        <AppBar position="fixed" open={openProjectDrawer}>
            <Toolbar>
                <IconButton
                    color="inherit"
                    aria-label="open drawer"
                    onClick={() => onClickMenuButton()}
                    edge="start"
                    sx={{
                        marginRight: '36px',
                        ...(openProjectDrawer && { display: 'none' }),
                    }}
                >
                    <MenuIcon />
                </IconButton>
                <Typography variant="h6" noWrap component="div" sx={{ flexGrow: 2 }}>
                    {project.projectName}
                </Typography>
                <Button
                    color="inherit"
                    href="logout"
                    width={40}
                    ml={30}
                >
                    Logout
                </Button>
                <Box width={20}></Box>
                <img src={logo} height={40} alt="logo" />
            </Toolbar>
        </AppBar>
    );
}