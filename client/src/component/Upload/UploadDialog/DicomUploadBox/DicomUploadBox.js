import * as React from 'react';
import { Button, Typography } from '@mui/material';
import UploadBoxRow from './UploadBoxRow';
import { Stack } from '@mui/material';
const DicomUploadBox=({csvFilePath,dicomFilePathList,setdicomFilePathList})=>{

    const handleChangeFile=(event)=>{
        console.log(event.target.files)
        const newFileArray=Array.from(event.target.files).map(i => i.name)
        console.log([...dicomFilePathList, ...newFileArray])
        setdicomFilePathList([...dicomFilePathList, ...newFileArray])
    }
    if(csvFilePath===undefined){
        return(
            <div>
                <Stack margin borderRadius="5px" style={{alignItems: "center", backgroundColor:'#f5f5f5'}}>
                    <Typography color='#808080' margin>
                        Dicom 파일을 업로드 하기전 메타데이터를 업로드 해야 합니다.
                    </Typography>
                </Stack>
            </div>
        )
    }
    return(
        <Stack margin >
            <Stack borderRadius="5px" style={{alignItems: "center", backgroundColor:'#f5f5f5'}}>
                {dicomFilePathList.map((path)=> (<UploadBoxRow 
                    key={path}
                    fileName={path} 
                    severity={"success"}
                    dicomFilePathList={dicomFilePathList}
                    setdicomFilePathList={setdicomFilePathList}
                    />))}
                <input
                    accept=".dcm"
                    style={{ display: 'none' }}
                    id="dicom-upload-input"
                    multiple
                    type="file"
                    onChange={handleChangeFile}
                />
                <label htmlFor="dicom-upload-input">
                    <Button variant="raised" component="span">
                        + Upload Dicoms
                    </Button>
                </label> 
            </Stack>
        </Stack>
    )
}
export default DicomUploadBox;