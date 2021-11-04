from django.db import models
from djongo import models as djmodels
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
class Common(models.Model):
    age=models.SmallIntegerField()
    modality=models.CharField(max_length=3)
    manufacturer=models.CharField(max_length=200)
    manufacturerModelName =models.CharField(max_length=200)
    class Meta:
        abstract=True

class US(models.Model):
    anonymized_id = models.IntegerField(primary_key=True)
    #common=djmodels.EmbeddedField(model_container=Common)
    age = models.SmallIntegerField()
    modality = models.CharField(max_length=3)
    manufacturer = models.CharField(max_length=200)
    manufacturerModelName = models.CharField(max_length=200)

    benign_0_malignant_1=models.SmallIntegerField()

class MR(models.Model):
    anonymized_id = models.IntegerField(primary_key=True)
    #common=djmodels.EmbeddedField(model_container=Common)
    age = models.SmallIntegerField()
    modality = models.CharField(max_length=3)
    manufacturer = models.CharField(max_length=200)
    manufacturerModelName = models.CharField(max_length=200)

    non_pCR_0_pCR_1=models.SmallIntegerField()
    left_0_right_1=models.SmallIntegerField()
    ER=models.SmallIntegerField()
    PR=models.SmallIntegerField()
    HER2=models.SmallIntegerField()
    non_IDC_0_IDC_1=models.SmallIntegerField()

class MG(models.Model):
    anonymized_id = models.IntegerField(primary_key=True)
    #common=djmodels.EmbeddedField(model_container=Common)
    age = models.SmallIntegerField()
    modality = models.CharField(max_length=3)
    manufacturer = models.CharField(max_length=200)
    manufacturerModelName = models.CharField(max_length=200)

    non_pCR_0_pCR_1 = models.SmallIntegerField()
    left_0_right_1 = models.SmallIntegerField()
    ER = models.SmallIntegerField()
    PR = models.SmallIntegerField()
    HER2 = models.SmallIntegerField()
    non_IDC_0_IDC_1 = models.SmallIntegerField()
    compressionForce=models.FloatField()