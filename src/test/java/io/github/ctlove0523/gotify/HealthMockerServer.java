package io.github.ctlove0523.gotify;

import java.util.Objects;

import io.github.ctlove0523.gotify.health.Health;
import lombok.SneakyThrows;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.jetbrains.annotations.NotNull;

public class HealthMockerServer implements MockerServer {
	private MockWebServer server;

	public void stop() throws Exception {
		if (Objects.nonNull(server)) {
			server.shutdown();
		}
	}

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

				// 查询应用下的消息
				if (method.equals("GET") && path.contains("/health")) {
					Health health = new Health();
					health.setHealth("active");
					health.setDatabase("mysql");

					response.setBody(JacksonUtil.object2String(health));

					return response;
				}
				return response;
			}
		});

		server.start();
	}

	@Override
	public int port() {
		return server.getPort();
	}
}
