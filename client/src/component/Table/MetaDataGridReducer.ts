// action
const HEADER = 'MetaDataGridReducer';
const TYPE = {
    SET_SELECTED_ROW: `${HEADER}/SET_SELECTED_ROW` as const,
    SET_SELECTED_METADATA_ID: `${HEADER}/SET_SELECTED_METADATA_ID` as const,
    OPEN_DELETE_ROW_DIALOG: `${HEADER}/OPEN_DELETE_ROW_DIALOG` as const,
    CLOSE_DELETE_ROW_DIALOG: `${HEADER}/CLOSE_DELETE_ROW_DIALOG` as const,
    SET_SELECTED_STUDY_UID: `${HEADER}/SET_SELECTED_STUDY_UID` as const,
    SET_METADATA: `${HEADER}/SET_METADATA` as const,
    SET_BODY: `${HEADER}/SET_BODY` as const,
    SET_COLUMNS: `${HEADER}/SET_COLUMNS` as const,
};

export const MetaDataGridAction = {
    setSelectedRow: (row: Body[]) => ({ type: TYPE.SET_SELECTED_ROW, payload: row }),
    setSelectedMetaDataID: (metaDataID: string[]) => ({ type: TYPE.SET_SELECTED_METADATA_ID, payload: metaDataID }),
    openDeleteRowDialog: () => ({ type: TYPE.OPEN_DELETE_ROW_DIALOG }),
    closeDeleteRowDialog: () => ({ type: TYPE.CLOSE_DELETE_ROW_DIALOG }),
    setSelectedStudyUID: (studyUID: string[]) => ({ type: TYPE.SET_SELECTED_STUDY_UID, payload: studyUID }),
    setMetaData: (metaData: string | MetaData[]) => ({ type: TYPE.SET_METADATA, payload: metaData }),
    setBody: (body: Body[]) => ({ type: TYPE.SET_BODY, payload: body }),
    setColumns: (columns: string[]) => ({ type: TYPE.SET_COLUMNS, payload: columns }),
};

type MetaDataGridActionType =
    ReturnType<typeof MetaDataGridAction.setSelectedRow> |
    ReturnType<typeof MetaDataGridAction.setSelectedMetaDataID> |
    ReturnType<typeof MetaDataGridAction.openDeleteRowDialog> |
    ReturnType<typeof MetaDataGridAction.closeDeleteRowDialog> |
    ReturnType<typeof MetaDataGridAction.setSelectedStudyUID> |
    ReturnType<typeof MetaDataGridAction.setMetaData> |
    ReturnType<typeof MetaDataGridAction.setBody> |
    ReturnType<typeof MetaDataGridAction.setColumns>;


// state
export type Body = {
    [key: string]: string,
}

export type MetaData = {
    body: Body,
    metadataId: string,
    projectId: string
};

type MetaDataGridStateType = {
    selectedRow: Body[],
    selectedMetaDataID: string[],
    deleteRowDialogOpen: boolean,
    selectedStudyUID: string[],
    metaData: string | MetaData[],
    body: Body[],
    columns: string[],
}

const INIT_METADATAGRID_STATE: MetaDataGridStateType = {
    selectedRow: [], // 선택된 행들의 (metadataId가 포함된) Body 리스트
    selectedMetaDataID: [], // 선택된 행들의 metadataId 리스트
    deleteRowDialogOpen: false,
    selectedStudyUID: [], // 선택된 행들의 studyUID 리스트
    metaData: 'loading',
    body: [],
    columns: [],
};


// reducer
export default function MetaDataGridReducer(state: MetaDataGridStateType = INIT_METADATAGRID_STATE, action: MetaDataGridActionType): MetaDataGridStateType {
    switch (action.type) {
        case TYPE.SET_SELECTED_ROW:
            return { ...state, selectedRow: action.payload };
        case TYPE.SET_SELECTED_METADATA_ID:
            return { ...state, selectedMetaDataID: action.payload };
        case TYPE.OPEN_DELETE_ROW_DIALOG:
            return { ...state, deleteRowDialogOpen: true };
        case TYPE.CLOSE_DELETE_ROW_DIALOG:
            return { ...state, deleteRowDialogOpen: false };
        case TYPE.SET_SELECTED_STUDY_UID:
            return { ...state, selectedStudyUID: action.payload };
        case TYPE.SET_METADATA:
            return { ...state, metaData: action.payload };
        case TYPE.SET_BODY:
            return { ...state, body: action.payload };
        case TYPE.SET_COLUMNS:
            return { ...state, columns: action.payload };
        default:
            return state;
    }
}