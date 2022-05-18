import React from "react"
import './MetaDataGrid.css';
import { useDispatch, useSelector } from "react-redux";
import { RootState } from '../../store';
import { MetaDataGridAction } from './MetaDataGridReducer';
import { MLResultReduxAction } from "./MLResult/MLResultRedux";
import DataGrid, { Selection, FilterRow, Toolbar, Item, Editing, Column, Button, MasterDetail } from 'devextreme-react/data-grid';
import { Box, Tooltip, IconButton, Typography } from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";
import CloudDownloadIcon from '@mui/icons-material/CloudDownload';
import FileDownloadIcon from '@mui/icons-material/FileDownload';
import { extractBody, extractColumns } from "./Utils/extractMetaData";
import exportCSVFile from "./Utils/exportCSVFile"
import { getFileListAPI } from "../../api/StorageAPI";
import axios from 'axios'
import DeleteRowDialog from "./DeleteRowDialog";
import MLResultTableRow from "./MLResult/MLResultTableRow";

const hostLocation = process.env.SERVER_HOST ? process.env.SERVER_HOST : 'localhost'

export type Body = {
    [key: string]: string
}

export type MetaData = {
    body: Body,
    metadataId: string,
    projectId: string
};

// Page 리팩토링 후 Page로 옮길 타입
export type User = {
    userId: string,
    name: string,
    email: string,
    picture: string,
    role: string
};

// Page 리팩토링 후 Page로 옮길 타입
export type Project = {
    creator: User,
    projectId: string,
    projectName: string,
    visitor: User[]
};

export type MetaDataGridProps = {
    metaData: MetaData[],
    project: Project
};

const MetaDataGrid: React.FC<MetaDataGridProps> = ({ metaData, project }) => {
    const body = extractBody(metaData);
    const columns = extractColumns(body);

    const dispatch = useDispatch();
    const selectedRow = useSelector((state: RootState) => state.MetaDataGridReducer.selectedRow);
    const projectId = useSelector((state: RootState) => state.ParticipantInfoReducer.participants.projectId);

    getFileListAPI(
        projectId,
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
        const data = e.changes[0].data;
        const metadataId = data.metadataId;
        delete data.metadataId;
        const url = `api/MetaData/${metadataId}`;

        axios.put(url, data)
            .then(response => console.log(response))
            .catch(error => console.log(error));
    };

    const handleCSVDownloadButtonClick = () => {
        const selectedMetaData = selectedRow.map(row => {
            delete row.metadataId;
            return row;
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
            //     delete row.metadataId;
            //     return row;
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
                dataSource={body}
                defaultColumns={columns}
                keyExpr="metadataId"
                showBorders={true}
                hoverStateEnabled={true}
                onSelectionChanged={onSelectionChanged}
                onSaved={onSaved}
            >
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
                <Column type="buttons" width={110}>
                    <Button name="edit" />
                </Column>
                <MasterDetail
                    enabled={true}
                    render={MLResultTableRow}
                />
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
                        <DeleteRowDialog project={project} />
                    </Item>
                </Toolbar>
            </DataGrid>
        </Box>
    );
};

export default MetaDataGrid;