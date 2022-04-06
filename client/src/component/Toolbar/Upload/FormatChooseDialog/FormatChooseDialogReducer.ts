
export enum FORMAT_TYPE{
    DICOM,
    IMAGE
}
type FormatChooseDialogState={
    open : boolean,
    selectedFormat : FORMAT_TYPE | null
}

const init_state: FormatChooseDialogState={
    open : false,
    selectedFormat : null
}

const HEADER='FormatChooseDialog'
export const TYPE={
    OPEN_DIALOG: `${HEADER}/open` as const,
    CLOSE_DIALOG : `${HEADER}/close` as const,
    SELECT_FORMAT : `${HEADER}/format_choose` as const
}

export const FormatChooseAction={
    openDialog : ()=>({type : TYPE.OPEN_DIALOG}),
    closeDialog : ()=>({type : TYPE.CLOSE_DIALOG}),
    selectFormat : (format : FORMAT_TYPE)=>({type : TYPE.SELECT_FORMAT,payload : format})
}

type FormatChooseActionType=
    ReturnType<typeof FormatChooseAction.openDialog> |
    ReturnType<typeof FormatChooseAction.closeDialog> |
    ReturnType<typeof FormatChooseAction.selectFormat>

export default function FormatChooseReducer(state : FormatChooseDialogState=init_state,action: FormatChooseActionType): FormatChooseDialogState{
    switch (action.type) {
        case TYPE.OPEN_DIALOG:
            return {...state,open: true}
        case TYPE.CLOSE_DIALOG:
            return {...state,open:false}
        case TYPE.SELECT_FORMAT:
            return {...state,selectedFormat : action.payload}
        default:
            return state;
    }
}