import axios from "axios";
import { ProjectDrawerAction } from "../component/Drawer/ProjectDrawerReducer";

const getCreatedProjects = (dispatch: any, checkFirst: boolean, setCheckFirst: (x: boolean) => {}, setLoading: (x: boolean) => {}) => {
    const url = 'api/Project';
    const config = { maxRedirects: 0 };

    axios.get(url, config)
        .then(response => {
            if (response.data.body.length !== 0) {
                dispatch(ProjectDrawerAction.setCreatedProjects(response.data.body));
                if (checkFirst) {
                    dispatch(ProjectDrawerAction.setProject(response.data.body[0]));
                    setCheckFirst(false);
                }
            }
            setLoading(false)
        }).catch(error => {
            if (error.response) {
                alert(error.response.data.message);
                console.log(error.response.data);
            } else {
                alert('서버가 응답하지 않습니다.');
                console.log(error);
            }
        });
};

const getInvitedProjects = (dispatch: any, setLoading: (x: boolean) => {}) => {
    const url = 'api/Project/invited';
    const config = { maxRedirects: 0 };

    axios.get(url, config)
        .then(response => {
            if (response.data.body.length !== 0) {
                dispatch(ProjectDrawerAction.setInvitedProjects(response.data.body));
            }
            setLoading(false)
        }).catch(error => {
            if (error.response) {
                alert(error.response.data.message);
                console.log(error.response.data);
            } else {
                alert('서버가 응답하지 않습니다.');
                console.log(error);
            }
        });
};

export { getCreatedProjects, getInvitedProjects };