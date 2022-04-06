export type SnackbarType={
    open : boolean,
    message : string,
    progress : number,
    closeButtonOpen : boolean
}

const init_state:SnackbarType={
    open : false,
    message : '',
    progress : 0,
    closeButtonOpen : false
}

const HEADER='SnackbarHeader'
const SnackbarActionType={
    openSnackbar : `${HEADER}/openSnackbar` as const,
    closeSnackbar : `${HEADER}/closeSnackbar` as const,
    setMessage : `${HEADER}/setMessage` as const,
    setProgress : `${HEADER}/setProgress` as const,
    showCloseButton : `${HEADER}/showCloseButton` as const,
    hideCloseButton : `${HEADER}/hideCloseButton` as const,
}

export const SnackbarAction={
    openSnackbar : ()=>({type : SnackbarActionType.openSnackbar}),
    closeSnackbar : ()=>({type : SnackbarActionType.closeSnackbar}),
    setMessage : (msg : string)=>({type : SnackbarActionType.setMessage,payload : msg}),
    setProgress : (progress : number)=>({type : SnackbarActionType.setProgress,payload : progress}),
    showCloseButton : ()=>({type : SnackbarActionType.showCloseButton}),
    hideCloseButton : ()=>({type : SnackbarActionType.hideCloseButton})
}

type SnackbarActionType=
    ReturnType<typeof SnackbarAction.openSnackbar> |
    ReturnType<typeof SnackbarAction.closeSnackbar> |
    ReturnType<typeof SnackbarAction.setMessage> |
    ReturnType<typeof SnackbarAction.setProgress> |
    ReturnType<typeof SnackbarAction.showCloseButton> |
    ReturnType<typeof SnackbarAction.hideCloseButton>
export default function SnackbarReducer(state : SnackbarType=init_state,action : SnackbarActionType): SnackbarType{
    switch (action.type){
        case SnackbarActionType.openSnackbar:
            return {...state,open : true}
        case SnackbarActionType.closeSnackbar:
            return {...state,open : false}
        case SnackbarActionType.setMessage:
            return {...state, open:true,message : action.payload}
        case SnackbarActionType.setProgress:
            return {...state,progress : action.payload}
        case SnackbarActionType.showCloseButton:
            return {...state,closeButtonOpen : true}
        case SnackbarActionType.hideCloseButton:
            return {...state,closeButtonOpen : false}
        default:
            return state
    }

}