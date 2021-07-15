package io.github.ctlove0523.gotify;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ctlove0523.gotify.app.Application;
import io.github.ctlove0523.gotify.app.CreateApplicationRequest;
import lombok.SneakyThrows;
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

					System.out.println(content);

					ObjectMapper mapper = new ObjectMapper();


					CreateApplicationRequest car = mapper.readValue(content, CreateApplicationRequest.class);

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
