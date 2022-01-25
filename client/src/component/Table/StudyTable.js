import * as React from 'react';
import TableRow from '@mui/material/TableRow';
import TableCell from '@mui/material/TableCell';
import Collapse from '@mui/material/Collapse';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Table from '@mui/material/Table';
import TableHead from '@mui/material/TableHead';
import TableBody from '@mui/material/TableBody';
import { useState,useEffect } from 'react';
import axios from 'axios';

const hostLocation=process.env.SERVER_HOST
const viewerHost=`http://${hostLocation}:3000`
export default function StudyTable(props) {
    
    const [studyUIDList,setStudyUIDList]=useState([])
    
    useEffect(()=>{
        const url=`api/patient/${props.patientId}/study`
        console.log(url)
        axios.get(url)
            .then(response=>{
                setStudyUIDList(response.data)
                console.log(response.data)
            })
    },[props.patientId,props.open])
    
    return (
        <TableRow>
            <TableCell style={{ paddingBottom: 0, paddingTop: 0, border: 0 }} colSpan={props.colSpan}>
                <Collapse in={props.open} timeout="auto" unmountOnExit>
                    <Box sx={{ margin: 3 }}>
                        <Typography variant="h6" gutterBottom component="div">
                            Study UID List
                        </Typography>
                        <Table size="small" aria-label="study uid list">
                            <TableHead>
                                <TableRow>
                                    <TableCell>study_uid</TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {studyUIDList.map((studyRow,index) => (
                                    <TableRow
                                        hover
                                        // onClick={() => {뷰어 페이지로 리다이렉션 예정}}
                                        key={index}>
                                        <TableCell component="th" scope="row">
                                            <a href={`${viewerHost}/viewer/${studyRow}`}>
                                                {studyRow}
                                            </a>
                                        </TableCell>
                                    </TableRow>
                                ))}
                            </TableBody>
                        </Table>
                    </Box>
                </Collapse>
            </TableCell>
        </TableRow>
    );
}
