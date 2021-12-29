import * as React from 'react';
import { Button } from '@mui/material';
import UploadBoxRow from './UploadBoxRow';
import { Stack } from '@mui/material';
const DicomUploadBox=({dicomFilePathList,setdicomFilePathList})=>{
    function popFile(){

    }
    const handleChangeFile=(event)=>{
        console.log(event.target.files)
        setdicomFilePathList(Array.from(event.target.files).map(i => i.name))
    }
    return(
        <Stack borderRadius="5px" style={{alignItems: "center", backgroundColor:'#f5f5f5'}}>
            {dicomFilePathList.map((path)=> (<UploadBoxRow 
                fileName={path} 
                severity={"success"}
                dicomFilePathList={dicomFilePathList}
                setdicomFilePathList={setdicomFilePathList}
                />))}
            <input
                accept=".dcm"
                style={{ display: 'none' }}
                id="raised-button-file"
                multiple
                type="file"
                onChange={handleChangeFile}
            />
            <label htmlFor="raised-button-file">
                <Button variant="raised" component="span">
                    Upload
                </Button>
            </label> 
        </Stack>
    )
}
export default DicomUploadBox;