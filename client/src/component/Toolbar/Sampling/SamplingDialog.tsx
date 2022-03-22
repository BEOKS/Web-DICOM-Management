import { Alert, Button, Dialog, DialogActions, DialogContent, DialogTitle } from "@mui/material";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../../../store";
import { SamplingAction } from "./SamplingReducer";

export default function SamplingDialog() {
    const open = useSelector((state: RootState) => state.SamplingReducer.dialogStatus);
    const dispatch = useDispatch();

    const handleClickOK = () => {
        dispatch(SamplingAction.closeDialog());
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