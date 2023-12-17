import requests

from django.conf import settings
from django.utils import timezone


def generate_graph(total_users, active_users):
    import matplotlib
    matplotlib.use('Agg')  # Use the Agg backend, which does not require a GUI
    import matplotlib.pyplot as plt
    from io import BytesIO
    import base64

    # Your logic to generate the graph using matplotlib
    plt.bar(['Total Users', 'Active Users'], [total_users, active_users])
    plt.xlabel('User Type')
    plt.ylabel('Number of Users')
    plt.title('User Statistics')

    # Save the plot to a BytesIO buffer
    buffer = BytesIO()
    plt.savefig(buffer, format='png')
    buffer.seek(0)

    # Encode the image as base64
    image_base64 = base64.b64encode(buffer.getvalue()).decode()

    return image_base64


class ManagementMiddleware:
    def __init__(self, get_response):
        self.get_response = get_response
        self.start_time = None
        self.website = {
            'url': 'http://127.0.0.1:8000',
            'debug': settings.DEBUG,
            'response_time': None,
        }
        self.management_info = {
            'url': 'http://localhost:8080/management/info',
            'content': None,
        }
        self.graph_image = None

    def __call__(self, request):
        response = self.get_response(request)
        return response

    def process_view(self, request, view_func, view_args, view_kwargs):
        self.start_time = timezone.now()

    def process_template_response(self, request, response):
        try:
            management_info_response = requests.get(self.management_info['url'])
            management_info_response.raise_for_status()
            self.management_info['content'] = management_info_response.json()

            self.graph_image = generate_graph(
                self.management_info['content']['app-user.stats']['count'],
                self.management_info['content']['app-user.stats']['recentlyActive'])
        except requests.exceptions.RequestException as e:
            print(f"Error fetching management info: {e}")

        if settings.DEBUG:
            response.context_data['website'] = self.website
            response.context_data['website']['response_time'] = timezone.now() - self.start_time
            response.context_data['management_info'] = self.management_info
            response.context_data['graph_image'] = self.graph_image

        return response
