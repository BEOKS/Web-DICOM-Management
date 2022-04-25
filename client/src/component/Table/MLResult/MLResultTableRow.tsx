import * as React from 'react'
import sample_image from './sample_image.png'
import {Alert, Chip, Grid, Collapse, Skeleton, Stack, TableCell, Typography} from "@mui/material";
import TableRow from '@mui/material/TableRow';
import {useSelector} from "react-redux";
import {RootState} from "../../../store";
import {useState} from "react";
import {downloadFile} from "../../../api/StorageAPI";
interface MLResultTableArgs{
    image_name : string,
    anonymize_id : string,
    pred :string,
    prob :string,
    open : boolean,
}

/**
 * 입력받은 image_name과 anonymize_id의 값을 바탕으로
 * 머신러닝 결과(이미지 및 string)를 보여주는 컴포넌트입니다.
 * @param image_name
 * @param anonymize_id
 * @constructor
 */
const MLResultTableRow: React.FC<MLResultTableArgs> = ({image_name,anonymize_id,open,pred,prob})=>{
    return (
        <TableRow>
            <TableCell style={{ paddingBottom: 0, paddingTop: 0 }} colSpan={12}>
                <Collapse in={open} timeout="auto" unmountOnExit>
                    <Stack direction={'row'}>
                        <MLImageResult image_name={image_name} pred={pred} prob={prob}/>
                        <MLImageResult image_name={getCropImage(image_name)} pred={pred} prob={prob}/>
                        <MLImageResult image_name={getResultImage(image_name)} pred={pred} prob={prob}/>
                    </Stack>
                </Collapse>
            </TableCell>
        </TableRow>
    )
}

interface MLResultImageArgs{
    image_name: string,
    pred :string,
    prob :string

}
const MLImageResult : React.FC<MLResultImageArgs> =({image_name,pred,prob})=>{
    const fileList : string[]=useSelector((state:RootState)=>state.MLResultReducer.imageFileNames)
    const projectId : string=useSelector((state:RootState)=>state.ParticipantInfoReducer.participants.projectId)
    const [img,setImg] = useState('' )
    if(!fileList.some(file=>file.includes(image_name))){
        if(image_name.includes("cam")||image_name.includes("crop")){
            return (
                <div style={{
                    display: 'flex',
                    alignItems: 'center',
                    flexWrap: 'wrap',
                }}>
                </div> 
                
            )
        }
        else{
            return (
                <Alert severity={"info"}> Please update image file for preview </Alert>
            )
        }
    }
    else if(img===''){
        downloadFile(projectId,image_name,
            (image: any)=>{setImg(image)},
            (error: any)=>setImg('error'))
        return (
            <Skeleton variant="rectangular" width={300} height={210} />
        )
    }
    else if(img==='error'){
        return(
            <Alert severity={"error"}> {image_name} Fail loading image</Alert>
        )
    }
    else if(img=='data:'){
        return (<div></div>)
    }
    else{
        return (
            <Grid
                container
                spacing={0}
                direction="column"
                alignItems="center"
                >
                <Grid item xs={3}>
                    <img src={img} alt=" image" width={'80%'}/>
                    <Chip label= {image_name.includes("cam")? `${pred} : ${prob}`:
                            (image_name.includes("crop")? 'Cropped image':'Original image')} style={{width:'80%'}}/>
                </Grid>   
            </Grid> 
        // <Stack>
        //     <img src={img} alt=" image" width={'80%'}/>
        //     <Chip label= {image_name.includes("cam")? 'Cam Result':
        //             (image_name.includes("crop")? 'Cropped image':'Original image')} style={{width:'80%'}}/>
        //     {/* <Typography align='center' width={'80%'}>
        //         {image_name.includes("cam")? img:
        //             (image_name.includes("crop")? 'Cropped image':'Original image')}
        //     </Typography> */}
        // </Stack>

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
const getCropImage=(imageName:string)=>{
    const index=imageName.indexOf('.')
    return imageName.substring(0,index)+".crop"+imageName.substring(index)
}
export default MLResultTableRow