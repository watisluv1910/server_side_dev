from django.template.response import TemplateResponse


def index(request):
    return TemplateResponse(request, "index.html", {
        'title': 'The Sun News Backend Dashboard'
    })
