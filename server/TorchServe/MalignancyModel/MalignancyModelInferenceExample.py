import cv2 
import torch
from torchvision import transforms
from PIL import Image
import numpy as np

transform = transforms.Compose([
    transforms.Grayscale(),
    transforms.Resize((224,224)),
    transforms.ToTensor()])

img_path = "/data/image/breast/ultrasound/NIA_set/1122524_0000_1.png"
img = Image.open(img_path) # PIL 이미지 한 장 입력

model = torch.jit.load("model.jit.pt") # model load
input = transform(Image.fromarray(img)).unsqueeze(0)
input = (input - input.mean()) / input.std()
output_dict = model(input)

prob = output_dict["output"].item() # Malignancy 확률값
pred = 1 if prob > 0.5 else 0
cam = output_dict["mask"].squeeze().cpu().numpy()
cam = np.uint8(cam*255)

heatmap = cv2.applyColorMap(cam, cv2.COLORMAP_JET) # colormap 때문에 numpy 변환 후 post-processing
heatmap = cv2.cvtColor(heatmap, cv2.COLOR_BGR2RGBA)
heatmap = Image.fromarray(heatmap).resize(img.size)

result = Image.blend(img.convert("RGBA"), heatmap, 0.3)