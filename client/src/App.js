import 'devextreme/dist/css/dx.common.css';
import 'devextreme/dist/css/dx.light.css';
import * as React from 'react'
import {useState} from 'react';
import Page from './Page'
import LoadingPage from './component/Login/Loading';
import { chcekLoginStatusAsync } from './component/Login/Login';
export default function App(){ 
    const [loginStatus,setLoginStatus]=useState();
    if(loginStatus===undefined){
        chcekLoginStatusAsync(setLoginStatus);
        console.log('사용자 정보를 확인중입니다..')
        return(
            <LoadingPage message="사용자 정보를 확인중입니다."/>
        )
    }
    else if(loginStatus===false){
        console.log('1초 뒤 페이지가 이동됩니다.')
        window.location.href='/oauth2/authorization/naver'
        return(<div></div>)
    }
    else if(loginStatus){
        return(<Page/>)
    }
    else{
        alert("로그인 확인 중 오류가 발생했습니다.")
    }
}