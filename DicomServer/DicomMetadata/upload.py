from pymongo import MongoClient
import pandas as pd
import json
from glob import glob

def mongoimport(csv_path, db_name, coll_name, db_url,type):
    client = MongoClient(db_url)
    db = client[db_name]
    coll = db[coll_name]
    data = pd.read_csv(csv_path)
    data['type']=type
    payload = json.loads(data.to_json(orient='records'))
    coll.insert(payload)
    return coll.count()

if __name__=='__main__':
    mongodb_URI="mongodb://knuipalab:knuipalab418@dicomserver-mongodb-1:27017/?authSource=admin&readPreference=primary&appname=MongoDB%20Compass&directConnection=true&ssl=false"
    for csv_path in glob('/app/DicomMetadata/*.csv'):
        type=csv_path.split('_')[1]
        print(f'Start upload {csv_path}')
        collCount=mongoimport(csv_path,
        db_name="Dicom",
        coll_name='metadata',
        db_url=mongodb_URI,
        type=type)
        print(f'Update : {csv_path}, collection count : {collCount}')
    
