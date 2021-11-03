from django.db import models

# Create your models here.
# MR
# anonymized_id,age,modality,manufacturer,manufacturerModelName,"class
# non-pCR: 0 pCR: 1","left: 0
# right: 1",ER,PR,HER2,"non-IDC: 0
# IDC: 1"

# MG
# anonymized_id,age,modality,manufacturer,manufacturerModelName,"class
# non-pCR: 0 pCR: 1","left: 0
# right: 1",ER,PR,HER2,"non-IDC: 0
# IDC: 1",compressionForce

# US
# anonymized_id,age,modality,manufacturer,manufacturerModelName,"class
# benign: 0 malignant: 1"
class MR(models.Model):
    anonymized_id=models.AutoField(primary_key=True)
    age=models.IntegerField()
    modality=models.CharField(max_length=3)
    manufacturer=models.CharField(max_length=20)

class MG(MR):
    compressionForce=models.IntegerField()