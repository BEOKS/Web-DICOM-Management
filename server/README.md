# DicomServer
##  Usage
### Before
1. 최신 버전의 [도커](https://docs.docker.com/get-docker/)를 설치해주세요.
2. JDK 11 이상의 버전을 설치해주세요
### MAC
```
sh server.sh up
```
### Window
```
./server.sh up
```
### 실행결과
![image](https://user-images.githubusercontent.com/30094719/143244120-658e2e3a-feb3-4728-8c43-90ef564a7f56.png)

위와 같은 출력이 나오며 http://localhost:8080/ 에 정상적으로 접속 가능하다면 성공입니다. 에러가 발생한다면 이슈를 발행해주세요!
## MongoDB 시각화

1. MongoDB Compass를 실행시킵니다.
2. connection string으로 아래 URI를 입력합니다(서버에 MongoDB 컨테이너를 실행했을 경우 localhost대신 서버의 IP를 입력합니다.)
```
mongodb://knuipalab:knuipalab418@localhost:27017/?authSource=admin&readPreference=primary&appname=MongoDB%20Compass&directConnection=true&ssl=false
```
<p align="center">
  <img src=https://user-images.githubusercontent.com/30094719/140272060-8415f8fd-f1f4-478a-aeef-639832e99b62.png width="80%"/>
</p>
4. Dicom/metadata에서 입력된 정보와 시각화 자료를 볼 수 있습니다.
<p align="center">
  <img src=https://user-images.githubusercontent.com/30094719/140272563-9ec5a4cc-7ba7-42e6-b9f2-5aa9a5a62567.png width="80%"/>
  <img src=https://user-images.githubusercontent.com/30094719/140272591-76846f8a-b6c5-4898-a7e2-2a6c20cb20ce.png width="80%"/>
</p>

## 1.2 TODO
- [x] 1. set Docker-compose
  - [x] 1-1. init Django - init complete
  - [x] 1-2. init MongoDB - init complete
  - [x] 1-3. Upload Sample Dicom file and metaData to MongoDB
  - [x] 1-4. connect Django and MongoDB
- [x] 2. Make RESTful api
  - [x] 3-1. GET: Downlaod mongoDB info
  - [ ] 3-2. PUT: Upload New Dicom file
  - [ ] 3-3. GET<:id>: Downlaod Dicom file
