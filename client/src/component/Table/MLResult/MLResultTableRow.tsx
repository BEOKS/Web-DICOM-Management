import * as React from 'react'
import sample_image from './sample_image.png'
import {Alert, CircularProgress, Collapse, Stack, TableCell, Typography} from "@mui/material";
import TableRow from '@mui/material/TableRow';
import {useSelector} from "react-redux";
import {RootState} from "../../../store";
import {useState} from "react";
import {downloadFile} from "../../../api/StorageAPI";
interface MLResultTableArgs{
    image_name : string,
    anonymize_id : string,
    open : boolean,
}

/**
 * 입력받은 image_name과 anonymize_id의 값을 바탕으로
 * 머신러닝 결과(이미지 및 string)를 보여주는 컴포넌트입니다.
 * @param image_name
 * @param anonymize_id
 * @constructor
 */
const MLResultTableRow: React.FC<MLResultTableArgs> = ({image_name,anonymize_id,open})=>{
    return (
        <TableRow>
            <TableCell style={{ paddingBottom: 0, paddingTop: 0 }} colSpan={10}>
                <Collapse in={open} timeout="auto" unmountOnExit>
                    <Stack direction={'row'}>
                        <MLImageResult image_name={image_name}/>
                        <MLImageResult image_name={getResultImage(image_name)}/>
                    </Stack>
                </Collapse>
            </TableCell>
        </TableRow>
    )
}

interface MLResultImageArgs{
    image_name: string
}
const MLImageResult : React.FC<MLResultImageArgs> =({image_name})=>{
    const fileList : string[]=useSelector((state:RootState)=>state.MLResultReducer.imageFileNames)
    const projectId : string=useSelector((state:RootState)=>state.ParticipantInfoReducer.participants.projectId)
    const [img,setImg] = useState('' )
    console.log('fileList',fileList)
    if(!fileList.some(file=>file.includes(image_name))){
        if(image_name.includes("cam")){
            return (
                <div style={{
                    display: 'flex',
                    alignItems: 'center',
                    flexWrap: 'wrap',
                }}>
                    <Alert severity={"warning"}> cam 이미지가 존재하지 않습니다. 결과를 보기 위해선 원본 이미지 업로드 후, 추론을 진행해주세요</Alert>
                </div> 
                
            )
        }
        else{
            return (
                <Alert severity={"warning"}> {image_name} 이미지가 존재하지 않습니다.</Alert>
            )
        }
    }
    else if(img===''){
        downloadFile(projectId,image_name,
            (image: any)=>{setImg(image)},
            (error: any)=>setImg('error'))
        return (
            <Alert severity={"info"}> {image_name} 이미지를 다운로드 중입니다. <CircularProgress/></Alert>
        )
    }
    else if(img==='error'){
        return(
            <Alert severity={"error"}> {image_name} 이미지를 가져오는데 실패했습니다.</Alert>
        )
    }
    else{
        return (
        <Stack>
            <img src={img} alt="ML original image" width={'80%'}/>
            <Typography align='center' width={'80%'}>{image_name.includes("cam")? 'cam result':'original image'}</Typography>
        </Stack>

        )
    }
}

interface MLStringResultArgs{
    anonymize_id : string
}
const MLStringResult : React.FC<MLStringResultArgs>=({anonymize_id})=>{
    return (<span>ML Result</span>)
}

const getResultImage=(imageName:string)=>{
    const index=imageName.indexOf('.')
    return imageName.substring(0,index)+".cam"+imageName.substring(index)
}
export default MLResultTableRow