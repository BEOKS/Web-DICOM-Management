import * as React from 'react';
import Box from '@mui/material/Box';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TablePagination from '@mui/material/TablePagination';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import FormControlLabel from '@mui/material/FormControlLabel';
import Switch from '@mui/material/Switch';
import EnhancedTableHead from './EnhancedTableHead'
import EnhancedTableToolbar from "./EnhancedTableToolbar";
import { stableSort, getComparator } from './Utils';
import DicomRow from './DicomRow';
import {useDispatch, useSelector} from "react-redux";
import {MLResultReduxAction} from "./MLResult/MLResultRedux";
import {getFileListAPI} from "../../api/StorageAPI";
import ParticipantInfoReducer from "../Toolbar/ProjectParticipant/ParticipantInfoReducer";

export default function DicomTable(props) {
    const [order, setOrder] = React.useState('asc');
    const [orderBy, setOrderBy] = React.useState('id');
    const [selected, setSelected] = React.useState([]);
    const [selectedStudyUIDList, setSelectedStudyUIDList] = React.useState([]);

    // Sprint4: Dicom 삭제, 다운로드는 Study UID를 기준으로 변경하지만,
    // Non-Referenced Dicom은 여전히 Patient ID를 기준으로 삭제하기 때문에 selectedPatientIDList를 남겨둠
    const [selectedPatientIDList, setSelectedPatientIDList] = React.useState([]);

    const [page, setPage] = React.useState(0);
    const [dense, setDense] = React.useState(false);
    const [rowsPerPage, setRowsPerPage] = React.useState(5);
    const projectId=useSelector((state)=>state.ParticipantInfoReducer.participants.projectId)
    
    const rows = [...props.data];
    console.log('data',rows)
    const isNonReferenced = props.isNonReferenced;
    // 메타 데이터 형식 변경으로 인한 임시 키
    const STUDY_KEY_NAME = "StudyInstanceUID";
    const dispatch=useDispatch()
    getFileListAPI(
        projectId,
        fileList => dispatch(MLResultReduxAction.setImageFileNames(fileList)),
            error=>console.log('이미지 파일 리스트 다운로드 실패'+error.message)
    )

    // 드로어에서 다른 프로젝트 클릭 시 테이블 행 선택을 해제함
    React.useEffect(() => {
        setSelected([]);
    }, [props.data]);

    const getKeysFromJSON = () => {
        if (rows.length <= 0) {
            return [];
        } else {
            return isNonReferenced
                ? ['patientId']
                : Object.keys(rows[0].body);
        }
    };
    const keys = getKeysFromJSON();

    const handleRequestSort = (event, property) => {
        const isAsc = orderBy === property && order === 'asc';
        setOrder(isAsc ? 'desc' : 'asc');
        setOrderBy(property);
    };

    const handleSelectAllClick = (event) => {
        if (event.target.checked) {
            const newSelecteds = isNonReferenced
                ? rows.map(n => n.body.patientId)
                : rows.map(n => n.metadataId);
            setSelected(newSelecteds);
            setSelectedStudyUIDList(rows.map(n => n.body[STUDY_KEY_NAME]));
            setSelectedPatientIDList(rows.map(n => n.body.patientId));
            return;
        }
        setSelected([]);
        setSelectedStudyUIDList([]);
        setSelectedPatientIDList([]);
    };

    const handleClick = (event, id) => {
        const selectedIndex = selected.indexOf(id);
        let newSelected = [];

        if (selectedIndex === -1) {
            newSelected = newSelected.concat(selected, id);
        } else if (selectedIndex === 0) {
            newSelected = newSelected.concat(selected.slice(1));
        } else if (selectedIndex === selected.length - 1) {
            newSelected = newSelected.concat(selected.slice(0, -1));
        } else if (selectedIndex > 0) {
            newSelected = newSelected.concat(
                selected.slice(0, selectedIndex),
                selected.slice(selectedIndex + 1),
            );
        }
        setSelected(newSelected);
        setSelectedStudyUIDList(newSelected.map(id => {
            return rows.find(row=>row.metadataId === id).body[STUDY_KEY_NAME];
        }));
        setSelectedPatientIDList(newSelected.map(id => {
            const patientId = isNonReferenced 
                ? id 
                : rows.find(row=>row.metadataId === id).body[keys[0]];
            return patientId;
        }));
    };

    const handleChangePage = (event, newPage) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = (event) => {
        setRowsPerPage(parseInt(event.target.value, 10));
        setPage(0);
    };

    const handleChangeDense = (event) => {
        setDense(event.target.checked);
    };

    const isSelected = (id) => selected.indexOf(id) !== -1;

    // Avoid a layout jump when reaching the last page with empty rows.
    const emptyRows =
        page > 0 ? Math.max(0, (1 + page) * rowsPerPage - rows.length) : 0;

    return (
        <Box sx={{ width: '100%', px: 3, mt: 3 }}>
            <Paper sx={{ width: '100%', mb: 2 }}>
                <EnhancedTableToolbar 
                    numSelected={selected.length}
                    selected={selected}
                    selectedStudyUIDList={selectedStudyUIDList}
                    selectedPatientIDList={selectedPatientIDList}
                    isNonReferenced={isNonReferenced}
                    metaDataUpdated={props.metaDataUpdated}
                    setMetaDataUpdated={props.setMetaDataUpdated}
                    metadata={rows}
                    project={props.project}
                    />
                <TableContainer>
                    <Table
                        sx={{ minWidth: 750 }}
                        aria-labelledby="tableTitle"
                        size={dense ? 'small' : 'medium'}
                    >
                        <EnhancedTableHead
                            numSelected={selected.length}
                            order={order}
                            orderBy={orderBy}
                            onSelectAllClick={handleSelectAllClick}
                            onRequestSort={handleRequestSort}
                            rowCount={rows.length}
                            rows={rows}
                            keys={keys}
                        />
                        <TableBody>
                            {/* if you don't need to support IE11, you can replace the `stableSort` call with:
                 rows.slice().sort(getComparator(order, orderBy)) */}
                            {stableSort(rows, getComparator(order, orderBy))
                                .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                                .map((row, index) => {
                                    const id = isNonReferenced ? row.body.patientId : row.metadataId;
                                    const isItemSelected = isSelected(id);
                                    const labelId = `enhanced-table-checkbox-${index}`;

                                    return (
                                        <DicomRow
                                            isItemSelected={isItemSelected}
                                            labelId={labelId}
                                            handleClick={handleClick}
                                            isNonReferenced={isNonReferenced}
                                            row={row}
                                            keys={keys}
                                            key={id}
                                        />
                                    );
                                })}
                            {emptyRows > 0 && (
                                <TableRow
                                    style={{
                                        height: (dense ? 33 : 53) * emptyRows,
                                    }}
                                >
                                    <TableCell colSpan={keys.length + 2} />
                                </TableRow>
                            )}
                        </TableBody>
                    </Table>
                </TableContainer>
                <TablePagination
                    rowsPerPageOptions={[5, 10, 25]}
                    component="div"
                    count={rows.length}
                    rowsPerPage={rowsPerPage}
                    page={page}
                    onPageChange={handleChangePage}
                    onRowsPerPageChange={handleChangeRowsPerPage}
                />
            </Paper>
            <FormControlLabel
                control={<Switch checked={dense} onChange={handleChangeDense} />}
                label="Dense padding"
            />
        </Box>
    );
}
