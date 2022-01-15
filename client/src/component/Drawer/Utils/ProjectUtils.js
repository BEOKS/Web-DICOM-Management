import axios from "axios";
const url='/api/Project'
const SUCCESS=1,FAIL=0;

const createProject=(projectName,callBackListener)=>{
    const json={"projectName":projectName};
    axios.post(url,json)
        .then(
            response=>{
                callBackListener(SUCCESS) 
            }
        )
        .catch(
            error=>{
                callBackListener(FAIL,error) 
            }
        )
}

export {createProject};