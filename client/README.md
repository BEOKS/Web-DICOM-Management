# Usage
1. Viewer 서브 모듈을 업데이트 한다.
``` 
git submodule init
git submodule update
```
2. Viewer 디렉토리로 이동한 후 이미지 Viewer 클라이언트를 우선 실행한다.
```
cd Viewer
yarn start
```
3. Viewer 클라이언트가 정상적으로 실행되면 client 디렉토리로 돌아와 client를 실행한다.
```
cd client
yarn start
```