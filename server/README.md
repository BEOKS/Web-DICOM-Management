# DicomServer
##  Usage
### MAC
```
sh server.sh up
```
### Window
```
./server.sh up
```
### 실행결과

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
