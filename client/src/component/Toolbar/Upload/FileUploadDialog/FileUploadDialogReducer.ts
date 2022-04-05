export type FileUploadDialogType={
    open : boolean
}
const init_type : FileUploadDialogType={
    open : false
}
const HEADER='FileUploadDialog'
const TYPE={
    OPEN_DIALOG : `${HEADER}/openDialog` as const,
    CLOSE_DIALOG : `${HEADER}/closeDialog` as const
}
export const FileUploadDialogAction={
    openDialog : ()=>({type : TYPE.OPEN_DIALOG}),
    closeDialog : ()=>({type : TYPE.CLOSE_DIALOG})
}

type FileUploadDialogActionType=
    ReturnType<typeof FileUploadDialogAction.openDialog>|
    ReturnType<typeof FileUploadDialogAction.closeDialog>

export default function FileUploadDialogReducer(state : FileUploadDialogType=init_type, action : FileUploadDialogActionType): FileUploadDialogType{
    switch (action.type){
        case TYPE.OPEN_DIALOG:
            return {...state,open : true}
        case TYPE.CLOSE_DIALOG:
            return {...state,open : false}
        default:
            return state
    }
}