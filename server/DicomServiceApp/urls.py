from django.urls import path
from .views import DicomServiceApp

urlpatterns=[
    path('metadata',DicomServiceApp.as_view({
        'get':'list',
        'post':'create'
    })),
    path('metadata/<str:id>', DicomServiceApp.as_view({
        'get':'retrieve',
        'put':'update',
        'delete': 'destroy'
    })),
]