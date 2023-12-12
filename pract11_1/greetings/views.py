from django.shortcuts import render


def hello(request):
    if 'username' in request.GET:
        username = request.GET['username']
    else:
        username = 'Guest'

    return render(request, 'greetings/hello.html', {'username': username})
