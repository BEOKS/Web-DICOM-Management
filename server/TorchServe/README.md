# 6. Machine Learning Result Visualization
DSMP is basically a platform for building databases, but we felt the need for the ability to visualize machine learning inference results in the beta test stage. You can use this feature by building a machine learning server using Torchserve. Users can choose which model to use to infer the currently uploaded data. When inference is started with the selected model, the backend automatically delivers the medical image of the project to the machine learning server and saves the result back to the database. Results in the form of strings and numbers are updated in metadata, and image result is added to the image database.

# Usage
## 1. Install Torchserve
If you want to store and show ML result, you need to install Torchserve which can make ML inference with HTTP API and Pytorch. You can check details about installation [here](https://github.com/pytorch/serve/blob/master/README.md#serve-a-model).

## 2. Create Model Archive
As Metioned [here](https://github.com/pytorch/serve/tree/master/model-archiver#creating-a-model-archive), you need to create ```.mar``` file for upload Pytorch model in Torchserve. If you successfully upload it, HTTP API for inference and management is automatically build.

For creating custom service ```.mar``` file. You can check [here](https://pytorch.org/serve/custom_service.html) for details, but it is a little bit complicated, so I'll briefly explan it here. 

### 2.1. Ready Pytorch Script
Save the completed Pytorch model as a single file with weights in [jit.pt](https://pytorch.org/docs/stable/generated/torch.jit.save.html) format. 
### 2.2. Base Handler
If you have a torch model, the next step is to create a Handler file to define the pre-processing, inference, and post-processing processes.
```python
# Reference : https://pytorch.org/serve/custom_service.html#writing-a-custom-handler-from-scratch-for-prediction-and-explanations-request

# custom handler file

# model_handler.py

"""
ModelHandler defines a custom model handler.
"""

from ts.torch_handler.base_handler import BaseHandler

class ModelHandler(BaseHandler):
    """
    A custom model handler implementation.
    """

    def __init__(self):
        self._context = None
        self.initialized = False
        self.explain = False
        self.target = 0

    def initialize(self, context):
        """
        Initialize model. This will be called during model loading time
        :param context: Initial context contains model server system properties.
        :return:
        """
        self._context = context
        self.initialized = True
        #  load the model, refer 'custom handler class' above for details

    def preprocess(self, data):
        """
        Transform raw input into model input data.
        :param batch: list of raw requests, should match batch size
        :return: list of preprocessed model input data
        """
        # Take the input data and make it inference ready
        preprocessed_data = data[0].get("data")
        if preprocessed_data is None:
            preprocessed_data = data[0].get("body")

        return preprocessed_data


    def inference(self, model_input):
        """
        Internal inference methods
        :param model_input: transformed model input data
        :return: list of inference output in NDArray
        """
        # Do some inference call to engine here and return output
        model_output = self.model.forward(model_input)
        return model_output

    def postprocess(self, inference_output):
        """
        Return inference result.
        :param inference_output: list of inference output
        :return: list of predict results
        """
        # Take output from network and post-process to desired format
        postprocess_output = inference_output
        return postprocess_output

    def handle(self, data, context):
        """
        Invoke by TorchServe for prediction request.
        Do pre-processing of data, prediction using model and postprocessing of prediciton output
        :param data: Input data for prediction
        :param context: Initial context contains model server system properties.
        :return: prediction output
        """
        model_input = self.preprocess(data)
        model_output = self.inference(model_input)
        return self.postprocess(model_output)
```
Keep in mind that input files are delivered in batch format, and please specify all processes correctly.
### 2.3 Create Mar File
If you get torch model file and Handler file, you are ready to create ```.mar``` file with torch-model-archiver. For details, please check [here](https://github.com/pytorch/serve/tree/master/model-archiver#torch-model-archiver-command-line-interface).

## 3. Running Torchserve
You can now run server with ```torchserve``` command. Move your ```.mar``` file to models directory, ans run command below. All models in models directory will automatically runned. Then you can inference with HTTP API.
```sh
torchserve --model-store /models
```
Check for detail command option [here](https://pytorch.org/serve/server.html#model-files)

## 4. Creat ML Service in Spring Boot 
 The model server ran well, but now you have to deliver the file inferred from the middleware, receive it again, and process it. For that, you have to create service file like [this](../DSMP/src/main/java/com/knuipalab/dsmp/service/machineLearning/BasicMalignancyServerMessenger.java).