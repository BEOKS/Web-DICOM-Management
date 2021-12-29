# DicomServer
##  Usage
### Before
1. 최신 버전의 [도커](https://docs.docker.com/get-docker/)를 설치해주세요. (docker-compose는 v2 버전으로 설치되어야 합니다, 리눅스의 경우 간혹 v1으로 설치되므로 업데이트가 필요합니다.)
2. java SDK (version. 17.0.1)을 설치합니다. (설치하지 않을 경우 spring 프로젝트 파일이 정상적으로 실행되지 않습니다.)
### MAC, LINUX
```
#/DSNP/server
sudo sh run_docker.sh local #local에서 실행할 때
sudo sh run_docker.sh prod #서버에서 실행할 때
```
### WINDOWS
```
run_docker.sh local #local에서 실행할 때
run_docker.sh prod #서버에서 실행할 때
```

> ``` run_docker.sh local ```로 커맨드 입력시, orthanc와 MongoDB
> 컨테이너에 포트접속이 활성화 되어 개발자가 로컬에서 접속할 수 있다.
> ``` run_docker.sh prod```로 커맨드 입력시, orthanc와 MongoDB 컨테이너는
> Spring 컨테이너에서만 접속이 가능하다. (즉, API를 통해서만 접근 가능)

### 실행결과
![image](https://user-images.githubusercontent.com/30094719/143684942-ab57c412-0f0c-47ef-9860-7425b580a02a.png)

![image](https://user-images.githubusercontent.com/30094719/143244120-658e2e3a-feb3-4728-8c43-90ef564a7f56.png)

3가지 컨테이너가 실행되고 위와 같은 출력이 나오며 http://localhost:8080/ 에 정상적으로 접속 가능하다면 성공입니다. 에러가 발생한다면 이슈를 발행해주세요!
## MongoDB 시각화
0. ```run_docker.sh local```로 컨테이너를 실행시킵니다.
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
