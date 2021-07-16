package io.github.ctlove0523.gotify;

import java.net.URI;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.github.ctlove0523.gotify.app.Application;
import io.github.ctlove0523.gotify.message.Message;
import io.github.ctlove0523.gotify.message.PagedMessages;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

class MessageClientImpl implements MessageClient {
	private final GotifyClientConfig clientConfig;

	private final GotifyClientWebSocketClient webSocketClient;

	public MessageClientImpl(GotifyClientConfig clientConfig) {
		this.clientConfig = clientConfig;

		String uri = "ws://" + clientConfig.getHost() + ":" + clientConfig.getPort() + "/stream";
		String authInfo = clientConfig.getCredential().getUserName() + ":" + clientConfig.getCredential().getPassword();
		String authorization = Base64.getEncoder().encodeToString(authInfo.getBytes());

		this.webSocketClient = new GotifyClientWebSocketClient(URI.create(uri));
		webSocketClient.addHeader("Authorization", "Basic " + authorization);
		webSocketClient.connect();
	}


	@Override
	public Result<PagedMessages, GotifyResponseError> getAppMessages(Integer appId, Integer limit, Integer since) {

		OkHttpClient client = new OkHttpClient.Builder()
				.build();
		Map<String, Object> pathParas = new HashMap<>();
		pathParas.put("id", appId);

		Map<String, Object> queryParas = new HashMap<>();
		if (limit != null) {
			queryParas.put("limit", limit);
		}
		if (since != null) {
			queryParas.put("since", since);
		}

		String url = UriBuilder.builder()
				.config(clientConfig)
				.path("/application/{id}/message")
				.pathParams(pathParas)
				.queryParams(queryParas)
				.build();

		Request request = new Request.Builder()
				.url(url)
				.get()
				.build();

		return new GotifyRequest.Builder()
				.request(request)
				.clientAuthInfoWriter(ClientAuthInfoWriterFactory.writer(clientConfig.getCredential()))
				.build()
				.execute(PagedMessages.class);
	}

	@Override
	public Result<PagedMessages, GotifyResponseError> getAppMessages(Integer appId, Integer limit) {
		return getAppMessages(appId, limit, null);
	}

	@Override
	public Result<PagedMessages, GotifyResponseError> getAppMessages(Integer appId) {
		return getAppMessages(appId, 200, null);
	}

	@Override
	public Result<Boolean, GotifyResponseError> deleteAppMessages(Integer appId) {

		OkHttpClient client = new OkHttpClient.Builder()
				.build();
		Map<String, Object> pathParas = new HashMap<>();
		pathParas.put("id", appId);

		String url = UriBuilder.builder()
				.config(clientConfig)
				.path("/application/{id}/message")
				.pathParams(pathParas)
				.build();
		Request request = new Request.Builder()
				.url(url)
				.delete()
				.build();
		return new GotifyRequest.Builder()
				.request(request)
				.clientAuthInfoWriter(ClientAuthInfoWriterFactory.writer(clientConfig.getCredential()))
				.build()
				.execute(Boolean.class);
	}

	@Override
	public Result<PagedMessages, GotifyResponseError> getMessages() {
		return getMessages(200, null);
	}

	@Override
	public Result<PagedMessages, GotifyResponseError> getMessages(Integer limit) {
		return getMessages(limit, null);
	}

	@Override
	public Result<PagedMessages, GotifyResponseError> getMessages(Integer limit, Integer since) {
		Map<String, Object> queryParas = new HashMap<>();
		if (limit != null) {
			queryParas.put("limit", limit);
		}
		if (since != null) {
			queryParas.put("since", since);
		}

		String url = UriBuilder.builder()
				.config(clientConfig)
				.path("/message")
				.queryParams(queryParas)
				.build();
		Request request = new Request.Builder()
				.url(url)
				.get()
				.build();
		return new GotifyRequest.Builder()
				.request(request)
				.clientAuthInfoWriter(ClientAuthInfoWriterFactory.writer(clientConfig.getCredential()))
				.build()
				.execute(PagedMessages.class);
	}

	@Override
	public Result<Message, GotifyResponseError> createMessage(Integer appId, Message message) {
		AppClient appClient = new AppClientImpl(clientConfig);
		String appToken = "";
		Result<List<Application>, GotifyResponseError> result = appClient.getApplications();
		if (!result.isSuccessful()) {
			return GotifyResult.error(new GotifyResponseError());
		}
		for (Application application : result.result()) {
			if (application.getId().equals(appId)) {
				appToken = application.getToken();
				break;
			}
		}

		RequestBody body = RequestBody.create(JacksonUtil.object2String(message), MediaType.get("application/json"));

		String url = UriBuilder.builder()
				.config(clientConfig)
				.path("/message")
				.build();
		Request request = new Request.Builder()
				.url(url)
				.header("X-Gotify-Key", appToken)
				.header("Content-Type", "application/json")
				.post(body)
				.build();

		OkHttpClient client = new OkHttpClient.Builder()
				.build();
		return new GotifyRequest.Builder()
				.request(request)
				.clientAuthInfoWriter(ClientAuthInfoWriterFactory.writer(clientConfig.getCredential()))
				.build()
				.execute(Message.class);
	}

	@Override
	public Result<Boolean, GotifyResponseError> deleteMessages() {

		OkHttpClient client = new OkHttpClient.Builder()
				.build();

		String url = UriBuilder.builder()
				.config(clientConfig)
				.path("/message")
				.build();

		Request request = new Request.Builder()
				.url(url)
				.delete()
				.build();
		return new GotifyRequest.Builder()
				.request(request)
				.clientAuthInfoWriter(ClientAuthInfoWriterFactory.writer(clientConfig.getCredential()))
				.build()
				.execute(Boolean.class);
	}

	@Override
	public Result<Boolean, GotifyResponseError> deleteMessage(Integer id) {
		Map<String, Object> pathParas = new HashMap<>();
		pathParas.put("id", id);

		String url = UriBuilder.builder()
				.config(clientConfig)
				.path("/message/{id}")
				.pathParams(pathParas)
				.build();

		Request request = new Request.Builder()
				.url(url)
				.delete()
				.build();

		return new GotifyRequest.Builder()
				.request(request)
				.clientAuthInfoWriter(ClientAuthInfoWriterFactory.writer(clientConfig.getCredential()))
				.build().execute(Boolean.class);
	}

	@Override
	public void registerMessageHandler(MessageHandler handler) {
		Objects.requireNonNull(handler, "handler");
		webSocketClient.addHandler(handler);
	}

	@Override
	public void close() {

	}
}
