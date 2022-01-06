import * as React from 'react';
import { Stack, Button,IconButton,Typography } from '@mui/material';
import ClearIcon from '@mui/icons-material/Clear';

const MetaUploadBox=({csvFile,setCsvFile,setdicomFiles})=>{
    console.log('build MetaUploadBox comopnent ',csvFile)
    const handleChangeFile=(event)=>{
        setCsvFile(event.target.files[0])
    }
    const handleClearEvent=()=>{
        setCsvFile(undefined)
        setdicomFiles(new Set([]))
    }
    function MetaUploadBoxContent(props){
        if(csvFile===undefined){
            return (
                <div>
                    <input
                        accept=".csv"
                        style={{ display: 'none' }}
                        id="csvMeta-upload-input"
                        type="file"
                        onChange={handleChangeFile}
                    />
                    <label htmlFor="csvMeta-upload-input"  width="100%">
                        <Button variant="raised" component="span">
                            Upload Metadata csv file
                        </Button>
                    </label>
                </div>
                )
        }
        else{
            return (
                <Stack direction="row" style={{justifyContent: 'center'}} width="100%">
                    <Typography margin='5px'>
                        {csvFile.name}
                    </Typography>
                    <IconButton 
                        size="small"
                        onClick={handleClearEvent}>
                        <ClearIcon/>
                    </IconButton>
                </Stack>
            )
        }
    }
    return(
        <Stack margin borderRadius='5px' style={{alignItems: "center", backgroundColor:'#f5f5f5'}}  width="100%">
            <MetaUploadBoxContent  width="100%"/>
        </Stack>
    )
}

export default MetaUploadBox;