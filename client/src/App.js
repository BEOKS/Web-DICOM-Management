import * as React from 'react'
import {useState} from 'react';
import Page from './component/HomePage/Page'
import LoadingPage from './component/Login/Loading';
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
        return(<div></div>)
    }
    else if(loginStatus){
        return(<Page/>)
    }
    else{
        alert("로그인 확인 중 오류가 발생했습니다.")
    }
}