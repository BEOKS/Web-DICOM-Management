export type User={
    userId : string,
    name : string,
    email : string,
    picture : string, // picture link
    role : string
}
export function isUser(obj : any): boolean{
    return typeof obj ==='object' &&
        typeof obj['userId'] ==='string' &&
        typeof obj['name'] ==='string' &&
        typeof obj['email'] ==='string' &&
        typeof obj['picture'] ==='string' &&
        typeof obj['role'] ==='string'
}
export type Participants={
    projectId : string,
    projectName : string,
    creator : User,
    visitor : User[]
}
export function isParticipants(obj : any):boolean{
    return typeof obj ==='object' &&
        typeof obj['projectId'] ==='string' &&
        typeof obj['projectName'] ==='string' &&
        typeof isUser(obj['creator']) &&
        typeof obj['visitor'] ==='object' &&
        obj['visitor'].every((visitor :  User)=>isUser(visitor))
}
export enum LOADING_STATUS{
    LOADING,
    ERROR,
    FINISH,
    WAIT
}

type ParticipantInfoState={
    participants : Participants
    loadingStatus : LOADING_STATUS
}

const INIT_PARTICIPANT_INFO_STATE: ParticipantInfoState={
    participants : {
        projectId : '',
        projectName : '',
        creator : {userId : '',name : '',email : '',picture : '',role : ''},
        visitor : []
    },
    loadingStatus: LOADING_STATUS.LOADING,

}

const HEADER='ParticipantInfoReducer'
export const TYPE={
    SET_PARTICIPANT : `${HEADER}/SET_PARTICIPANT` as const,
    SET_LOADING_COMPLETE : `${HEADER}/SET_LOADING_COMPLETE` as const,
    SET_LOADING_START : `${HEADER}/SET_LOADING_START` as const,
    SET_ERROR_STATE : `${HEADER}/SET_ERROR_STATE` as const
}

export const ParticipantInfoAction={
    setParticipant : (participant : Participants)=>({type: TYPE.SET_PARTICIPANT,payload : participant}),
    setLoadingComplete : ()=>({type: TYPE.SET_LOADING_COMPLETE}),
    setLoadingStart : ()=>({type: TYPE.SET_LOADING_START}),
    setErrorState : ()=>({type : TYPE.SET_ERROR_STATE})
}

type ParticipantInfoActionType=
    ReturnType<typeof ParticipantInfoAction.setParticipant> |
    ReturnType<typeof ParticipantInfoAction.setLoadingComplete> |
    ReturnType<typeof ParticipantInfoAction.setLoadingStart> |
    ReturnType<typeof ParticipantInfoAction.setErrorState>

export default function ParticipantInfoReducer(state : ParticipantInfoState=INIT_PARTICIPANT_INFO_STATE,action : ParticipantInfoActionType): ParticipantInfoState{
    switch (action.type){
        case TYPE.SET_PARTICIPANT:
            return {...state,participants : action.payload,loadingStatus: LOADING_STATUS.FINISH}
        case TYPE.SET_LOADING_COMPLETE:
            return {...state,loadingStatus : LOADING_STATUS.FINISH}
        case TYPE.SET_LOADING_START:
            return {...state,loadingStatus : LOADING_STATUS.LOADING}
        case TYPE.SET_ERROR_STATE:
            return {...state,loadingStatus :LOADING_STATUS.ERROR}
        default :
            return state;
    }
}
