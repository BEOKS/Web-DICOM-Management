# Dicom Service Management Project
![example workflow](https://github.com/BEOKS/DicomProject/actions/workflows/deployment.dev.yml/badge.svg)
## :dart: Objective
Dicom 의료 영상 데이터를 저장, 관리하며 시각화 기능과 머신러닝 개발을 지원하는 SW개발
## :bar_chart: Success metrics
의료데이터 관리 및 시각화 지원 기능을 구현하여 v1.0 배포를 목표로 한다.(option 제외)
## 📓 Requirements
### 1. 의료 데이터 관리
Requirement | User Story | Notes | Priority
------------- | ------------- | ------------- | -------------
Dicom 업로드, 다운로드 클라이언트 구현 | 사용자는 웹 UI를 통해서 Dicom데이터 업로드, 다운로드를 요청 할 수 있다.| React를 이용하여 UI를 구성하고 REST API형식으로 미들웨서 서버에 요청하도록하여 구현이 가능하다| 1
Dicom 리스트 뷰 기능 | 사용자는 저장되어 있는 Dicom 리스트를 웹 UI를 통해서 확인 할 수 있다. | React를 이용하여 UI를 구성하고 REST API형식으로 미들웨서 서버에 요청하도록하여 구현이 가능하다. | 2
프로젝트별 데이터 관리 | 사용자는 웹 UI를 통해서 프로젝트별로 데이터를 그룹화 할 수 있으며 접근권한을 설정 할 수 있다. | MongoDB에 사용자 계정 정보와  Dicom 메타데이터를 연결함으로써 설정이 가능하다. | 3
영상 데이터 업로드, 다운로드 클라이언트 구현 | 사용자는 웹 UI를 통해서 영상 데이터 업로드, 다운로드를 요청 할 수 있다. | 영상데이터를 입력받아 이미지 슬라이싱 후 Dicom형태로 변환하여 구현이 가능하다.(가정) | 4(>v1.0)
### 2. 의료 데이터 시각화 지원
Requirement | User Story | Notes | Priority
------------- | ------------- | ------------- | -------------
Dicom 파일 선택 기능 | 사용자는 웹 UI에 있는 Dicom 리스트 뷰에서 한 행을 클릭하여 해당 Dicom 파일을 시각화 하여 볼 수 있다. | 각 리스트에 OHIF 뷰어를 호출하는 링크를 설정함으로써 구현이 가능하다. | 1
줌 인/아웃, 드래그 등 기분 툴 구현 | 사용자는 웹 UI를 통해서 이미지를 다루는 툴을 사용할 수 있다. | OHIF 오픈소스를 통해서 구현이 가능하다. | 2
Segmentation 뷰어 기능 구현 | 사용자는 웹 UI를 통해서 Dicom에 Segmentation이 적용된 이미지를 확인 할 수 있다. | Dicom SEG 파일을 orthan서버에 업로드 함으로써 구현이 가능하다. | 3
Annotation(ROI) 작성 기능 구현 | 사용자는 웹 UI를 통해서 이미지 위에 Annotation을 작성할 수 있으며 서버에 저장이 가능하다. | OHIF에는 기능이 구현되어 있지 않아 추후 기능 구현이 필요하다. 작업 난이도 또한 상당히 높다. | 4(>v1.0)
## 🏗️Architecture
![image](https://user-images.githubusercontent.com/30094719/143526030-73eac6ec-b4b5-41ed-8805-9d1b4ab9393c.png)
1. Base Client : 계정관리, Dicom데이터 업로드, 다운로드 그리고 머신러닝 지원 등 전체적인 사용자 기능을 지원한다.
2. DicomViewer : Base Client에 의해 호출되며 Dicom 파일 시각화를 지원하는  클라이언트이다.
3. MiddleWare : 클라이언트와 데이터베이스, 서버 사이의 요청을 처리한다.
4. Dicom Server :  Dicom 데이터를 저장하며 Dicom Web 표준 프로토콜을 통해 Dicom 데이터를 송수신한다.
5. Database : Dicom 메타데이터 및 계정정보 등 사용자 기능을 지원하기 위한 데이터를 저장한다.
6. TorchServer : Dicom 이미지를 통한 머신러닝 모델을 학습, 추론 기능을 지원한다.
7. docker compose : 각 서버는 도커로 실행되며 docker compose는 이를 통합 관리한다.
8. Jenknins : 지속 개발을 위한 CI/CD를 지원한다. 경우에 따라 다른 프레임워크로 대체될 수 있다.(ex. github actions, teamcity 등)

## 📖 Paper
1. [Wiki](https://alpine-freezer-d6f.notion.site/DSMP-Wiki-0777d45b69124dbbb0e897ec4e7e3279)
2. [개발 명세서](https://alpine-freezer-d6f.notion.site/a15a1f59b5764c7da1c0e3fd655b3bde)
3. [시스템 아키텍쳐](https://alpine-freezer-d6f.notion.site/Project-Architecture-92b2000cefc34208900ff0f2414b9127)
## Usage
1. DicomServer의 README.md를 참조하여 서버를 실행한다.
2. DicomClient/dicom-clinet의 README.md 를 참조하여 클라이언트 서버를 실행한다.
