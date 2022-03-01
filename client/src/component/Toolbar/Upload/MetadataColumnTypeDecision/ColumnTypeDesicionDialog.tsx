import * as React from 'react'
import {useSelector, useDispatch} from "react-redux";
import {RootState} from "../../../../store";
import {
    Dialog, DialogTitle, DialogContent, Box,
    DialogActions, Button, Select, FormControl, InputLabel, MenuItem, Stack, Grid, SelectChangeEvent, Alert
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

const SelectComponent=(columnName : string, columnSchema : any,handleSelectChange : (columnName:string,value:string)=>(event :  SelectChangeEvent<string>)=>void)=>{
    let value : string=columnSchema[columnName]===undefined ? '' : columnSchema[columnName]
    return(
        <Box sx={{ minWidth: 120 }} key={columnName} margin={1}>
            <FormControl fullWidth>
                <InputLabel id={columnName}>{columnName}</InputLabel>
                <Select
                    labelId={columnName}
                    id={columnName}
                    label={columnName}
                    value={value}
                    onChange={handleSelectChange(columnName,value)}
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
    const columnSchema: any=useSelector((state:RootState)=>state.ColumnTypeDecisionDialogReducer.columnSchema)

    const dispatch=useDispatch()
    const handleOkButtonClick=()=>{
        dispatch(DialogAction.closeDialog())
    }
    const handleCloseButtonClick=()=>{
        dispatch(DialogAction.closeDialog())
    }
    const handleSelectChange=(columnName: string,value:string)=> (event :  SelectChangeEvent<string>)=>{
        value=event.target.value
        columnSchema[columnName]=event.target.value
        dispatch(DialogAction.setColumnSchema({...columnSchema,columnName:event.target.value}))
        console.log(columnSchema,event.target.value,value)
    }

    return(
        <Dialog open={open}>
            <DialogTitle>{TITLE}</DialogTitle>
            <DialogContent>
                <Alert severity={'info'}>각 메타데이터의 열에 올바른 레벨을 지정해주세요.</Alert>
                <Grid container spacing={3} paddingTop={4} >
                    {columnList.map((columnName:string) =>SelectComponent(columnName,columnSchema,handleSelectChange))}
                </Grid>
            </DialogContent>
            <DialogActions>
                <Button onClick={handleOkButtonClick}>{OK_BUTTON_STRING}</Button>
                <Button onClick={handleCloseButtonClick}>{CLOSE_BUTTON_STRING}</Button>
            </DialogActions>
        </Dialog>
    )
}
export default ColumnTypeDecisionDialog