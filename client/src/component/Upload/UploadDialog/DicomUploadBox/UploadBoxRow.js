import * as React from 'react'
import { Alert , IconButton,Box  } from '@mui/material'
import ClearIcon from '@mui/icons-material/Clear';

const UploadBoxRow=({fileName,dicomFiles,setdicomFiles,severity})=>{
    console.log('build UploadBoxRow component',fileName,dicomFiles,severity)
    return(
        <Box borderRadius="3%" style={{width: "100%"}}>
            <Alert severity={severity} >
                {fileName}
                <IconButton 
                    size="small"
                    onClick={()=>setdicomFiles(dicomFiles.filter(i => i.name!==fileName))}>
                    <ClearIcon/>
                </IconButton>
            </Alert>
        </Box>
    )
}
export default UploadBoxRow;