import axios from 'axios';

const url='/MetaData';

const updateMetaData=async(json)=>{
    const response=await axios.post(url,json)
    return response;
}

export {updateMetaData};