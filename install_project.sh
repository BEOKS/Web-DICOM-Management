#!bin/bash
# 1. install client
cd client
yarn install
# 2. install dicom viewer
cd ../Viewers
yarn config set workspaces-experimental true 
yarn install