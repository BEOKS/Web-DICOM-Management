// dummy API
export const getModelList = (callback: (modelList: string[]) => void) => {
    callback(['model1', 'model2', 'model3', 'model4']);
};

// dummy API
export const inference = (modelName: string) => {
    alert(modelName + ' inference start!');
};