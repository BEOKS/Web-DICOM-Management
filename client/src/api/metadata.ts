import axios from 'axios';

const url='/MetaData';

const updateMetaData=(body : any, metadataId : string)=>{
    axios.put(`api/MetaData/${metadataId}`,body)
        .catch(error =>{
            alert('Metadata Update Fail')
        })
}

export {updateMetaData};