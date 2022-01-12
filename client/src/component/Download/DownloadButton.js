import * as React from 'react'
import {Button} from '@mui/material'

export default function DownloadButton(){
    const handleClickOpen=()=>{

    }
    return(
        <div>
            <Button  onClick={handleClickOpen} variant="outlined">Upload</Button>
        </div>
    )
}