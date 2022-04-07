export const HEADER='DicomUploadBox'
const TYPE={
    SET_STATUS_FILE_LOADING : `${HEADER}/SET_STATUS_FILE_LOADING` as const,
    SET_STATUS_FILE_LOADING_FINISH : `${HEADER}/SET_STATUS_FILE_LOADING_FINISH`as const
}
const DicomUploadBoxAction={
    setStatusLoading : ()=>({type : TYPE.SET_STATUS_FILE_LOADING}),
    setStatusFinish : ()=>({type : TYPE.SET_STATUS_FILE_LOADING_FINISH})
}

type CounterAction=
    | ReturnType<typeof DicomUploadBoxAction.setStatusLoading>
    | ReturnType<typeof DicomUploadBoxAction.setStatusFinish>

enum Status{
    WAIT,LOADING,FINISH
}

type DicomUploadBoxState={
    dicomUplodingStatus : Status
}

const INIT_STATE : DicomUploadBoxState={
    dicomUplodingStatus: Status.WAIT
}

export default function DicomUploadBoxReducer(state :DicomUploadBoxState=INIT_STATE, action: CounterAction): DicomUploadBoxState{
    switch(action.type){
        case TYPE.SET_STATUS_FILE_LOADING:
            return {...state,dicomUplodingStatus : Status.LOADING}
        case TYPE.SET_STATUS_FILE_LOADING_FINISH:
            return {...state,dicomUplodingStatus: Status.FINISH}
        default:
            return state
    }
}
export {DicomUploadBoxAction,Status}