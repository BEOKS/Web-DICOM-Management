import { Alert, Button, Dialog, DialogActions, DialogContent, DialogTitle } from "@mui/material";
import axios from "axios";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../../../store";
import { SamplingAction } from "./SamplingReducer";

interface SamplingDialogProps {
    getMetaData: () => {}
}

const SamplingDialog: React.FC<SamplingDialogProps> = ({ getMetaData }) => {
    const dispatch = useDispatch();
    const open = useSelector((state: RootState) => state.SamplingReducer.dialogOpen);
    const projectId = useSelector((state: RootState) => state.ParticipantInfoReducer.participants.projectId);

    const handleClickOK = () => {
        dispatch(SamplingAction.closeDialog());
        dispatch(SamplingAction.openSnackbar());
        
        const url = `api/MetaData/Sampling/${projectId}`;
        axios.put(url)
            .then(response => {
                console.log(response);
                dispatch(SamplingAction.updateSnackbar());
                getMetaData();
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