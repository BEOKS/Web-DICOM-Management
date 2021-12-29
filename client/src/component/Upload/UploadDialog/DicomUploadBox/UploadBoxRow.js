import * as React from 'react'
import { Alert , IconButton,Box  } from '@mui/material'
import ClearIcon from '@mui/icons-material/Clear';

const UploadBoxRow=({fileName,dicomFilePathList,setdicomFilePathList,severity})=>{
    return(
        <Box borderRadius="3%" style={{width: "100%"}}>
            <Alert severity={severity} >
                {fileName}
                <IconButton 
                    size="small"
                    onClick={()=>setdicomFilePathList(dicomFilePathList.filter(i => i!==fileName))}>
                    <ClearIcon/>
                </IconButton>
            </Alert>
        </Box>
    )
}
export default UploadBoxRow;