import * as React from 'react';
import {Stack, Button, IconButton, Typography} from '@mui/material';
import ClearIcon from '@mui/icons-material/Clear';

const MetaUploadBox = ({csvFile, setCsvFile, setdicomFiles: setImageOrDicomFiles}) => {
    console.log('build MetaUploadBox comopnent ', csvFile)
    const handleChangeFile = (event) => {
        setCsvFile(event.target.files[0])
    }
    const handleClearEvent = () => {
        setCsvFile(undefined)
        setImageOrDicomFiles(new Set([]))
    }

    function MetaUploadBoxContent(props) {
        if (csvFile === undefined) {
            return (
                <div>
                    <input
                        accept=".csv"
                        style={{display: 'none'}}
                        id="csvMeta-upload-input"
                        type="file"
                        onChange={handleChangeFile}
                    />
                    <label htmlFor="csvMeta-upload-input" width="100%">
                        <Button variant="raised" component="span">
                            Upload Metadata csv file
                        </Button>
                    </label>
                </div>
            )
        } else {
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

    return (
        <Stack borderRadius='5px' style={{alignItems: "center", backgroundColor: '#f5f5f5'}} width="100%"
               sx={{marginTop: '8px'}}>
            <MetaUploadBoxContent width="100%"/>
        </Stack>
    )
}

export default MetaUploadBox;