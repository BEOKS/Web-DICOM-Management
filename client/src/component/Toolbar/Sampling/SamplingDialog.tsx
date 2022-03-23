import { Alert, Button, Dialog, DialogActions, DialogContent, DialogTitle } from "@mui/material";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../../../store";
import { SamplingAction } from "./SamplingReducer";

export default function SamplingDialog() {
    const dispatch = useDispatch();
    const open = useSelector((state: RootState) => state.SamplingReducer.dialogOpen);

    const handleClickOK = () => {
        dispatch(SamplingAction.closeDialog());
        dispatch(SamplingAction.openSnackbar());
        // sampling API request 예정
        setTimeout(() => { dispatch(SamplingAction.updateSnackbar()) }, 3000);
    };

    const handleClickCancel = () => {
        dispatch(SamplingAction.closeDialog());
    };

    return (
        <Dialog open={open}>
            <DialogTitle>데이터 샘플링</DialogTitle>
            <DialogContent>
                <Alert severity='info'>이 프로젝트의 데이터 셋에 대해 랜덤 샘플링을 진행합니다.</Alert>
            </DialogContent>
            <DialogActions>
                <Button onClick={handleClickOK}>확인</Button>
                <Button onClick={handleClickCancel}>취소</Button>
            </DialogActions>
        </Dialog>
    );
}