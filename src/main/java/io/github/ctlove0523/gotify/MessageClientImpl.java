package io.github.ctlove0523.gotify;

import java.io.IOException;
import java.net.URI;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ctlove0523.gotify.app.Application;
import io.github.ctlove0523.gotify.message.Message;
import io.github.ctlove0523.gotify.message.PagedMessages;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

class MessageClientImpl implements MessageClient {
	private GotifyClientConfig clientConfig;

	private GotifyClientWebSocketClient webSocketClient;

	public MessageClientImpl(GotifyClientConfig clientConfig) {
		this.clientConfig = clientConfig;

		String uri = "ws://" + clientConfig.getHost() + ":" + clientConfig.getPort() + "/stream";
		String authInfo = clientConfig.getUserName() + ":" + clientConfig.getPassword();
		String authorization = Base64.getEncoder().encodeToString(authInfo.getBytes());

		this.webSocketClient = new GotifyClientWebSocketClient(URI.create(uri));
		webSocketClient.addHeader("Authorization", "Basic " + authorization);
		webSocketClient.connect();
	}


	@Override
	public PagedMessages getAppMessages(Integer appId, Integer limit, Integer since) {
		String authInfo = clientConfig.getUserName() + ":" + clientConfig.getPassword();
		String authorization = Base64.getEncoder().encodeToString(authInfo.getBytes());

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
				.header("Authorization", "Basic " + authorization)
				.get()
				.build();

		try (Response response = client.newCall(request).execute();
			 ResponseBody body = response.body()) {

			if (Objects.nonNull(body)) {
				String content = body.string();

				ObjectMapper mapper = new ObjectMapper();

				return mapper.readValue(content, PagedMessages.class);
			}

		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public PagedMessages getAppMessages(Integer appId, Integer limit) {
		return getAppMessages(appId, limit, null);
	}

	@Override
	public PagedMessages getAppMessages(Integer appId) {
		return getAppMessages(appId, 200, null);
	}

	@Override
	public Boolean deleteAppMessages(Integer appId) {
		String authInfo = clientConfig.getUserName() + ":" + clientConfig.getPassword();
		String authorization = Base64.getEncoder().encodeToString(authInfo.getBytes());

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
				.header("Authorization", "Basic " + authorization)
				.delete()
				.build();
		try (Response response = client.newCall(request).execute()) {

			return response.isSuccessful();

		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public PagedMessages getMessages() {
		return getMessages(200, null);
	}

	@Override
	public PagedMessages getMessages(Integer limit) {
		return getMessages(limit, null);
	}

	@Override
	public PagedMessages getMessages(Integer limit, Integer since) {
		String authInfo = clientConfig.getUserName() + ":" + clientConfig.getPassword();
		String authorization = Base64.getEncoder().encodeToString(authInfo.getBytes());

		OkHttpClient client = new OkHttpClient.Builder()
				.build();

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
				.header("Authorization", "Basic " + authorization)
				.get()
				.build();
		try (Response response = client.newCall(request).execute();
			 ResponseBody body = response.body()) {

			if (Objects.nonNull(body)) {
				String content = body.string();

				ObjectMapper mapper = new ObjectMapper();

				return mapper.readValue(content, PagedMessages.class);
			}

		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Message createMessage(Integer appId, Message message) {
		AppClient appClient = new AppClientImpl(clientConfig);
		String appToken = "";
		for (Application application : appClient.listApplication()) {
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
		try (Response response = client.newCall(request).execute();
			 ResponseBody responseBody = response.body()) {
			if (responseBody != null) {
				return JacksonUtil.string2Object(responseBody.string(), Message.class);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Boolean deleteMessages() {
		String authInfo = clientConfig.getUserName() + ":" + clientConfig.getPassword();
		String authorization = Base64.getEncoder().encodeToString(authInfo.getBytes());

		OkHttpClient client = new OkHttpClient.Builder()
				.build();

		String url = UriBuilder.builder()
				.config(clientConfig)
				.path("/message")
				.build();

		Request request = new Request.Builder()
				.url(url)
				.header("Authorization", "Basic " + authorization)
				.delete()
				.build();
		try (Response response = client.newCall(request).execute()) {

			return response.isSuccessful();

		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public Boolean deleteMessage(Integer id) {
		String authInfo = clientConfig.getUserName() + ":" + clientConfig.getPassword();
		String authorization = Base64.getEncoder().encodeToString(authInfo.getBytes());

		OkHttpClient client = new OkHttpClient.Builder()
				.build();


		Map<String, Object> pathParas = new HashMap<>();
		pathParas.put("id", id);

		String url = UriBuilder.builder()
				.config(clientConfig)
				.path("/message/{id}")
				.pathParams(pathParas)
				.build();

		Request request = new Request.Builder()
				.url(url)
				.header("Authorization", "Basic " + authorization)
				.delete()
				.build();
		try (Response response = client.newCall(request).execute()) {

			return response.isSuccessful();

		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return false;
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
