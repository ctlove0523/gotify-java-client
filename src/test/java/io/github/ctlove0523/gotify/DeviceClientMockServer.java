package io.github.ctlove0523.gotify;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.github.ctlove0523.gotify.device.Client;
import lombok.SneakyThrows;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.jetbrains.annotations.NotNull;

public class DeviceClientMockServer implements MockerServer {
	private MockWebServer server;

	private Map<Integer, Client> clients = new HashMap<>();

	@Override
	public int port() {
		return server.getPort();
	}

	public void stop() throws Exception {
		if (Objects.nonNull(server)) {
			server.shutdown();
			clients.clear();
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

				// 查询clients
				if (method.equals("GET") && path.contains("/client")) {
					List<Client> clientList = new ArrayList<>(clients.values());

					response.setBody(JacksonUtil.object2String(clientList));

					return response;
				}

				// 创建client
				if (method.equals("POST") && path.contains("/client")) {
					String content = request.getBody().readString(Charset.defaultCharset());
					Client client = JacksonUtil.string2Object(content, Client.class);
					clients.put(client.getId(), client);
					response.setResponseCode(200);
					response.setBody(JacksonUtil.object2String(client));
					return response;
				}

				// 更新client
				if (method.equals("PUT") && path.contains("/client")) {
					String content = request.getBody().readString(Charset.defaultCharset());
					Client client = JacksonUtil.string2Object(content, Client.class);
					Integer clientId = getClientId(path);
					client.setId(clientId);

					clients.put(clientId, client);

					response.setBody(JacksonUtil.object2String(client));

					return response;
				}

				// 删除client
				if (method.equals("DELETE") && path.contains("/client")) {
					Integer clientId = getClientId(path);
					if (clients.containsKey(clientId)) {
						clients.remove(clientId);
						response.setResponseCode(200);
					}
					else {
						response.setResponseCode(404);
					}

					return response;
				}

				return response;
			}
		});

		server.start();
	}

	public void addClient(Client client) {
		clients.put(client.getId(), client);
	}

	private Integer getClientId(String path) {
		return Integer.parseInt(path.replace("/client/", ""));
	}

	public Client getClient(Integer id) {
		return clients.get(id);
	}

}
