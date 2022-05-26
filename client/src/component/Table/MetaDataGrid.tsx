import React from "react";
import './MetaDataGrid.css';
import { useDispatch, useSelector } from "react-redux";
import { RootState } from '../../store';
import { Body, MetaDataGridAction } from './MetaDataGridReducer';
import { MLResultReduxAction } from "./MLResult/MLResultRedux";
import DataGrid, { Selection, FilterRow, Toolbar, Item, Editing, Column, MasterDetail, LoadPanel } from 'devextreme-react/data-grid';
import ArrayStore from 'devextreme/data/array_store';
import DataSource from 'devextreme/data/data_source';
import { Box, Tooltip, IconButton, Typography } from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";
import CloudDownloadIcon from '@mui/icons-material/CloudDownload';
import FileDownloadIcon from '@mui/icons-material/FileDownload';
import { extractBody, extractColumns } from "./Utils/extractMetaData";
import exportCSVFile from "./Utils/exportCSVFile"
import { getFileListAPI } from "../../api/StorageAPI";
import { updateMetaData } from "../../api/metadata";
import axios from 'axios';
import DeleteRowDialog from "./DeleteRowDialog";
import MLResultTableRow from "./MLResult/MLResultTableRow";
import { useEffect } from "react";

const hostLocation = process.env.SERVER_HOST ? process.env.SERVER_HOST : 'localhost';

const MetaDataGrid = () => {
    const dispatch = useDispatch();
    const metaData = useSelector((state: RootState) => state.MetaDataGridReducer.metaData);
    const project = useSelector((state: RootState) => state.ProjectDrawerReducer.project);
    const selectedRow = useSelector((state: RootState) => state.MetaDataGridReducer.selectedRow);
    const body = useSelector((state: RootState) => state.MetaDataGridReducer.body);
    const columns = useSelector((state: RootState) => state.MetaDataGridReducer.columns);

    useEffect(() => {
        dispatch(MetaDataGridAction.setBody(extractBody(metaData)));
    }, [metaData]);

    useEffect(() => {
        dispatch(MetaDataGridAction.setColumns(extractColumns(body)));
    }, [body]);

    const dataSource = new DataSource({
        store: new ArrayStore({
            data: body,
            key: 'metadataId',
        }),
    });

    getFileListAPI(
        project.projectId,
        fileList => dispatch(MLResultReduxAction.setImageFileNames(fileList)),
        error => console.log('이미지 파일 리스트 다운로드 실패' + error.message)
    );

    // StudyUID 필드명이 바뀔 수 있으므로 따로 기재
    const STUDY_UID_NAME = "StudyInstanceUID";

    // 행 선택
    const onSelectionChanged = ({ selectedRowsData }: { selectedRowsData: Body[] }) => {
        dispatch(MetaDataGridAction.setSelectedRow(selectedRowsData));
        dispatch(MetaDataGridAction.setSelectedMetaDataID(selectedRowsData.map((row: Body) => row.metadataId)));
        dispatch(MetaDataGridAction.setSelectedStudyUID(selectedRowsData.map((row: Body) => row[STUDY_UID_NAME])));
    };

    // 메타 데이터 수정 후 저장
    const onSaved = (e: any) => {
        if (e.changes.length !== 0) {
            const data = { ...e.changes[0].data };
            const metadataId = data.metadataId;

            // metadataId가 삭제된 원래 body 형식으로 메타데이터 업데이트 요청
            delete data.metadataId;
            updateMetaData(data, metadataId);
        }
    };

    const handleCSVDownloadButtonClick = () => {
        const selectedMetaData = selectedRow.map(row => {
            const rowCopy = { ...row };
            delete rowCopy.metadataId;
            return rowCopy;
        });

        if (selectedRow.length === 0) {
            alert('다운로드할 메타 데이터를 선택해주세요.');
        } else {
            exportCSVFile(selectedMetaData, project.projectName + '.csv');
        }
    };

    const handleDicomDownloadButtonClick = () => {
        //download files
        if (selectedRow.length === 0) {
            alert('다운로드할 Dicom을 선택해주세요.');
        } else {
            alert('현재 이미지 파일 시스템 개발을 위해 일시적으로 Dicom 서비스를 중단하였습니다. 추후 복구 예정입니다.')
            // const selectedMetaData = selectedRow.map(row => {
            //     const rowCopy = { ...row };
            //     delete rowCopy.metadataId;
            //     return rowCopy;
            // });
            // selectedMetaData.forEach(metaData => {
            //     const studyUID = metaData.StudyInstanceUID;
            //     axios.get(`http://${hostLocation}:8080/api/study/${studyUID}/dicom`, { responseType: 'blob' })
            //         .then((response) => {
            //             const url = window.URL.createObjectURL(new Blob([response.data]));
            //             const link = document.createElement('a');
            //             link.href = url;
            //             link.setAttribute('download', `${studyUID}.zip`); //or any other extension
            //             document.body.appendChild(link);
            //             link.click();
            //         });
            // });
        }
    };

    const handleDeleteButtonClick = () => {
        if (selectedRow.length > 0) {
            dispatch(MetaDataGridAction.openDeleteRowDialog());
        } else {
            alert('삭제할 행을 선택해주세요.');
        }
    };

    return (
        <Box m={3} >
            <DataGrid
                dataSource={dataSource}
                showBorders={true}
                hoverStateEnabled={true}
                onSelectionChanged={onSelectionChanged}
                onSaved={onSaved}
            >
                {columns.map(column => {
                    return <Column key={column} dataField={column} />
                })}
                <Selection
                    mode="multiple"
                    selectAllMode="allPages"
                    showCheckBoxesMode="always"
                />
                <FilterRow visible={true} />
                <Editing
                    mode="row"
                    useIcons={true}
                    allowUpdating={true} />
                <MasterDetail
                    enabled={true}
                    render={MLResultTableRow}
                />
                <LoadPanel enabled />
                {/* Toolbar를 다른 컴포넌트로 분리했더니 화면에 나타나지 않아서 그대로 둠 */}
                <Toolbar>
                    <Item location="before">
                        {selectedRow.length === 0
                            ? <Typography variant="h6" ml={1}>Dicom List</Typography>
                            : <Typography ml={1}>{selectedRow.length} selected</Typography>
                        }
                    </Item>
                    <Item location="after">
                        <Tooltip title="Download CSV">
                            <IconButton onClick={() => handleCSVDownloadButtonClick()}>
                                <FileDownloadIcon />
                            </IconButton>
                        </Tooltip>
                    </Item>
                    <Item location="after">
                        <Tooltip title="Download Dicom">
                            <IconButton onClick={() => handleDicomDownloadButtonClick()}>
                                <CloudDownloadIcon />
                            </IconButton>
                        </Tooltip>
                    </Item>
                    <Item location="after">
                        <Tooltip title="Delete">
                            <IconButton onClick={() => handleDeleteButtonClick()}>
                                <DeleteIcon />
                            </IconButton>
                        </Tooltip>
                        <DeleteRowDialog />
                    </Item>
                </Toolbar>
            </DataGrid>
        </Box>
    );
};

export default MetaDataGrid;