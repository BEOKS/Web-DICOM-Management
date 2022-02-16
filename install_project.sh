#!bin/bash
# 1. install client
cd client
yarn install
# 2. install dicom viewer
cd ../Viewer
yarn config set workspaces-experimental true 
yarn install