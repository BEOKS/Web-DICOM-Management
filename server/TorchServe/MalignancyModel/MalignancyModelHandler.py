import io
from PIL import Image
from requests import head
import torch
from ts.torch_handler.base_handler import BaseHandler
from torchvision import transforms
import base64
import sys
from io import BytesIO
import json
import cv2 
import numpy as np
from CropUltrasound import cropImage
from scipy.stats import norm

MEAN = 0.498750471
STD = 0.33493005
T = 0.4

def base64Encoding(image):
    buffered = BytesIO()
    image.save(buffered, format="PNG")
    cam= base64.b64encode(buffered.getvalue()).decode('utf-8')
    return cam
class MalignancyModelHandler(BaseHandler):
    """
    MalignancyModel의 전처리, 추론, 후처리를 진행하기 위한
    핸들러 클래스 파일입니다.
    """

    def __init__(self, *args, **kwargs):
        super().__init__()
        self.pilTransform=transforms.Compose([transforms.ToPILImage()])
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
        image = Image.open(io.BytesIO(image)).convert("L")
        image=cropImage(image)
        image=self.pilTransform(image)
        self.original = image
        image = self.transform(image)
        # add batch dim
        image = image.unsqueeze(0)
        image=(image-image.mean())/image.std()
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
        prob = output_dict["output"].item() # Malignancy 확률값
        prob = (prob - MEAN) / STD
        prob = norm.cdf(prob)           # 최종 확률값

        pred = 'Malignancy' if prob > T else 'Non-malignancy' # 0: Non-malignancy, 1: Malignancy
        cam = output_dict["mask"].squeeze().cpu().numpy() # cam
        cam *=prob
        cam = cv2.resize(np.uint8(cam*255), self.original.size, interpolation=cv2.INTER_LINEAR) 
        cam[0,0] = 255
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
            img=self.original
            # if pred:
            #     heatmap = cv2.applyColorMap(cam.numpy(), cv2.COLORMAP_JET) # colormap 때문에 numpy 변환 후 post-processing
            #     heatmap = cv2.cvtColor(heatmap, cv2.COLOR_BGR2RGBA)
            #     heatmap = Image.fromarray(heatmap).resize(img.size)

            #     buffered = BytesIO()
            #     result = Image.blend(img.convert("RGBA"), heatmap, 0.3)
            #     result.save(buffered, format="PNG")
            #     cam= base64.b64encode(buffered.getvalue()).decode('utf-8')
            # else:
            #     cam=''
            # pred = 'Malignancy' if pred else 'Non-malignancy'
            prob=str(round(float(prob)*100,1))+'%'
            heatmap = cv2.applyColorMap(cam, cv2.COLORMAP_JET) # colormap 때문에 numpy 변환 후 post-processing
            heatmap = cv2.cvtColor(heatmap, cv2.COLOR_BGR2RGBA)
            heatmap = Image.fromarray(heatmap)

            crop= base64Encoding(img)
            heatmap=Image.blend(img.convert("RGBA"), heatmap, 0.3)
            cam = base64Encoding(heatmap)
            res.append({'prob' : prob, 'pred': pred,'crop': crop,'cam':cam })
        return res