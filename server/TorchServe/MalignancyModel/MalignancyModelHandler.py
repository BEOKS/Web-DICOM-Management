import io
from PIL import Image
import torch
from ts.torch_handler.base_handler import BaseHandler
from torchvision import transforms
import base64
import sys
print("asdf",sys.version)
from io import BytesIO
import json

import cv2 

class MalignancyModelHandler(BaseHandler):
    """
    MalignancyModel의 전처리, 추론, 후처리를 진행하기 위한
    핸들러 클래스 파일입니다.
    """

    def __init__(self, *args, **kwargs):
        super().__init__()
        self.transform = transforms.Compose([
            transforms.Grayscale(),
            transforms.Resize((224,224)),
            transforms.ToTensor()])

    def preprocess_one_image(self, req):
        """
        Process one single image.
        """
        # get image from the request
        image = req.get("data")
        if image is None:
            image = req.get("body")       
         # create a stream from the encoded image
        image = Image.open(io.BytesIO(image))
        self.original = image
        image = self.transform(image)
        # add batch dim
        image = image.unsqueeze(0)
        return image

    def preprocess(self, requests):
        """
        Process all the images from the requests and batch them in a Tensor.
        """
        images = [self.preprocess_one_image(req) for req in requests]
        images = torch.cat(images)    
        return images

    def inference(self, x):
        """
        Given the data from .preprocess, perform inference using the model.
        We return the predicted label for each image.
        """
        output_dict = self.model(x)
        prob = output_dict["prob"].item() # Malignancy 확률값
        pred = output_dict["pred"].item() # 0: Non-malignancy, 1: Malignancy
        cam = output_dict["cam"] # cam
        return [[prob,pred,cam]]

    def postprocess(self, preds):
        """
        Given the data from .inference, postprocess the output.
        In our case, we get the human readable label from the mapping 
        file and return a json. Keep in mind that the reply must always
        be an array since we are returning a batch of responses.
        """
        res = []
        # pres has size [BATCH_SIZE, 1]
        # convert it to list
        for index,value in enumerate(preds):
            prob,pred,cam=value
            pred = 'Malignancy' if pred else 'Non-malignancy'
            if pred:
                img=self.original
                heatmap = cv2.applyColorMap(cam.numpy(), cv2.COLORMAP_JET) # colormap 때문에 numpy 변환 후 post-processing
                heatmap = cv2.cvtColor(heatmap, cv2.COLOR_BGR2RGBA)
                heatmap = Image.fromarray(heatmap).resize(img.size)
            
                result = Image.blend(img.convert("RGBA"), heatmap, 0.3)
                buffered = BytesIO()
                result.save(buffered, format="PNG")
                cam= base64.b64encode(buffered.getvalue()).decode('utf-8')
            else:
                cam=''
            print("asdfg",prob,pred,type(cam))
            res.append({'prob' : prob, 'pred': pred,'cam':cam })
        return res