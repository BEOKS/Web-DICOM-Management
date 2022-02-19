import * as React from 'react';
import { Dialog, DialogActions, DialogContent, Button, List, ListItem, ListItemButton, ListItemIcon, ListItemText, Checkbox, Typography } from '@mui/material';
import '../UpDownloadToolbar.css';

export default function DeleteDialog(props) {
    const { open, setOpen, project } = props;
    const [checked, setChecked] = React.useState([]);

    const handleDeleteClick = () => {
        // axios delete
        setOpen(false);
    };

    const handleCancelClick = () => {
        setOpen(false);
    };

    const handleToggle = (value) => () => {
        const currentIndex = checked.indexOf(value);
        const newChecked = [...checked];

        if (currentIndex === -1) {
            newChecked.push(value);
        } else {
            newChecked.splice(currentIndex, 1);
        }

        setChecked(newChecked);
        console.log(newChecked);
    };

    return (
        <Dialog open={open}>
            <DialogContent>
                <Typography variant="subtitle2" gutterBottom component="div" color="#014361">
                    <span className="divider" />현재 프로젝트에 초대된 계정
                </Typography>
                <List className="invitedEmailList" sx={{ py: 0 }}>
                    {console.log(project)}
                    {project && project.visitor.map((visitor, index) => {
                        const labelId = `checkbox-list-label-${visitor.email}`;

                        return (
                            <ListItem
                                key={visitor.email}
                                disablePadding
                                divider={project.visitor.length > 0 && index !== project.visitor.length - 1}
                            >
                                <ListItemButton role={undefined} onClick={handleToggle(visitor.email)} dense sx={{ py: 0 }}>
                                    <ListItemIcon>
                                        <Checkbox
                                            edge="start"
                                            checked={checked.indexOf(visitor.email) !== -1}
                                            tabIndex={-1}
                                            disableRipple
                                            inputProps={{ 'aria-labelledby': labelId }}
                                        />
                                    </ListItemIcon>
                                    <ListItemText id={labelId} primary={visitor.email} />
                                </ListItemButton>
                            </ListItem>
                        );
                    })}
                </List>
            </DialogContent>
            <DialogActions>
                <Button onClick={handleDeleteClick}>삭제</Button>
                <Button onClick={handleCancelClick}>취소</Button>
            </DialogActions>
        </Dialog>
    );
}