import { Alert, Button, Dialog, DialogActions, DialogContent, DialogTitle } from "@mui/material";
import axios from "axios";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../../../store";
import { MLAction } from "./MLReducer";

interface MLDialogProps {
    getMetaData: () => {}
}

const MLDialog: React.FC<MLDialogProps> = ({ getMetaData }) => {
    const dispatch = useDispatch();
    const open = useSelector((state: RootState) => state.MLReducer.dialogOpen);
    const projectId = useSelector((state: RootState) => state.ParticipantInfoReducer.participants.projectId);

    const handleClickOK = () => {
        dispatch(MLAction.closeDialog());
        dispatch(MLAction.openSnackbar());

        const url = `api/MetaData/MalignancyClassification/${projectId}`;
        axios.put(url)
            .then(response => {
                console.log(response);
                dispatch(MLAction.updateSnackbar());
                getMetaData();
            }).catch(error => {
                alert(error);
                console.log(error);
            });
    };

    const handleClickCancel = () => {
        dispatch(MLAction.closeDialog());
    };

    return (
        <Dialog open={open}>
            <DialogTitle>머신러닝 추론</DialogTitle>
            <DialogContent>
                <Alert severity='info'>이 프로젝트의 데이터 셋에 대해 <strong>머신러닝 추론</strong>을 진행합니다.</Alert>
            </DialogContent>
            <DialogActions>
                <Button onClick={handleClickOK}>확인</Button>
                <Button onClick={handleClickCancel}>취소</Button>
            </DialogActions>
        </Dialog>
    );
}

export default MLDialog;