import React from "react"
import DataGrid, { Selection, FilterRow } from 'devextreme-react/data-grid';
import { Box } from "@mui/material";
import { extractBody, extractColumns } from "./Utils/extractMetaData";

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

    return (
        <Box m={3} >
            <DataGrid
                dataSource={body}
                defaultColumns={columns}
                showBorders={true}
            >
                <Selection
                    mode="multiple"
                    selectAllMode="allPages"
                    showCheckBoxesMode="always"
                />
                <FilterRow visible={true} />
            </DataGrid>
        </Box>
    );
};

export default MetaDataGrid;