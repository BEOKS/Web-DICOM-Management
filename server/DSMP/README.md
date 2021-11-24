## 스프링 코드를 업데이트 하여 도커 이미지를 빌드 및 컨테이너를 실행하는 방법
1. ```.\gradlew build```를 통해서 jar로 스프링 코드를 빌드한다.
2. build/dependency 디렉토리를 생성한다 (이미 존재하는 경우 생략)
```
mkdir -p build/dependency 
```
3. 빌드된 스프링 jar 파일에서 빌드된 정보를 build/dependency로 추출한다.(버전이 변경되었다면 실행하고자 하는 버전에 맞추어 jar 경로를 설정한다.)
```
cd build/dependency
jar -xf ../libs/DSMP-0.0.1-SNAPSHOT.jar
```
4. BOOT-INF,META-INF 디렉토리가 생성되면 프로젝트 루트(DSMP)에서 Dockerfile을 빌드한다.
```
docker build -t springio/dsmp-docker:1.0 .
```
5. 포트 번호를 부여하여 빌드한 이미지를 컨테이너로 실행한다.
```
docker run -p 8080:8080 springio/dsmp-docker:1.0
```
> 이렇게 복잡하게 도커 이미지를 빌드하는 이유는 아래와 같다.
> 
>  there is a clean separation between dependencies and application resources in a Spring Boot fat JAR file, and we can use that fact to improve performance. The key is to create layers in the container filesystem. The layers are cached both at build time and at runtime (in most runtimes), so we want the most frequently changing resources (usually the class and static resources in the application itself) to be layered after the more slowly changing resources. Thus, we use a slightly different implementation of the Dockerfile
> 
> FROM : https://spring.io/guides/gs/spring-boot-docker/#:~:text=there%20is%20a,of%20the%20Dockerfile%3A