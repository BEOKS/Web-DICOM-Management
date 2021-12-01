#!bin/bash
# All server controlled with docker contianer. 
# In spring, Before starting docker, you have to update dependecy like below
# 1. Build file without testing
cd DSMP
rm -rf build/dependency
chmod +x ./gradlew
./gradlew build -x test
# 2. make dependency dir
mkdir -p build/dependency
cd build/dependency
# 3. update dependency dir 
jar_path=`ls ../libs/*-SNAPSHOT.jar 2> /dev/null`
jar xf ${jar_path} 
# 4. Run docker
cd ..
docker-compose build --no-cache
docker-compose up
