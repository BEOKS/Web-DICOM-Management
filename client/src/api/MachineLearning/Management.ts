// dummy API
import axios from "axios";
export const getModelList = (callback: (modelList: string[]) => void) => {
    axios.get('api/ML/getModelList')
        .then(response=>{
            const json = Array.from(response.data['models']).map((data : any) => data['modelName'])
            callback(json)
        })
        .catch(error =>{
            alert('사용 가능한 모델 검색 중 에러가 발생하였습니다. 다시 시도해주세요')
            callback([])
        })
    // callback(['model1', 'model2', 'model3', 'model4']);
};

// dummy API
export const inference = (modelName: string) => {
    alert(modelName + ' inference start!');
};