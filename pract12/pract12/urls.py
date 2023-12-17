from django.contrib import admin
from django.urls import path

from management.views import index

urlpatterns = [
    path('', index, name='index'),
    path('management/', index, name='management'),
    path('admin/', admin.site.urls),
]
