import { Dialog, DialogTitle, DialogContent, DialogContentText, DialogActions, TextField, Button } from '@mui/material';
import { createProject } from './Utils/ProjectUtils';
import { useRef } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { ProjectDrawerAction } from './ProjectDrawerReducer';
import { RootState } from '../../store';
import { useEffect } from 'react';

const SUCCESS = 1, FAIL = 0;

const CreateProjectDialog = () => {
    const dispatch = useDispatch();
    const openCreateProjectDialog = useSelector((state: RootState) => state.ProjectDrawerReducer.openCreateProjectDialog);
    const newProjectName = useSelector((state: RootState) => state.ProjectDrawerReducer.newProjectName);
    const project = useSelector((state: RootState) => state.ProjectDrawerReducer.project);
    const okButtonRef: React.MutableRefObject<boolean> = useRef(true);

    useEffect(() => {
        if (project.projectName === undefined) {
            dispatch(ProjectDrawerAction.openCreateProjectDialog());
        } else {
            dispatch(ProjectDrawerAction.closeCreateProjectDialog());
        }
    }, [project]);

    const preventAdditionalClick = (action: () => void) => {
        if (!okButtonRef.current) {
            okButtonRef.current = true;
        }
        action();
    };

    const handleProjectCreateRequest = (status: number, message = '') => {
        if (status === FAIL) {
            alert(message);
        }
        if (status === SUCCESS) {
            dispatch(ProjectDrawerAction.closeCreateProjectDialog());
            dispatch(ProjectDrawerAction.closeDrawer());
            dispatch(ProjectDrawerAction.openDrawer());
        }
    };

    const handleClickOK = () => {
        preventAdditionalClick(() => { createProject(newProjectName, handleProjectCreateRequest) })
    };

    const handleClickCancel = () => {
        dispatch(ProjectDrawerAction.closeCreateProjectDialog());
    };

    return (
        <Dialog open={openCreateProjectDialog} onClose={() => handleClickCancel()}>
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
                    variant="standard"
                    fullWidth
                    onChange={(e) => {
                        dispatch(ProjectDrawerAction.setNewProjectName(String(e.target.value)));
                        if (okButtonRef.current) {
                            okButtonRef.current = false;
                        }
                    }}
                />
            </DialogContent>
            <DialogActions>
                <Button disabled={okButtonRef.current} onClick={() => handleClickOK()}>확인</Button>
                <Button onClick={() => handleClickCancel()}>취소</Button>
            </DialogActions>
        </Dialog>
    );
};

export default CreateProjectDialog;