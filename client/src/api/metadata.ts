import axios from 'axios';
import { Project } from '../component/Drawer/ProjectDrawerReducer';
import { Body, MetaDataGridAction } from '../component/Table/MetaDataGridReducer';

const getMetaData = (project: Project, dispatch: any) => {
    const url = `api/MetaData/${project.projectId}`;

    axios.get(url)
        .then(response => {
            dispatch(MetaDataGridAction.setMetaData(response.data.body));
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

export { getMetaData, updateMetaData };