#!bin/bash
# 1. run dicom viewer client
cd Viewers
yarn run dev:$1&
# 2. run client
cd ../client
yarn start:$1&
# 3. run server
cd ../server
sh run_docker.sh $1
