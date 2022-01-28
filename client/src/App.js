import * as React from 'react'
import {useState} from 'react';
import Page from './Page'
import LoadingPage from './component/Login/loading';
export default function App(){ 
    const [loginStatus,setLoginStatus]=useState()

    if(loginStatus===undefined){
        return(
            <LoadingPage message="사용자 정보를 확인중입니다."/>
        )
    }
    else{
        return(<Page/>)
    }
}