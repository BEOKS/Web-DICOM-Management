# Usage
> 이 클라이언트는 Viewer 클라이언트를 참조하기 때문에 우선 Viewer클라이언트를 실행해야한다.
1. 클라이언트 시작 전 [server/README.md](../server/README.md)를 참조하여 서버를 실행한다.
2. Viewer 서브 모듈을 업데이트 한다.
``` 
git submodule init
git submodule update
```
3. Viewer 디렉토리로 이동한 후 이미지 Viewer 클라이언트를 우선 실행한다.
```
cd Viewer
yarn start
```
4. Viewer 클라이언트가 정상적으로 실행되면 client 디렉토리로 돌아와 client를 실행한다.
```
cd client
yarn start
```