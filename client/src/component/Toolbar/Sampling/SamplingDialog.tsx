import { Alert, Button, Dialog, DialogActions, DialogContent, DialogTitle } from "@mui/material";
import axios from "axios";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../../../store";
import { SamplingAction } from "./SamplingReducer";
import { getMetaData } from "../../../api/metadata"

const SamplingDialog = () => {
    const dispatch = useDispatch();
    const open = useSelector((state: RootState) => state.SamplingReducer.dialogOpen);
    const project = useSelector((state: RootState) => state.ProjectDrawerReducer.project);

    const handleClickOK = () => {
        dispatch(SamplingAction.closeDialog());
        dispatch(SamplingAction.openSnackbar());

        const url = `api/MetaData/Sampling/${project.projectId}`;
        axios.put(url)
            .then(response => {
                console.log(response);
                dispatch(SamplingAction.updateSnackbar());
                getMetaData(project, dispatch);
            }).catch(error => {
                alert(error);
                console.log(error);
            });
    };

    const handleClickCancel = () => {
        dispatch(SamplingAction.closeDialog());
    };

    return (
        <Dialog open={open}>
            <DialogTitle>데이터 샘플링</DialogTitle>
            <DialogContent>
                <Alert severity='info'>이 프로젝트의 데이터 셋에 대해 <strong>랜덤 샘플링</strong>을 진행합니다.</Alert>
            </DialogContent>
            <DialogActions>
                <Button onClick={handleClickOK}>확인</Button>
                <Button onClick={handleClickCancel}>취소</Button>
            </DialogActions>
        </Dialog>
    );
}

export default SamplingDialog;