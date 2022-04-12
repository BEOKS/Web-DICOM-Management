import cv2 
import torch
from torchvision import transforms

transform = transforms.Compose([
    transforms.Grayscale(),
    transforms.Resize((224,224)),
    transforms.ToTensor()])

img_path = "/data/image/breast/ultrasound/NIA_set/1122524_0000_1.png"
img = Image.open(img_path) # PIL 이미지 한 장 입력

model = torch.jit.load("model.jit.pt") # model load
output_dict = model(transform(img).unsqueeze(0))

prob = output_dict["prob"].item() # Malignancy 확률값
pred = output_dict["pred"].item() # 0: Non-malignancy, 1: Malignancy
pred = 'Malignancy' if pred else 'Non-malignancy'
cam = output_dict["cam"] # cam

if pred: # Non-malignancy의 경우 cam 반환하지 않음 
    heatmap = cv2.applyColorMap(cam.numpy(), cv2.COLORMAP_JET) # colormap 때문에 numpy 변환 후 post-processing
    heatmap = cv2.cvtColor(heatmap, cv2.COLOR_BGR2RGBA)
    heatmap = Image.fromarray(heatmap).resize(img.size)
    
    result = Image.blend(img.convert("RGBA"), heatmap, 0.3)