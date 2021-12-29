import * as React from 'react';
import { Stack, Button,IconButton,Typography } from '@mui/material';
import ClearIcon from '@mui/icons-material/Clear';
import { width } from '@mui/system';

const MetaUploadBox=({csvFilePath,setCsvFilePath})=>{
    console.log(csvFilePath)
    const handleChangeFile=(event)=>{
        setCsvFilePath(event.target.files[0].name)
    }
    function MetaUploadBoxContent(props){
        setTimeout(() => {}, 1000)
        if(csvFilePath===undefined){
            return (<Button variant="raised" component="span">
                        Upload Metadata csv file
                    </Button>)
        }
        return (
            <Stack direction="row" style={{justifyContent: 'center'}} width="100%">
                <Typography margin='5px'>
                    {csvFilePath}
                </Typography>
                <IconButton 
                    size="small"
                    onClick={()=>setTimeout(()=>setCsvFilePath(undefined), 100)}>
                    <ClearIcon/>
                </IconButton>
            </Stack>
        )
    }
    return(
        <Stack borderRadius='5px' style={{alignItems: "center", backgroundColor:'#f5f5f5'}}  width="100%">
            <input
                accept=".csv"
                style={{ display: 'none' }}
                id="raised-button-file"
                type="file"
                onChange={handleChangeFile}
            />
            <label htmlFor="raised-button-file"  width="100%">
                <MetaUploadBoxContent  width="100%"/>
            </label>
        </Stack>
    )
}

export default MetaUploadBox;