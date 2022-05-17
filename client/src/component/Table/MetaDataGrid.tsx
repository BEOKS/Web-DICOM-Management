import React from "react"
import { useDispatch, useSelector } from "react-redux";
import { RootState } from '../../store';
import { MetaDataGridAction } from './MetaDataGridReducer';
import DataGrid, { Selection, FilterRow, Toolbar, Item } from 'devextreme-react/data-grid';
import { Box, Tooltip, IconButton, Typography } from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";
import CloudDownloadIcon from '@mui/icons-material/CloudDownload';
import FileDownloadIcon from '@mui/icons-material/FileDownload';
import { extractBody, extractColumns } from "./Utils/extractMetaData";
import exportCSVFile from "./Utils/exportCSVFile"
import './MetaDataGrid.css';

export type Body = {
    [key: string]: string
}

export type MetaData = {
    body: Body,
    metadataId: string,
    projectId: string
};

// Page 리팩토링 후 Page로 옮길 타입들
type User = {
    userId: string,
    name: string,
    email: string,
    picture: string,
    role: string
};

type Project = {
    creator: User,
    projectId: string,
    projectName: string,
    visitor: User[]
};

type MetaDataGridProps = {
    metaData: MetaData[],
    project: Project
};

const MetaDataGrid: React.FC<MetaDataGridProps> = ({ metaData, project }) => {
    const body = extractBody(metaData);
    const columns = extractColumns(body);

    const dispatch = useDispatch();
    const selectedRow = useSelector((state: RootState) => state.MetaDataGridReducer.selectedRow);
    const selectedMetaDataID = useSelector((state: RootState) => state.MetaDataGridReducer.selectedMetaDataID);


    const onSelectionChanged = ({ selectedRowsData }: { selectedRowsData: Body[] }) => {
        dispatch(MetaDataGridAction.setSelectedRow(selectedRowsData));
        dispatch(MetaDataGridAction.setSelectedMetaDataID(selectedRowsData.map((row: Body) => row.metadataId)));
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

    return (
        <Box m={3} >
            <DataGrid
                dataSource={body}
                defaultColumns={columns}
                keyExpr="metadataId"
                showBorders={true}
                hoverStateEnabled={true}
                onSelectionChanged={onSelectionChanged}
            >
                <Selection
                    mode="multiple"
                    selectAllMode="allPages"
                    showCheckBoxesMode="always"
                />
                <FilterRow visible={true} />

                {/* Toolbar를 다른 컴포넌트로 분리했더니 화면에 나타나지 않아서 그대로 둠 */}
                <Toolbar>
                    <Item location="before">
                        <Typography ml={1}>{selectedMetaDataID.length} selected</Typography>
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
                            <IconButton>
                                <CloudDownloadIcon />
                            </IconButton>
                        </Tooltip>
                    </Item>
                    <Item location="after">
                        <Tooltip title="Delete">
                            <IconButton>
                                <DeleteIcon />
                            </IconButton>
                        </Tooltip>
                    </Item>
                </Toolbar>
            </DataGrid>
        </Box>
    );
};

export default MetaDataGrid;