export type Schema=object
const HEADER='ColumnTypeDesicionDialog'
const TYPE={
    OPEN_DIALOG : `${HEADER}/OPEN_DIALOG` as const,
    CLOSE_DIALOG : `${HEADER}/CLOSE_DIALOG`as const,
    SET_COLUMN_LIST : `${HEADER}/SET_COLUMN_LIST`as const,
    SET_COLUMN_SCHEMA : `${HEADER}/SET_COLUMN_SCHEMA`as const
}
const DialogAction={
    openDialog : ()=>({type: TYPE.OPEN_DIALOG}),
    closeDialog : ()=>({type: TYPE.CLOSE_DIALOG}),
    setColumnList : (columnList : string[])=>({type : TYPE.SET_COLUMN_LIST, payload : columnList}),
    setColumnSchema : (columnSchema : Schema)=>({type : TYPE.SET_COLUMN_SCHEMA, payload:columnSchema })
}

type CounterAction =
    | ReturnType<typeof DialogAction.openDialog>
    | ReturnType<typeof DialogAction.closeDialog>
    | ReturnType<typeof DialogAction.setColumnList>
    | ReturnType<typeof DialogAction.setColumnSchema>;

type DialogState={
    columnTypeDecisionOpenStatus : boolean,
    columnList: string[],
    columnSchema : Schema
}

const INIT_DIALOG_STATE : DialogState={
    columnTypeDecisionOpenStatus: false,
    columnList: [],
    columnSchema : {}
}

// type DialogAction=


function ColumnTypeDecisionDialogReducer(state :DialogState =INIT_DIALOG_STATE, action: CounterAction) : DialogState{
    switch (action.type){
        case TYPE.OPEN_DIALOG:
            return {...state, columnTypeDecisionOpenStatus : true}
        case TYPE.CLOSE_DIALOG:
            return {...state, columnTypeDecisionOpenStatus: false}
        case TYPE.SET_COLUMN_LIST:
            return {...state, columnList : action.payload}
        case TYPE.SET_COLUMN_SCHEMA:
            return {...state, columnSchema: action.payload}
        default:
            return state
    }
}

export default ColumnTypeDecisionDialogReducer;
export {DialogAction}