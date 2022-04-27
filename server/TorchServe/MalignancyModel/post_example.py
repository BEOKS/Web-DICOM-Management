import base64
import json
import requests
import io
import numpy as np
from PIL import Image
# Take in base64 string and return cv image
def stringToRGB(base64_string):
    imgdata = base64.b64decode(str(base64_string))
    image = Image.open(io.BytesIO(imgdata))
    return image
#server/TorchServe/FileSample/label_1/20110107_3647596_US_2_0003.png
#/home/jaeseong/DicomProject/server/TorchServe/FileSample/sample/c_826627_b_l_1_1.jpg

def inference(path):
    url="http://localhost:8098/predictions/Malignancy"
    response=requests.post(url,data=open(path, 'rb'))
    response=response.json()
    if response['cam']!='':
        response['cam']=stringToRGB(response['cam'])
    return response

from glob import glob

for image_path in glob('/home/jaeseong/DicomProject/server/DSMP/Storage/*/*.jpg'):
    result=inference(image_path)
    cam=result['cam']
    cam.save(image_path.split('.jpg')[0]+'.cam.jpg')