package io.github.ctlove0523.gotify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.github.ctlove0523.gotify.message.Message;
import io.github.ctlove0523.gotify.message.PagedMessages;
import io.github.ctlove0523.gotify.message.Paging;
import lombok.SneakyThrows;
import okhttp3.HttpUrl;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.jetbrains.annotations.NotNull;

public class MessageMockServer {
	private MockWebServer server;

	private Map<Integer, List<Message>> appMessages = new HashMap<>();

	public int port() {
		return server.getPort();
	}
	public void stop() throws Exception {
		if (Objects.nonNull(server)) {
			server.shutdown();
			appMessages.clear();
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
				if (method.equals("GET") && path.contains("/application")) {
					Integer appId = getAppId(request.getRequestUrl());
					Paging paging = new Paging();
					paging.setSize(appMessages.get(appId).size());

					PagedMessages pagedMessages = new PagedMessages();
					pagedMessages.setMessages(appMessages.get(appId));

					response.setBody(JacksonUtil.object2String(pagedMessages));

					return response;
				}
				return response;
			}
		});

		server.start();
	}

	private Integer getAppId(HttpUrl httpUrl) {
		return Integer.parseInt(httpUrl.pathSegments().get(1));
	}

	public void addMessage(Message message) {
		Integer appId = message.getAppid();
		List<Message> messages = appMessages.getOrDefault(appId, new ArrayList<>());
		messages.add(message);
		appMessages.put(appId, messages);
	}

	public void addMessages(List<Message> messages) {
		for (Message message : messages) {
			addMessage(message);
		}
	}
}
