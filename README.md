[![Deployment](https://github.com/BEOKS/DicomProject/actions/workflows/deployment.dev.yml/badge.svg?branch=BEOKS-patch-1)](https://github.com/BEOKS/DicomProject/actions/workflows/deployment.dev.yml)
# Dicom Service Management Project
DSMP(Dicom Service Management Project) aim convinient DICOM(Digital Imaging and Communications in Medicine) database management for clinical ML project based on web.
# Feature
## 1. Anonymization 
When researchers or developer collect DICOM data for medical research, they need to anonymize each patient's personal information in DICOM meatadata and related other data. This process is so tired, because instead of using patient's ID you need to give unique Anonymized ID for each DICOM data from multiple medical institutions. There may be same patient's ID in differenct medical institutions. Therefore, whenever data is obtained, it is necessary to manually check for multiple duplicates ID and perform the anonymization process. 

DSMP automatically performs anonymization and assigns an appropriate ID each time data is uploaded. Anonymization process is performed before upload to database. Therefore, it is possible to ensure the prevention of leakage of personal information and to dramatically reduce the time to collect research data.

## 2. Metadata Relation
Medical AI research requires not only DICOM images but also related metadata. If metadata including patient ID is uploaded in CSV format, it can be combined with DICOM data that has already been uploaded or will be uploaded to make data management easier.

## 3. More Image Format
DICOM format is not always used to build a medical image database. So DSMP also supports uploading images in PNG/JPEG format. In this case, the image anonymization process is not executed, and the image file name must be entered instead of the patient ID in the metadata.

## 4. Project, User Management
The subject of medical AI research can be many, and data for each subject is required. DSMP provides a function to manage data on a project-by-project basis, and each project manager can invite other researchers to use the data together. This can improve security by ensuring that only the necessary researcher can access each projects.

## 5. Data Visualization
Of course, DSMP can view the uploaded metadata list in table form, and in the case of medical images, you can visualize it by clicking on each table row. However, we do offer better features to increase researcher's intuition about their data.

The data you use for a project can be very large. The data manager must check whether the data has been uploaded correctly or not. DSMP automatically provides data visualization based on uploaded metadata. By selecting the desired column in the metadata, you can quickly understand the distribution of each data. If the data is in the form of a category, a pie chart is provided, and if the data is in the form of a number, a histogram is provided like below
<img width="1769" alt="image" src="https://user-images.githubusercontent.com/30094719/175278688-2c556b23-5b55-426d-80bd-7e980c88142d.png">

## 6. Machine Learning Result Visualization
DSMP is basically a platform for building databases, but we felt the need for the ability to visualize machine learning inference results in the beta test stage. You can use this feature by building a machine learning server using [Torchserve](https://pytorch.org/serve/). Users can choose which model to use to infer the currently uploaded data. When inference is started with the selected model, the backend automatically delivers the medical image of the project to the machine learning server and saves the result back to the database. Results in the form of strings and numbers are updated in metadata, and image result is added to the image database.

# Usage
## Prerequirement
1. Docker >=20.10.16
2. OpenJDK>=17.0.1 
3. Node.js>=v16.6.1
4. [yarn](https://classic.yarnpkg.com/lang/en/docs/install/#windows-stable)>=1.22.19

### Install Project
#### Common
1. Download 
```sh 
#clone all project with submodules
git clone --recurse-submodules https://github.com/BEOKS/DicomProject.git
cd DicomProject
```
2. Configuration
DSMP use OAuth2 authentication with Google and Naver, For now, we use Naver as default. You can use other OAuth2 with [Spring Security
](https://docs.spring.io/spring-security/reference/servlet/oauth2/index.html). In spring resources, You can check [oauth-sample](https://github.com/BEOKS/DicomProject/blob/main/server/DSMP/src/main/resources/application-oauth.yml.example) file. Create application-oauth.yml in same directory that contain client-id and client-secret.

#### Window
```sh
.\install_project.sh
```
#### Mac, Linux
```sh
sudo sh install_project.sh
```
### Run Project
#### Window
```sh
run_project.sh local # run project for development, localhost:3000에서 서비스 이용가능
run_project.sh prod # run project for deploy
```
#### Mac, Linux
```sh
sudo sh run_project.sh local # run project for development,localhost:3000에서 서비스 이용가능
sudo sh run_project.sh prod # run project for deploy
```
Client page port is 3001 and server port is 8080

# Author
[Jaeseong Lee](https://github.com/BEOKS), lee01042000@gmail.com
