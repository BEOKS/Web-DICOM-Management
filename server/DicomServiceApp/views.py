from rest_framework import viewsets
from pymongo import MongoClient,errors as pymongo_errors
from django.http import JsonResponse

class DicomServiceApp(viewsets.ViewSet):
    def __init__(self, **kwargs) -> None:
        super().__init__(**kwargs)
        self.mongodb=MongoClient('mongodb://knuipalab:knuipalab418@server_mongodb_1:27017/?authSource=admin&readPreference=primary&appname=MongoDB%20Compass&directConnection=true&ssl=false')
        try:
            print('connecting to mongodb ...')
            self.mongodb.server_info()
            print('connected!')
            self.collection=self.mongodb.Dicom.metadata
        except pymongo_errors.ServerSelectionTimeoutError as err:
            print(err)

    def list(self, request):
        result=[]
        for document in self.collection.find():
            del document['_id']
            result.append(document)
        return JsonResponse(result,safe=False)

    def create(self, request):
        pass
    def retrieve(self, request):
        pass
    def update(self, request):
        pass
    def destroy(self, request):
        pass