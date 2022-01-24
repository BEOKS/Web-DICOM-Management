import * as React from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import Radio from '@mui/material/Radio';
import RadioGroup from '@mui/material/RadioGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import FormControl from '@mui/material/FormControl';
import FormLabel from '@mui/material/FormLabel';
import Alert from '@mui/material/Alert';
import axios from 'axios';

export default function DeleteRowDialog(props) {
    const [value, setValue] = React.useState('');
    const { open, onClose, selected, selectedPatientIDList, metaDataUpdated, setMetaDataUpdated } = props;

    const deleteMetaData = () => {
        selected.forEach(metadataId => {
            const url = `api/MetaData/${metadataId}`;
            axios.delete(url)
                .then(response => console.log(response))
                .catch(error => console.log(error));
        });
    };

    const deleteDicom = () => {
        selectedPatientIDList.forEach(patientId => {
            const url = `api/patient/${patientId}`;
            axios.delete(url)
                .then(response => console.log(response))
                .catch(error => console.log(error));
        })
    };

    const handleRadioChange = (event) => {
        setValue(event.target.value);
    };

    const handleSubmit = (event) => {
        // event.preventDefault();
        deleteMetaData();

        if (value === 'metaDataAndDicom') {
            deleteDicom();
        }
        setMetaDataUpdated(!metaDataUpdated);
        onClose();
    };

    return (
        <div>
            <Dialog
                open={open}
                onClose={onClose}
                aria-labelledby="alert-dialog-title"
                aria-describedby="alert-dialog-description"
            >
                <DialogTitle id="alert-dialog-title">
                    {"선택한 행 삭제"}
                </DialogTitle>
                <DialogContent>
                    <Alert severity="info">메타 데이터만 삭제할 경우, 해당 메타 데이터와 매핑되었던 Dicom 파일은
                        <br />Non-Reference Dicom 카테고리에서 확인할 수 있습니다.</Alert>
                    <FormControl component="fieldset" sx={{ mt: 3 }}>
                        <FormLabel component="legend">삭제 옵션을 선택해주세요.</FormLabel>
                        <RadioGroup
                            aria-label="gender"
                            defaultValue="onlyMetaData"
                            name="radio-buttons-group"
                            value={value}
                            onChange={handleRadioChange}
                        >
                            <FormControlLabel value="onlyMetaData" control={<Radio />} label="메타 데이터 삭제" />
                            <FormControlLabel value="metaDataAndDicom" control={<Radio />} label="메타 데이터와 Dicom 파일 모두 삭제" />
                        </RadioGroup>
                    </FormControl>
                </DialogContent>
                <DialogActions>
                    <Button
                        autoFocus
                        type="submit"
                        onClick={handleSubmit}
                    >
                        확인
                    </Button>
                    <Button onClick={onClose}>취소</Button>
                </DialogActions>
            </Dialog>
        </div>
    );
}