package io.github.ctlove0523.gotify;

import java.util.Objects;

import io.github.ctlove0523.gotify.version.Version;
import lombok.SneakyThrows;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.jetbrains.annotations.NotNull;

public class VersionMockerServer implements MockerServer {
	private MockWebServer server;

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
				if (method.equals("GET") && path.equals("/version")) {
					Version version = new Version();
					version.setVersion("0.0.1");
					version.setBuildDate("2021-08-01");
					version.setCommit("commit");
					response.setBody(JacksonUtil.object2String(version));
					return response;
				}


				return response;
			}
		});

		server.start();
	}

	public void stop() throws Exception {
		if (Objects.nonNull(server)) {
			server.shutdown();
		}
	}

	@Override
	public int port() {
		return server.getPort();
	}
}
