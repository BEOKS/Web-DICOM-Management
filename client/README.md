# Usage
> 이 클라이언트는 Viewer 클라이언트를 참조하기 때문에 우선 Viewer클라이언트를 실행해야한다.
0. Node.js 가 설치되어 있지 않다면 [최신버전](https://nodejs.org/ko/download/)으로 설치해야 합니다.
1. 클라이언트 시작 전 [server/README.md](../server/README.md)를 참조하여 서버를 실행합니다.
2. Viewer 서브 모듈을 업데이트 합니다.
``` 
git submodule init
git submodule update
```
3. Viewer 디렉토리로 이동한 후 이미지 Viewer 클라이언트를 우선 실행합니다.
```
cd Viewer
yarn config set workspaces-experimental true
yarn install #초기에 한번 실행
yarn run dev:orthanc
```
4. Viewer 클라이언트가 정상적으로 실행되면 client 디렉토리로 돌아와 client를 실행합니다.
```
cd client
yarn install #초기에 한번 실행
yarn start
```
5. http://localhost:3001/ 에 정상적으로 접속된다면 성공입니다.
> 클라이언트 서버 또한 다른 서버와 같이 docker를 이용한 세팅을 설정할 예정입니다.