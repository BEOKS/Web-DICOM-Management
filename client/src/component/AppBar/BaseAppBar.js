import { styled } from '@mui/material/styles';
import MuiAppBar from '@mui/material/AppBar';
import { drawerWidth } from '../Drawer/ProjectDrawer';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import IconButton from '@mui/material/IconButton';
import MenuIcon from '@mui/icons-material/Menu';
import Button from '@mui/material/Button';
import logo from './logo.png'
import { Box } from '@mui/material';

import { useSelector } from 'react-redux';

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

export default function BaseAppBar({open, handleDrawerOpen}) {
    const project = useSelector(state => state.ProjectDrawerReducer.project);

    return (
        <AppBar position="fixed" open={open}>
            <Toolbar>
                <IconButton
                    color="inherit"
                    aria-label="open drawer"
                    onClick={handleDrawerOpen}
                    edge="start"
                    sx={{
                        marginRight: '36px',
                        ...(open && { display: 'none' }),
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
                <img src={logo} height={40} alt="logo"/>
            </Toolbar>
        </AppBar>
    );
}