import axios from "axios";
const url='/api/Project/'
const TAG='create project : '
const SUCCESS=1,FAIL=0;
const createProject=(projectName,callBackListener)=>{
    if(projectName || !(projectName instanceof String)){
        console.log(TAG, 'projectName is not valid ',projectName)
    }
    axios.post(url+projectName)
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