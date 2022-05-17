import { Body } from "./MetaDataGrid";

// action
const HEADER = 'MetaDataGridReducer';
const TYPE = {
    SET_SELECTED_ROW: `${HEADER}/SET_SELECTED_ROW` as const,
    SET_SELECTED_METADATA_ID: `${HEADER}/SET_SELECTED_METADATA_ID` as const
};

export const MetaDataGridAction = {
    setSelectedRow: (row: Body[]) => ({ type: TYPE.SET_SELECTED_ROW, payload: row }),
    setSelectedMetaDataID: (metaDataID: string[]) => ({ type: TYPE.SET_SELECTED_METADATA_ID, payload: metaDataID })
};

type MetaDataGridActionType =
    ReturnType<typeof MetaDataGridAction.setSelectedRow> |
    ReturnType<typeof MetaDataGridAction.setSelectedMetaDataID>;


// state
type MetaDataGridStateType = {
    selectedRow: Body[]
    selectedMetaDataID: string[]
}

const INIT_METADATAGRID_STATE: MetaDataGridStateType = {
    selectedRow: [], // 선택된 행들의 (metadataId가 포함된) Body 리스트
    selectedMetaDataID: [] // 선택된 행들의 metadataId 리스트
};


// reducer
export default function MetaDataGridReducer(state: MetaDataGridStateType = INIT_METADATAGRID_STATE, action: MetaDataGridActionType): MetaDataGridStateType {
    switch (action.type) {
        case TYPE.SET_SELECTED_ROW:
            return { ...state, selectedRow: action.payload };
        case TYPE.SET_SELECTED_METADATA_ID:
            return { ...state, selectedMetaDataID: action.payload };
        default:
            return state;
    }
}