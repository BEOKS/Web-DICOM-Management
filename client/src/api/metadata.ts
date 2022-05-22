import axios from 'axios';

const updateMetaData = (body: any, metadataId: string) => {
    const url = 'api/MetaData/' + metadataId;

    axios.put(url, body)
        .catch(error => {
            alert('Metadata Update Fail')
        });
};

export { updateMetaData };