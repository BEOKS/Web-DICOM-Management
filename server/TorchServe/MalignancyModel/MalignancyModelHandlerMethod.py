from MalignancyModelHandler import MalignancyModelHandler

_service=MalignancyModelHandler()

def handle(data,context):
    if not _service.initialized:
        _service.initialize(context)
    if data is None:
        return None
    data=_service.preprocess(data)
    data=_service.inference(data)
    print("inference result asdf",data)
    data=_service.postprocess(data)
    return data