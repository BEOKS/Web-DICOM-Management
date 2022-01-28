import axios from 'axios';
const chcekLoginStatusAsync=(setLoginStatus)=>{
    axios.get('api/Project',{maxRedirects:0})
        .then(response=>setLoginStatus(true))
        .catch(error=>setLoginStatus(false));
}
export {chcekLoginStatusAsync};