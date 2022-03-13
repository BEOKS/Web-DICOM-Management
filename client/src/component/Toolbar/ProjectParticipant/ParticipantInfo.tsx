import * as React from 'react';
import {Alert, Avatar, CircularProgress, Stack, Tooltip, Typography} from "@mui/material";
import {useDispatch, useSelector} from "react-redux";
import {RootState} from "../../../store";
import {isUser, LOADING_STATUS, ParticipantInfoAction, Participants, User} from "./ParticipantInfoReducer";
import axios from "axios";

const getParticipantInfo=(responseCallback : (participant : Participants)=>void, errorCallback : ()=>void)=>{
    axios.get('/api/Project/invited')
        .then(response=>{
            if (isUser(response.data.body[0])){
                responseCallback(response.data.body[0])
            }
            else{
                errorCallback()
            }
        })
        .catch(error=>{
            errorCallback()
        })
}
export default function ParticipantInfo(){
    const loadingStatus : LOADING_STATUS= useSelector((state : RootState)=>state.ParticipantInfoReducer.loadingStatus)
    const participantInfo : Participants=useSelector((state:RootState)=> state.ParticipantInfoReducer.participants)
    const dispatch = useDispatch()
    if(loadingStatus===LOADING_STATUS.LOADING){
        getParticipantInfo(
            (participantInfo : Participants)=>{
                dispatch(ParticipantInfoAction.setParticipant(participantInfo))
            },()=>{dispatch(ParticipantInfoAction.setErrorState())})
    }
    return(
        <Stack >
            {loadingStatus===LOADING_STATUS.LOADING &&
                <Stack direction={"row"} style={{ verticalAlign: "middle" }}>
                    <Typography style={{ verticalAlign: "middle" }}>Loading User Information...</Typography>
                    <CircularProgress style={{padding: "7px"}}/>
                </Stack>
            }
            {loadingStatus===LOADING_STATUS.ERROR &&
                <Alert  severity={'error'}>참여자 정보를 가져오는데 실패했습니다.</Alert>
            }
            {loadingStatus===LOADING_STATUS.FINISH &&
                <Stack direction={"row"}>
                    <Tooltip title={`Creator, ${participantInfo.creator.name}`} key={0}>
                        <Avatar alt={participantInfo.creator.name} src={participantInfo.creator.picture}/>
                    </Tooltip>
                    {
                        participantInfo.visitor.map((visitor :User,index)=>
                            <Tooltip title={`${participantInfo.creator.name}`} key={index+1}>
                                <Avatar alt={visitor.name} src={visitor.picture}/>
                            </Tooltip>)
                    }
                </Stack>
            }
        </Stack>
    )
}