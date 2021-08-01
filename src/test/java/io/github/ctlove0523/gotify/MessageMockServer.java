package io.github.ctlove0523.gotify;

import java.nio.charset.Charset;
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

				// 删除应用下的消息
				if (method.equals("DELETE") && path.contains("/application")) {
					System.out.println("begin to delete app message");
					Integer appId = getAppId(request.getRequestUrl());
					appMessages.remove(appId);
					response.setResponseCode(200);
					return response;
				}

				// 查询消息
				if (method.equals("GET") && path.contains("/message")) {
					System.out.println("begin to get all message");
					List<Message> messages = new ArrayList<>();
					appMessages.forEach((integer, messageList) -> messages.addAll(messageList));
					Paging paging = new Paging();
					paging.setSize(messages.size());

					PagedMessages pagedMessages = new PagedMessages();
					pagedMessages.setMessages(messages);
					pagedMessages.setPaging(paging);

					response.setBody(JacksonUtil.object2String(pagedMessages));

					return response;
				}

				// 创建消息
				if (method.equals("POST") && path.contains("/message")) {
					System.out.println("begin to create message");
					String content = request.getBody().readString(Charset.defaultCharset());
					Message message = JacksonUtil.string2Object(content, Message.class);
					addMessage(message);
				}

				// 删除所有消息
				if (method.equals("DELETE") && path.contains("/message") && !path.contains("/message/")) {
					System.out.println("begin to delete all message");
					System.out.println(path);
					appMessages.clear();
					response.setResponseCode(200);
					return response;
				}

				// 删除指定ID的消息
				if (method.equals("DELETE") && path.contains("/message/")) {
					Integer messageId = Integer.parseInt(path.replace("/message/", ""));
					System.out.println("begin to delete message with id " + messageId);
					deleteMessage(messageId);
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

	public List<Message> getAppMessage(Integer appId) {
		return appMessages.getOrDefault(appId, new ArrayList<>());
	}

	public List<Message> getMessages() {
		List<Message> messages = new ArrayList<>();
		appMessages.forEach((integer, messageList) -> messages.addAll(messageList));

		return messages;
	}

	private void deleteMessage(Integer id) {
		for (Integer appId : appMessages.keySet()) {
			appMessages.get(appId).removeIf(message -> message.getId().equals(id));
		}
	}
}
