# DicomServer
## Usage
1. 프로젝트 폴더명은 반드시 *DicomServer*로 지정되어 있어야 합니다.
2. 프로젝트 폴더에서 ```docker-compose up```커맨드를 입력하면 컨테이너가 빌드, 실행되고 메타데이터가 mongoDB에 자동으로 업로드 됩니다.(Docker Compose version v2.0.0)
![image](https://user-images.githubusercontent.com/30094719/140272159-8a1808a3-8b9c-4312-b5bc-e8385f34688a.png)
로그가 위와 같이 출력된다면 DB와 서버가 정상적으로 실행된 상태입니다.

**(주의. ```docker-compose up```을 통해서 빌드할때마다 mongoDB에 중첩된 데이터가 추가될 수 있습니다**)

4. MongoDB Compass를 실행시킵니다.
5. connection string으로 아래 URI를 입력합니다
```
mongodb://knuipalab:knuipalab418@localhost:27017/?authSource=admin&readPreference=primary&appname=MongoDB%20Compass&directConnection=true&ssl=false
```
<p align="center">
  <img src=https://user-images.githubusercontent.com/30094719/140272060-8415f8fd-f1f4-478a-aeef-639832e99b62.png width="80%"/>
</p>
6. Dicom/metadata에서 입력된 정보와 시각화 자료를 볼 수 있습니다.
<p align="center">
  <img src=https://user-images.githubusercontent.com/30094719/140272563-9ec5a4cc-7ba7-42e6-b9f2-5aa9a5a62567.png width="80%"/>
  <img src=https://user-images.githubusercontent.com/30094719/140272591-76846f8a-b6c5-4898-a7e2-2a6c20cb20ce.png width="80%"/>
</p>

## TODO
- [x] 1. set Docker-compose
  - [x] 1-1. init Django - init complete
  - [x] 1-2. init MongoDB - init complete
  - [x] 1-3. Upload Sample Dicom file and metaData to MongoDB
  - [x] 1-4. connect Django and MongoDB
- [x] 2. Make RESTful api
  - [x] 3-1. GET: Downlaod mongoDB info
  - [ ] 3-2. PUT: Upload New Dicom file
  - [ ] 3-3. GET<:id>: Downlaod Dicom file
