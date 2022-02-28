import * as React from 'react'
import {useSelector, useDispatch} from "react-redux";
import {RootState} from "../../../../store";
import {
    Dialog, DialogTitle, DialogContent, Box,
    DialogActions, Button, Select, FormControl, InputLabel, MenuItem, Stack
} from "@mui/material";
import {DialogAction} from './ColumnTypeDesicionDialogReducer'

const TITLE="스키마 설정"
const OK_BUTTON_STRING="확인"
const CLOSE_BUTTON_STRING="취소"
const DICOM_ID ={
    PATIENT : 'anonymized_id',
    STUDY : 'StudyInstanceUID',
    SERIES : 'SeriesInstanceUID',
    INSTANCE : 'SOPInstanceUID'
}
const LABEL={
    PATIENT : 'PATIENT LABEL',
    STUDY : 'STUDY LABEL',
    SERIES : 'SERIES LABEL',
    INSTANCE : 'INSTANCE LABEL'
}

const selectComponent=(columnName : string)=>{
    return(
        <Box sx={{ minWidth: 120 }} key={columnName}>
            <FormControl fullWidth>
                <InputLabel id={columnName}>Age</InputLabel>
                <Select
                    labelId={columnName}
                    id={columnName}
                    label={columnName}
                    value=''
                >
                    {Object.values(DICOM_ID).map( option =><MenuItem key={option} value={option}>{option}</MenuItem> )}
                    {Object.values(LABEL).map( option =><MenuItem key={option} value={option}>{option}</MenuItem> )}
                </Select>
            </FormControl>
        </Box>
    )
}

function ColumnTypeDecisionDialog(){
    const open=useSelector((state:RootState) => state.ColumnTypeDecisionDialogReducer.columnTypeDecisionOpenStatus)
    const columnList=useSelector((state: RootState)=> state.ColumnTypeDecisionDialogReducer.columnList)
    const columnSchema=useSelector((state: RootState)=> state.ColumnTypeDecisionDialogReducer.columnSchema)

    const dispatch=useDispatch()
    const handleOkButtonClick=()=>{
        dispatch(DialogAction.closeDialog())
    }
    const handleCloseButtonClick=()=>{
        dispatch(DialogAction.closeDialog())
    }

    return(
        <Dialog open={open}>
            <DialogTitle>{TITLE}</DialogTitle>
            <DialogContent>
                <Stack>
                    {columnList.map((columnName:string) =>selectComponent(columnName))}
                </Stack>
            </DialogContent>
            <DialogActions>
                <Button onClick={handleOkButtonClick}>{OK_BUTTON_STRING}</Button>
                <Button onClick={handleCloseButtonClick}>{CLOSE_BUTTON_STRING}</Button>
            </DialogActions>
        </Dialog>
    )
}
export default ColumnTypeDecisionDialog