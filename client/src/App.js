import * as React from 'react'
import {useState} from 'react';
import Page from './Page'
import LoadingPage from './component/Login/loading';
import { chcekLoginStatusAsync } from './component/Login/Login';
export default function App(){ 
    const [loginStatus,setLoginStatus]=useState();
    if(loginStatus===undefined){
        chcekLoginStatusAsync(setLoginStatus);
        return(
            <LoadingPage message="사용자 정보를 확인중입니다."/>
        )
    }
    else if(loginStatus===false){
        window.location.href='/oauth2/authorization/google'
    }
    else{
        return(<Page/>)
    }
}