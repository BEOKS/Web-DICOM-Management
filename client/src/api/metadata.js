import axios from 'axios';

const url='/MetaData';

const updateMetaData=async(json)=>{
    const res=await axios.get(url)
    console.log(json,res)
    const response=await axios.post(url,json)
    console.log('updateMetaData : ',response)
    return response.data;
}

export {updateMetaData};