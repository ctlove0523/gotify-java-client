package io.github.ctlove0523.gotify;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.github.ctlove0523.gotify.app.Application;
import io.github.ctlove0523.gotify.app.CreateApplicationRequest;
import io.github.ctlove0523.gotify.app.UpdateApplicationRequest;
import lombok.SneakyThrows;
import okhttp3.HttpUrl;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.jetbrains.annotations.NotNull;

public class ApplicationMockServer {

	private MockWebServer server;

	private List<Application> applications = new ArrayList<>();

	public void start() throws Exception {
		this.server = new MockWebServer();
		server.setDispatcher(new Dispatcher() {
			@SneakyThrows
			@NotNull
			@Override
			public MockResponse dispatch(@NotNull RecordedRequest request) throws InterruptedException {
				String method = request.getMethod();
				String path = request.getPath();
				Objects.requireNonNull(method, "method");
				Objects.requireNonNull(path, "path");

				MockResponse response = new MockResponse();
				if (method.equals("GET") && path.equals("/application")) {

					response.setBody(JacksonUtil.list2String(applications));
					return response;
				}

				if ((method.equals("POST") && path.equals("/application"))) {
					String content = request.getBody().readString(Charset.defaultCharset());

					CreateApplicationRequest car =JacksonUtil.string2Object(content, CreateApplicationRequest.class);

					Application application = new Application();
					application.setInternal(car.getInternal());
					application.setName(car.getName());
					application.setDescription(car.getDescription());
					application.setId(car.getId());
					application.setImage(car.getImage());
					application.setToken("token");

					applications.add(application);
					response.setBody(JacksonUtil.object2String(application));
					return response;
				}

				if (method.equals("PUT") && path.contains("/application")) {
					String content = request.getBody().readString(Charset.defaultCharset());
					UpdateApplicationRequest updateApplicationRequest = JacksonUtil
							.string2Object(content, UpdateApplicationRequest.class);

					Objects.requireNonNull(updateApplicationRequest, "updateApplicationRequest");

					HttpUrl httpUrl = request.getRequestUrl();
					Objects.requireNonNull(httpUrl, "htpUrl");
					int id = Integer.parseInt(httpUrl.pathSegments().get(1));
					for (Application application : applications) {
						if (id == application.getId()) {
							application.setName(updateApplicationRequest.getName());
							application.setDescription(updateApplicationRequest.getDescription());

							response.setBody(JacksonUtil.object2String(application));
							return response;
						}
					}
				}

				if (method.equals("DELETE") && path.contains("/application")) {
					HttpUrl httpUrl = request.getRequestUrl();
					Objects.requireNonNull(httpUrl, "htpUrl");
					int id = Integer.parseInt(httpUrl.pathSegments().get(1));
					for (Application application : applications) {
						if (id == application.getId()) {
							response.setResponseCode(200);
							return response;
						}
					}

					GotifyResponseError error = new GotifyResponseError();
					error.setErrorDescription("application not found");
					error.setErrorCode(404);
					error.setError("NOT FOUND");
					response.setResponseCode(404);
					response.setBody(JacksonUtil.object2String(error));
					return response;
				}

				if (method.equals("POST") && path.contains("/image")) {
					HttpUrl httpUrl = request.getRequestUrl();
					Objects.requireNonNull(httpUrl, "htpUrl");
					int id = Integer.parseInt(httpUrl.pathSegments().get(1));
					for (Application application : applications) {
						if (id == application.getId()) {
							application.setImage(request.getBody().readString(StandardCharsets.UTF_8));
							response.setBody(JacksonUtil.object2String(application));
							return response;
						}
					}

					GotifyResponseError error = new GotifyResponseError();
					error.setErrorDescription("application not found");
					error.setErrorCode(404);
					error.setError("NOT FOUND");
					response.setResponseCode(404);
					response.setBody(JacksonUtil.object2String(error));
					return response;
				}
				return null;
			}
		});

		server.start();
	}

	public void stop() throws Exception {
		if (Objects.nonNull(server)) {
			server.shutdown();
			applications.clear();
		}
	}

	public void addApplication(Application application) {
		applications.add(application);
	}

	public int port() {
		return server.getPort();
	}
}
