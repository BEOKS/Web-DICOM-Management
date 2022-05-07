import { Alert, Button, Dialog, DialogActions, DialogContent, DialogTitle, InputLabel, MenuItem, FormControl, Select } from "@mui/material";
import { SelectChangeEvent } from '@mui/material/Select';
import axios from "axios";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../../../store";
import { MLAction } from "./MLReducer";
import { inference } from '../../../api/MachineLearning/Management';


interface MLDialogProps {
    getMetaData: () => {}
}

const MLDialog: React.FC<MLDialogProps> = ({ getMetaData }) => {
    const dispatch = useDispatch();

    const open = useSelector((state: RootState) => state.MLReducer.dialogOpen);
    const projectId = useSelector((state: RootState) => state.ParticipantInfoReducer.participants.projectId);
    const modelList = useSelector((state: RootState) => state.MLReducer.modelList);
    const selectedModel = useSelector((state: RootState) => state.MLReducer.selectedModel);

    const handleChange = (event: SelectChangeEvent) => {
        dispatch(MLAction.setSelectedModel(event.target.value as string));
    };

    const handleClickOK = (selectedModel: string) => {
        dispatch(MLAction.closeDialog());
        // dispatch(MLAction.openSnackbar());

        // const url = `api/MetaData/MalignancyClassification/${projectId}`;
        // axios.put(url)
        //     .then(response => {
        //         console.log(response);
        //         dispatch(MLAction.updateSnackbar());
        //         getMetaData();
        //     }).catch(error => {
        //         alert(error);
        //         console.log(error);
        //     });

        inference(selectedModel);
    };

    const handleClickCancel = () => {
        dispatch(MLAction.closeDialog());
    };

    return (
        <Dialog open={open}>
            <DialogTitle>Choose ML model</DialogTitle>
            <DialogContent>
                <Alert severity='info'>Select the ML Inference for this project images</Alert>
                <FormControl fullWidth size="small" sx={{ mt: 2 }}>
                    <InputLabel id="ml-inference-select-label">ML Inference</InputLabel>
                    <Select
                        labelId="ml-inference-select-label"
                        id="ml-inference-select"
                        value={selectedModel}
                        label="ML Inference"
                        onChange={handleChange}
                    >
                        {modelList.map((value: string) => <MenuItem value={value} key={value}>{value}</MenuItem>)}
                    </Select>
                </FormControl>
            </DialogContent>
            <DialogActions>
                <Button onClick={() => handleClickOK(selectedModel)}>확인</Button>
                <Button onClick={handleClickCancel}>취소</Button>
            </DialogActions>
        </Dialog>
    );
}

export default MLDialog;