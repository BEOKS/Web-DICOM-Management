import axios from 'axios';
import { Project } from '../component/Drawer/ProjectDrawerReducer';
import { Body, MetaData } from '../component/Table/MetaDataGridReducer';

const getMetaData = (project: Project, onSuccess: (metaData: MetaData[]) => void) => {
    const url = `api/MetaData/${project.projectId}`;

    axios.get(url)
        .then(response => {
            onSuccess(response.data.body);
        }).catch(error => {
            if (error.response) {
                alert(error.response.data.message);
                console.log(error.response.data);
            } else {
                alert(error.message);
                console.log(error);
            }
        });
};

const updateMetaData = (body: Body, metadataId: string) => {
    const url = 'api/MetaData/' + metadataId;

    axios.put(url, body)
        .catch(error => {
            alert('Metadata Update Fail')
        });
};

const deleteMetaData = (project: Project, selectedMetaDataID: string[]) => {
    const url = `api/MetaDataList/delete/${project.projectId}`;
    const data = selectedMetaDataID;

    axios.post(url, data)
        .then(response => console.log(response))
        .catch(error => {
            if (error.response) {
                alert(error.response.data.message);
                console.log(error.response.data);
            } else {
                alert(error.message);
                console.log(error);
            }
        });
};

export { getMetaData, updateMetaData, deleteMetaData };