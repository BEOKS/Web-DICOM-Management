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
Data in DICOM format is not always used to build a medical image database. So DSMP also supports uploading images in PNG/JPEG format. In this case, the image anonymization function is not executed, and the image file name must be entered instead of the patient ID in the metadata.

## 4. Project, User Management
The subject of medical AI research can be many, and data suitable for each subject is required. DSMP provides a function to manage data on a project-by-project basis, and each project manager can invite other researchers to use the data together. This can improve security by ensuring that only the necessary researcher can access each projects.

## 5. Data Visualization
Of course, DSMP can view the uploaded metadata list in table form, and in the case of medical images, you can visualize it by clicking on each table row. However, we do offer better features to increase researcher's intuition about their data.

The data you use for a project can be very large. The data manager must check whether the data has been uploaded correctly or not. DSMP automatically provides data visualization based on uploaded metadata. By selecting the desired column in the metadata, you can quickly understand the distribution of each data. If the data is in the form of a category, a pie chart is provided, and if the data is in the form of a number, a histogram is provided like below
<img width="1769" alt="image" src="https://user-images.githubusercontent.com/30094719/175278688-2c556b23-5b55-426d-80bd-7e980c88142d.png">

## 6. Machine Learning Result Visualization
DSMP is basically a platform for building databases, but we felt the need for the ability to visualize machine learning inference results in the beta test stage. You can use this feature by building a machine learning server using [Torchserve](https://pytorch.org/serve/). Users can choose which model to use to infer the currently uploaded data. When inference is started with the selected model, the backend automatically delivers the medical image of the project to the machine learning server and saves the result back to the database. Results in the form of strings and numbers are updated in metadata, and image result is added to the image database.

## Usage
### Prerequirement
1. 최신 버전의 도커를 설치해주세요. (docker-compose는 v2 버전으로 설치되어야 합니다, 리눅스의 경우 간혹 v1으로 설치되므로 업데이트가 필요합니다.)
2. java SDK (version. 17.0.1)을 설치합니다. (설치하지 않을 경우 spring 프로젝트 파일이 정상적으로 실행되지 않습니다.)
3. Node.js 가 설치되어 있지 않다면 v16.6.1버전으로 설치해야 합니다.
4. 최신 버전의 [yarn](https://classic.yarnpkg.com/lang/en/docs/install/#windows-stable)을 설치해주세요.

### Install Project
설치의 경우 프로젝트를 다운로드 한 후, 한번만 실행하면 됩니다. 프로젝트 코드가 갱신될 경우, 코드를 반영하기 위해서 다시 실행해야 합니다.
#### Common
1. 코드 다운로드
```sh 
#clone all project with submodules
git clone --recurse-submodules https://github.com/BEOKS/DicomProject.git
cd DicomProject
```
2. 현재 프로젝트는 구글 로그인을 사용하고 있으므로 구글 API clientId와 secrect id가 필요합니다. 이를 발급받아 [application-oauth.yml.example](https://github.com/BEOKS/DicomProject/blob/main/server/DSMP/src/main/resources/application-oauth.yml.example)과 같은 형식을 작성하여 같은 파일 위치에 application-oauth.yml 파일을 생성해야 합니다.
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
> 실행 직후, 클라이언트 서버가 먼저 실행되는 경우 localhost:3000, localhost:3001 페이지에서 에러가 발생할 수 있습니다. 도커 서버가 모두 실행완료 된 후, 새로고침을 해주세요

# Author
[Jaeseong Lee](https://github.com/BEOKS), lee01042000@gmail.com
