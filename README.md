[![Deployment](https://github.com/BEOKS/DicomProject/actions/workflows/deployment.dev.yml/badge.svg?branch=BEOKS-patch-1)](https://github.com/BEOKS/DicomProject/actions/workflows/deployment.dev.yml)

[English.doc](./README.EN.md)
# Dicom Service Management Project
DSMP(Dicom Service Management Project) 는 [DICOM](https://www.dicomstandard.org/)(Digital Imaging and Communications in Medicine) 데이터를 웹 기반 플랫폼으로 편리하게 다루기 위한 오픈소스 프로젝트입니다.
# Feature
## 1. 익명화

연구자나 개발자가 DICOM 데이터를 연구 목적으로 다루기 위해서는 DICOM 메타데이터 중 환자의 개인정보([PHI](https://www.hhs.gov/answers/hipaa/what-is-phi/index.html)) 부분을 익명화 해야합니다. 현재 대부분의 익명화 알고리즘은 단순히 새로운 값을 생성하거나 값을 지우는 방식으로 진행되고 있습니다. 하지만 이런 방법으론 데이터의 연관성이 사라지기 때문에 연구에 어려움이 있습니다. (예를 들어, 같은 환자의 CT DICOM 파일이 익명화 후 다른 환자 ID를 가지게 될 수 있습니다.) 이 프로젝트에선 Patient, Study, Series, Image ID 등 연구를 위해 연관성이 유지되어야하는 데이터에 단방향 해싱 ([Bcrypt](https://www.npmjs.com/package/bcrypt)) 알고리즘을 사용하여 익명성을 보장하며 연관성을 유지하는 익명화 기법을 지원하고 있습니다. 또한, 익명화 과정은 데이터가 서버에 전송되기 전 브라우저에서 진행되므로 익명화되지 않은 데이터가 네트워크에 유포될 가능성을 차단합니다.

## 2. Metadata Relation

의료 연구를 진행하기 위해선 DICOM 이미지 뿐만 아니라 이와 관련된 메타 데이터(양/악성, 병변의 크기 등)가 필요합니다. CSV 형식으로 메타데이터를 업로드 할 경우, 프로젝트는 메타데이터의 ID 부분을 읽어 기존에 저장된 DICOM 데이터와 연결하여 아래와 같이 데이터를 확인 할 수 있습니다.

![image](https://user-images.githubusercontent.com/30094719/196581697-769ff686-09ac-41ee-9917-c43a0da5b590.png)

## 3. More Image Format
우리는 의료 연구를 계속 진행하며, DICOM 파일 형식 뿐만 아니라 JPEG, PNG 등 가공된 데이터 형식이 필요하다는 것을 알게되었습니다. 따라서, DICOM과 JPEG, PNG 파일 형식 업로드를 모두 지원하도록 업데이트 했습니다. JPEG, PNG 파일 형식으로 업로드를 진행할 경우 익명화 과정은 진행되지 않으며, 파일이름을 특정 ID로 명명해야합니다. 이 ID 는 추후 업로드할 메타데이터의 ID 부분과 연관됩니다.

![image](https://user-images.githubusercontent.com/30094719/196581753-2588cdec-5d8a-45bc-8ae0-023373b13228.png)

## 4. Project, User Management
DSMP는 여러 개의 프로젝트를 생성하고 각 프로젝트마다 이미지와 메타데이터를 저장할 수 있습니다. 보안을 위해 각 프로젝트는 초대받은 사람만 참여가 가능하며, 프로젝트 생성자가 참여자를 관리 할 수 있습니다.


![image](https://user-images.githubusercontent.com/30094719/196581787-9049e1ad-bcf8-4267-bf11-5a3f66e53295.png)

## 5. Data Visualization
연구를 위해선 업로드 된 데이터가 올바른지, 분포는 어떤 지 등 데이터를 한눈에 파악하기 위해서 데이터 시각화가 필요합니다. DSMP는 메타데이터를 분석해 숫자형 데이터의 경우 히스토그램을, 카테고리형 데이터의 경우 파이차트를 시각화하는 기능을 제공합니다. 
<img width="1769" alt="image" src="https://user-images.githubusercontent.com/30094719/175278688-2c556b23-5b55-426d-80bd-7e980c88142d.png">

## 6. Machine Learning Result Visualization
DSMP는 의료 데이터베이스를 구축하기 위한 플랫폼이지만, 우리는 의료 데이터베이스 기반 연구가 머신러닝과 밀접하게 연관이 있다는 것을 알게 되었고 이를 지원하기 위해서 [Torchserve](https://pytorch.org/serve/) 결과를 저장할 수 있는 기능을 추가했습니다. 별도의 Torchserve 서버를 실행시키고 이를 DSMP 프로젝트와 연동하면, 아래와 같이 추론 모델을 선택해 추론을 요청하고 결과를 데이터베이스에 저장합니다. 자세한 구현을 위해선 [여기](./server/TorchServe/README.md)를 참고해주세요.
![image](https://user-images.githubusercontent.com/30094719/196581888-dd1face8-6892-4923-a72c-52e7171071b8.png)
![image](https://user-images.githubusercontent.com/30094719/196581975-626287ca-7689-4a6f-8279-49b006ad8a11.png)

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

# Used Framework & Language
<img src="https://img.shields.io/badge/Docker-2496ED?&logo=Docker&logoColor=white">
도커는 설치 시 반복되는 프로비저닝과 설정을 방지할 수 있습니다. 도커를 사용함으로써 Local, Dev, Prod 환경에서 동일한 동작을 보증할 수 있기 때문에 이 프로젝트는 도커를 기반으로 설치, 실행됩니다. 멀티 컨테이너를 사용하기 때문에 Docker-compose를 이용하고 있습니다.

 <img src="https://img.shields.io/badge/React-61DAFB?&logo=React&logoColor=white"> <img src="https://img.shields.io/badge/TypeScript-3178C6?&logo=TypeScript&logoColor=white"> <img src="https://img.shields.io/badge/JavaScript-F7DF1E?&logo=JavaScript&logoColor=white"> <img src="https://img.shields.io/badge/Redux-764ABC?&logo=Redux&logoColor=white"> 
 
 React 프레임워크는 재사용성이 높은 컴포넌트를 생성하기 용이하고 Typescript는 타입을 명시함으로써 데이터 구조 차이로 인한 버그 발생을 억제할 수 있기 때문에 도입했습니다. React Props는 프로젝트가 커질 수록 중복증가와 불필요할 컴포넌트 랜더링이 많아져 이를 해결하기 위해 Redux를 도입했습니다.

 <img src="https://img.shields.io/badge/OpenJDK-2496ED?&logo=OpenJDK&logoColor=white"> <img src="https://img.shields.io/badge/Spring Boot-6DB33F?&logo=Spring Boot&logoColor=white"> <img src="https://img.shields.io/badge/Spring Security-6DB33F?&logo=Spring Security&logoColor=white">  <img src="https://img.shields.io/badge/JUnit5-25A162?&logo=JUnit5&logoColor=white"> <img src="https://img.shields.io/badge/MongoDB-47A248?&logo=MongoDB&logoColor=white"> 

 As java running by JVM which guarantee stable software running environment like Auto Optimization and GC, We select java for server system. We use Spring Framework For effective and safe develop in Java development environment. And for testing, we use Junit5 ans Mockito.

 # Architecture
 Basically, We use Monolithic Architecture, because we now aim Fast Implement-Fast Feedback cycle. MSA(Microservice Architecture) is good for scale out, independent development and maintenance. But, it require many management like monitoring, configuration for each MSA component and Troubleshooting etc. If we make feature stable and need to handle scaling out per feature, We will migrate to MSA.
 
 For that, We use SoC(Separation of Concern) design structure. Simply, all code files for same feature need to store in same project of directory. So we hope to migrate to MSA relatively easily.
 
# Author
[Jaeseong Lee](https://github.com/BEOKS), lee01042000@gmail.com
