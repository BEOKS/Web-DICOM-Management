# Author: Chanho Kim <knight970815@gmail.com>

import os
import random
import shutil

import pandas as pd
import torch
import torch.nn as nn
import torch.nn.functional as F
import torch.optim as optim
from torch.utils.data import DataLoader, random_split
from torchvision import transforms
from torchvision.datasets import ImageFolder
import torchvision.models as models
import torchmetrics.functional as FM
import pytorch_lightning as pl


class Model(pl.LightningModule):
    
    def __init__(self):
        super().__init__()

        self.model = models.googlenet(pretrained=True)
        self.model.fc = nn.Linear(1024, 2)
        
    def forward(self, x):
        return self.model(x)

    def training_step(self, batch, batch_idx):
        x, y = batch
        output = self(x)
        loss = F.cross_entropy(output, y)
        return loss

    def validation_step(self, batch, batch_idx):
        x, y = batch
        output = self(x)
        loss = F.cross_entropy(output, y)
        acc = FM.accuracy(output, y)
        metrics = {"val_loss": loss, "val_acc": acc}
        self.log_dict(metrics)

    def test_step(self, batch, batch_idx):
        x, y = batch
        output = self(x)
        loss = F.cross_entropy(output, y)
        acc = FM.accuracy(output, y)
        metrics = {"test_loss": loss, "test_acc": acc}
        self.log_dict(metrics)

    def predict_step(self, batch, batch_idx):
        x, y, f = batch
        output = self(x)

        pred = output.argmax(dim=1)
        prob = F.softmax(output, dim=1)
        return {"pred": pred, "prob": prob, "target": y, "file": f}

    def configure_optimizers(self):
        optimizer = optim.Adam(self.parameters(), lr=1e-4)
        return optimizer


class ImageFolderWithPaths(ImageFolder):

    def __getitem__(self, index):
        original_tuple = super().__getitem__(index)
        path = self.imgs[index][0]
        tuple_with_path = (original_tuple + (path,))
        return tuple_with_path


def train_base_model(t=0.1):
    batch_size = 32
    patience = 50

    data_path = "/data/image/breast/ultrasound/dicom_2011/"
    test_data_path = "/data/image/breast/ultrasound/non_label_set/"
    output_path = f"/data/results/semi/semi_0"

    os.environ["CUDA_DEVICE_ORDER"] = "PCI_BUS_ID"
    os.environ["CUDA_VISIBLE_DEVICES"] = "0"

    pl.seed_everything(42)

    transform = transforms.Compose([
        transforms.Resize((224,224)),
        transforms.ToTensor(), 
    ])

    dataset = ImageFolder(data_path, transform=transform)
    train, val = random_split(dataset, [round(len(dataset)*(3/4)), round(len(dataset)*(1/4))])

    test = ImageFolderWithPaths(test_data_path, transform=transform)
    
    train_loader = DataLoader(train, batch_size=batch_size, shuffle=True, num_workers=os.cpu_count(), pin_memory=True)
    val_loader = DataLoader(val, batch_size=batch_size, shuffle=False, num_workers=os.cpu_count(), pin_memory=True)
    test_loader = DataLoader(test, batch_size=batch_size, shuffle=False, num_workers=os.cpu_count(), pin_memory=True)

    checkpoint_callback = pl.callbacks.ModelCheckpoint(dirpath=output_path, filename="checkpoint", monitor="val_acc", mode="max")
    early_stopping = pl.callbacks.EarlyStopping(monitor="val_acc", mode="max", patience=patience, verbose=True)

    model = Model()
    trainer = pl.Trainer(max_epochs=3, callbacks=[checkpoint_callback, early_stopping], default_root_dir=output_path, gpus=torch.cuda.device_count(), log_every_n_steps=1)
    trainer.fit(model, train_loader, val_loader)
    output = trainer.predict(dataloaders=test_loader, ckpt_path="best")

    fs =  pd.read_excel("excels/I-Data_0.xlsx", sheet_name="Sheet1")
    fs.fillna("", inplace=True)

    malignancy = fs["Malignancy"].values.tolist()
    for out in output:
        prob = out["prob"]
        file = out["file"]

        for p, f in zip(prob, file):
            if os.path.exists(f):
                file_name = os.path.split(f)[1][:-4] 
                if p[1] < t:
                    malignancy[int(fs.index[fs["file_name"]==file_name].values)] = "Non-malignancy"
                elif p[1] > (1 - t):
                    malignancy[int(fs.index[fs["file_name"]==file_name].values)] = "Malignancy"

    fs["Malignancy"] = malignancy
    fs.to_excel("excels/I-Data_1.xlsx", sheet_name="Sheet1", index=False)


def train_model(iter=0, t=0.1):
    batch_size = 32
    patience = 50

    data_path = "/data/image/breast/ultrasound/dicom_set/"
    test_data_path = "/data/image/breast/ultrasound/non_label_set/"
    output_path = f"/data/results/semi/semi_{iter}"

    os.environ["CUDA_DEVICE_ORDER"] = "PCI_BUS_ID"
    os.environ["CUDA_VISIBLE_DEVICES"] = "0"

    pl.seed_everything(42)

    transform = transforms.Compose([
        transforms.Resize((224,224)),
        transforms.ToTensor(), 
    ])

    dataset = ImageFolder(data_path, transform=transform)
    train, val = random_split(dataset, [round(len(dataset)*(3/4)), round(len(dataset)*(1/4))])

    test = ImageFolderWithPaths(test_data_path, transform=transform)

    train_loader = DataLoader(train, batch_size=batch_size, shuffle=True, num_workers=os.cpu_count(), pin_memory=True)
    val_loader = DataLoader(val, batch_size=batch_size, shuffle=False, num_workers=os.cpu_count(), pin_memory=True)
    test_loader = DataLoader(test, batch_size=batch_size, shuffle=False, num_workers=os.cpu_count(), pin_memory=True)

    checkpoint_callback = pl.callbacks.ModelCheckpoint(dirpath=output_path, filename="checkpoint", monitor="val_acc", mode="max")
    early_stopping = pl.callbacks.EarlyStopping(monitor="val_acc", mode="max", patience=patience, verbose=True)

    model = Model().load_from_checkpoint(f"/data/results/semi/semi_{iter-1}/checkpoint.ckpt")
    
    trainer = pl.Trainer(max_epochs=3, callbacks=[checkpoint_callback, early_stopping], default_root_dir=output_path, gpus=torch.cuda.device_count(), log_every_n_steps=1)
    trainer.fit(model, train_loader, val_loader)
    output = trainer.predict(dataloaders=test_loader, ckpt_path="best")

    fs =  pd.read_excel(f"excels/I-Data_{iter}.xlsx", sheet_name="Sheet1")
    fs.fillna("", inplace=True)

    malignancy = fs["Malignancy"].values.tolist()
    for out in output:
        prob = out["prob"]
        file = out["file"]

        for p, f in zip(prob, file):
            if os.path.exists(f):
                file_name = os.path.split(f)[1][:-4] 
                if p[1] < t:
                    malignancy[int(fs.index[fs["file_name"]==file_name].values)] = "Non-malignancy"
                elif p[1] > (1 - t):
                    malignancy[int(fs.index[fs["file_name"]==file_name].values)] = "Malignancy"

    fs["Malignancy"] = malignancy
    fs.to_excel(f"excels/I-Data_{iter+1}.xlsx", sheet_name="Sheet1", index=False)


def make_dataset(iter=0):
    data_path = "/data/image/breast/ultrasound/png_set/"
    train_data_path =  "/data/image/breast/ultrasound/dicom_set/"
    test_data_path =  "/data/image/breast/ultrasound/non_label_set/"

    shutil.rmtree(train_data_path)
    shutil.rmtree(test_data_path)

    os.mkdir(train_data_path)
    os.mkdir(f"{train_data_path}/label_0")
    os.mkdir(f"{train_data_path}/label_1")

    os.mkdir(test_data_path)
    os.mkdir(f"{test_data_path}/label_0")

    fs = pd.read_excel(f"excels/I-Data_{iter}.xlsx", sheet_name="Sheet1")
    fs.fillna("", inplace=True)

    for f in fs.loc[fs["Malignancy"]==""]["file_name"].values.tolist():
        if os.path.exists(f"{data_path}/{f}.png"):
            shutil.copy(f"{data_path}/{f}.png", f"{test_data_path}/label_0/{f}.png")

    ml = fs.loc[fs["Malignancy"]=="Malignancy"]["file_name"].values.tolist()
    for f in ml:
        if os.path.exists(f"{data_path}/{f}.png"):
            shutil.copy(f"{data_path}/{f}.png", f"{train_data_path}/label_1/{f}.png")

    nl = fs.loc[fs["Malignancy"]=="Non-malignancy"]["file_name"].values.tolist()
    snl = random.sample(nl, k=len(ml))
    for f in snl:
        if os.path.exists(f"{data_path}/{f}.png"):
            shutil.copy(f"{data_path}/{f}.png", f"{train_data_path}/label_0/{f}.png")

if __name__ == "__main__":
    t = 0.1
    make_dataset(0)
    train_base_model(t)

    i = 1
    null = 0
    pre_null = 0
    while True:
        make_dataset(i)
        train_model(i, t)
        i += 1

        fs =  pd.read_excel(f"excels/I-Data_{i}.xlsx", sheet_name="Sheet1")
        fs.fillna("", inplace=True)

        pre_null = null
        null = fs["Malignancy"].isnull().sum()

        print(f"{null} Instances left with t = {t}.")
        if i == 3:
            break
        # if null < 10000:
        #     break
        # if null == pre_null:
        #     t += 0.1