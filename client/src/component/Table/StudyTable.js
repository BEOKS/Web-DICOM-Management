import * as React from 'react';
import TableRow from '@mui/material/TableRow';
import TableCell from '@mui/material/TableCell';
import Collapse from '@mui/material/Collapse';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Table from '@mui/material/Table';
import TableHead from '@mui/material/TableHead';
import TableBody from '@mui/material/TableBody';

export default function StudyTable(props) {

    // Patient ID를 통해 서버에서 Study UID list를 받아왔다고 가정
    const studyUIDList = [
        { studyUID: 100 },
        { studyUID: 200 },
        { studyUID: 300 },
        { studyUID: 400 },
        { studyUID: 500 },
    ];

    return (
        <TableRow>
            <TableCell style={{ paddingBottom: 0, paddingTop: 0 }} colSpan={6}>
                <Collapse in={props.open} timeout="auto" unmountOnExit>
                    <Box sx={{ margin: 1 }}>
                        <Typography variant="h6" gutterBottom component="div">
                            Study UID List
                        </Typography>
                        <Table size="small" aria-label="purchases">
                            <TableHead>
                                <TableRow>
                                    <TableCell>study_uid</TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {studyUIDList.map((studyRow) => (
                                    <TableRow key={studyRow.studyUID}>
                                        <TableCell component="th" scope="row">
                                            {studyRow.studyUID}
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
