#!/bin/sh
cd DSMP
./gradlew build -x test
java -jar build/libs/gs-spring-boot-docker-0.1.0.jar
mkdir -p build/dependency
cd build/dependency 
echo 'Start update SpringBoot project jar file...'
jar -xf ../libs/DSMP-0.0.1-SNAPSHOT.jar
echo 'DSMP-SpringBoot Project Update Complete'
docker-compose $1 