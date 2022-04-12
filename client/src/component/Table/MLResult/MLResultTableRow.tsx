import * as React from 'react'
import sample_image from './sample_image.png'
import {Alert, CircularProgress, Collapse, TableCell} from "@mui/material";
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
            <TableCell style={{ paddingBottom: 0, paddingTop: 0 }} colSpan={6}>
                <Collapse in={open} timeout="auto" unmountOnExit>
                    <div>
                        <MLImageResult image_name={image_name}/>
                        <MLStringResult anonymize_id={anonymize_id}/>
                    </div>
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
    if(!fileList.some(file=>file.includes(image_name))){
        return (
            <Alert severity={"warning"}> {image_name} 이미지가 존재하지 않습니다.</Alert>
        )
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
        return (<img src={img} alt="ML original image"/>)
    }
}

interface MLStringResultArgs{
    anonymize_id : string
}
const MLStringResult : React.FC<MLStringResultArgs>=({anonymize_id})=>{
    return (<span>ML Result</span>)
}

export default MLResultTableRow