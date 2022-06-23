import axios from "axios";

const deleteDicom = (selectedStudyUID: string[]) => {
    if (selectedStudyUID.length > 0) {
        selectedStudyUID.forEach(studyUID => {
            const url = `api/study/${studyUID}`;

            axios.delete(url)
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
        });
    }
};

export { deleteDicom };