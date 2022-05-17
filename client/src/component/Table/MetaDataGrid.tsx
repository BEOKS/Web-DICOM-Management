import React from "react"
import { useState } from "react";
import DataGrid, { Selection, FilterRow, Toolbar, Item } from 'devextreme-react/data-grid';
import { Box, Tooltip, IconButton, Typography } from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";
import CloudDownloadIcon from '@mui/icons-material/CloudDownload';
import FileDownloadIcon from '@mui/icons-material/FileDownload';
import { extractBody, extractColumns } from "./Utils/extractMetaData";
import './MetaDataGrid.css';

export type Body = {
    [key: string]: string
}

export type MetaData = {
    body: Body,
    metadataId: string,
    projectId: string
};

type MetaDataGridProps = {
    metaData: MetaData[]
};

const MetaDataGrid: React.FC<MetaDataGridProps> = ({ metaData }) => {
    const body = extractBody(metaData);
    const columns = extractColumns(body);
    const [selected, setSelected] = useState<string[]>([]); // 선택된 행들의 metadataId 리스트

    const onSelectionChanged = ({ selectedRowsData }: { selectedRowsData: Body[] }) => {
        setSelected(selectedRowsData.map((row: Body) => row.metadataId));
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
                        <Typography ml={1}>{selected.length} selected</Typography>
                    </Item>
                    <Item location="after">
                        <Tooltip title="Download CSV">
                            <IconButton>
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