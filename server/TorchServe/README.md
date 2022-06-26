# 6. Machine Learning Result Visualization
DSMP is basically a platform for building databases, but we felt the need for the ability to visualize machine learning inference results in the beta test stage. You can use this feature by building a machine learning server using Torchserve. Users can choose which model to use to infer the currently uploaded data. When inference is started with the selected model, the backend automatically delivers the medical image of the project to the machine learning server and saves the result back to the database. Results in the form of strings and numbers are updated in metadata, and image result is added to the image database.

# Usage
## 1. Install Torchserve
If you want to store and show ML result, you need to install Torchserve which can make ML inference with HTTP API and Pytorch. You can check details about installation [here](https://github.com/pytorch/serve/blob/master/README.md#serve-a-model).

## 2. Create Model Archive
As Metioned [here](https://github.com/pytorch/serve/tree/master/model-archiver#creating-a-model-archive), you need to create ```.mar``` file for upload Pytorch model in Torchserve. If you successfully upload it, HTTP API for inference and management is automatically build.

For creating custom service 


