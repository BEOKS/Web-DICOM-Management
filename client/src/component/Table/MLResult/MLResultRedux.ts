
export interface MLResultReduxType{
    imageFileNames: string[]
}

const initMLResultRedux:MLResultReduxType={
    imageFileNames : []
}

const HEADER='MLResultReduxAction'
const MLResultActionCase={
    SET_IMAGE_FILE_NAMES : `${HEADER}/SET_IMAGE_FILE_NAMES`
}
export const MLResultReduxAction={
    setImageFileNames : (data : string[])=>({type : MLResultActionCase.SET_IMAGE_FILE_NAMES, payload: data})
}
type MLResultActionType=
    ReturnType<typeof MLResultReduxAction.setImageFileNames>

export default function  MLResultReducer(state : MLResultReduxType=initMLResultRedux,action : MLResultActionType): MLResultReduxType{
    switch (action.type){
        case MLResultActionCase.SET_IMAGE_FILE_NAMES:
            return {...state,imageFileNames : action.payload}
        default:
            return state
    }
}
