# Author: Chanho Kim <knight970815@gmail.com>

import os
import re
import cv2
import numpy as np
from PIL import Image

MAX_VALUE = 255

def combined_operations(img, kernel_size=1):
    output = cv2.Laplacian(img, cv2.CV_8U)
    _, output = cv2.threshold(output, 1, MAX_VALUE, cv2.THRESH_BINARY)

    kernel = np.ones((kernel_size, kernel_size),np.uint8)
    output = cv2.morphologyEx(output, cv2.MORPH_CLOSE, kernel)

    contours, _ = cv2.findContours(output, cv2.RETR_CCOMP, cv2.CHAIN_APPROX_SIMPLE)
    for cnt in contours:
        cv2.drawContours(output, [cnt], 0, MAX_VALUE, -1)

    return output

def extract_roi(img):
    height, width = img.shape

    start_x = 0
    end_x = width - 1
    start_y = 0
    end_y = height - 1

    output = combined_operations(img, 5)

    x_axis = np.count_nonzero(output, axis=0)
    for i, value in enumerate(x_axis):
        if i > 250 and i < width - 200:
            continue
        if value < 200:
            output[:,i] = 0

    w = width // 2
    for h in range(0, height-1):
        if output.item(h, w) * output.item(h+1, w):
            start_y = h + 10
            break

    for w in range(width//2, 0, -1):
        if not output.item(start_y, w) + output.item(start_y, w-1):
            start_x = w + 10
            break

    for w in range(width//2, width-1):
        if not output.item(start_y, w) + output.item(start_y, w+1):
            end_x = w - 10
            break

    y_axis = np.count_nonzero(output[start_y:,start_x:end_x], axis=1)
    for i, value in enumerate(y_axis):
        if value < 150:
            end_y = i + start_y - 10
            break

    return start_x, end_x, start_y, end_y

def remove_char(img):
    height, width = img.shape

    end_y = height - 1

    output = np.array(img) 
    output = output[-55:,20:width//2]
    _, output = cv2.threshold(output, 225, MAX_VALUE, cv2.THRESH_BINARY)

    y, _ = np.nonzero(output)
    if len(y):
        end_y = height - 70 + y.min()
    del(output)

    return end_y

def remove_sidebar(img):
    height, width = img.shape

    end_x = width - 1

    output = np.array(img) 
    _, output = cv2.threshold(output, 210, MAX_VALUE, cv2.THRESH_BINARY)

    x_axis = np.count_nonzero(output, axis=0)
    for i, value in enumerate(x_axis):
        if i < width - 100:
            continue
        if value > 90:
            end_x = i - 10
            break

    del(output)
    del(x_axis)

    if end_x == width - 1:
        end_x -= 10

    output = np.array(img) 
    output[output<50] = 0

    x_axis = np.count_nonzero(output, axis=0)
    for i, value in enumerate(x_axis[:end_x]):
        if i < width - 100:
            continue
        if not value:
            end_x = i - 10
            break

    return end_x

def check_dir(dir_path):
    if not os.path.exists(dir_path):
        os.mkdir(dir_path)

def cropImage(img):
    img=np.asarray(img)
    output = img[80:-40,45:-70]
    start_x, end_x, start_y, end_y = extract_roi(output)
    output = output[start_y:end_y,start_x:end_x]

    char = remove_char(output)
    output = output[:char,:]

    side = remove_sidebar(output)
    output = output[:,:side]
    return output

def main(input_dir):
    output_dir = "/home/jaeseong/DicomProject/server/TorchServe/FileSample/sample_crop"

    check_dir(output_dir)

    files = [f for f in os.listdir(input_dir) if re.match(r".*\.(jpg|png)", f)]

    for i, f in enumerate(files):
        print(f"{i:04d} {f}")

        if os.path.exists(f"{output_dir}/{f}"):
            continue

        #img = cv2.imread(f"{input_dir}/{f}", cv2.IMREAD_GRAYSCALE)
        img=Image.open(f"{input_dir}/{f}").convert("L")
        output=cropImage(img)
        print(type(output))
        # img=np.asarray(img)
        # print(type(img))
        # if img is None:
        #     continue

        # output = img[80:-40,45:-70]

        # start_x, end_x, start_y, end_y = extract_roi(output)
        # output = output[start_y:end_y,start_x:end_x]

        # if (start_y >= end_y) or (start_x >= end_x):
        #     continue

        # char = remove_char(output)
        # output = output[:char,:]

        # side = remove_sidebar(output)
        # output = output[:,:side]

        cv2.imwrite(f"{output_dir}/{f}", output)
        del(output)

