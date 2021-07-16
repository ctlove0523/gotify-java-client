package io.github.ctlove0523.gotify;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.ctlove0523.gotify.user.UpdateCurrentUserPasswordRequest;
import io.github.ctlove0523.gotify.user.User;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

class UserClientImpl implements UserClient {
	private final GotifyClientConfig clientConfig;

	UserClientImpl(GotifyClientConfig clientConfig) {
		this.clientConfig = clientConfig;
	}

	@Override
	public void close() {

	}

	@Override
	public Result<User, GotifyResponseError> currentUser() {
		String authInfo = clientConfig.getUserName() + ":" + clientConfig.getPassword();
		String authorization = Base64.getEncoder().encodeToString(authInfo.getBytes());

		OkHttpClient client = new OkHttpClient.Builder()
				.build();

		String url = UriBuilder.builder()
				.config(clientConfig)
				.path("/current/user")
				.build();

		Request request = new Request.Builder()
				.url(url)
				.header("Authorization", "Basic " + authorization)
				.get()
				.build();

		return new GotifyRequest.Builder()
				.client(client)
				.request(request)
				.clientAuthInfoWriter(ClientAuthInfoWriterFactory.writer(clientConfig.getCredential()))
				.build()
				.execute(User.class);
	}

	@Override
	public Result<Boolean, GotifyResponseError> updateCurrentUserPassword(UpdateCurrentUserPasswordRequest updateCurrentUserPasswordRequest) {
		String authInfo = clientConfig.getUserName() + ":" + clientConfig.getPassword();
		String authorization = Base64.getEncoder().encodeToString(authInfo.getBytes());

		OkHttpClient client = new OkHttpClient.Builder()
				.build();

		RequestBody requestBody = RequestBody.create(JacksonUtil.object2String(updateCurrentUserPasswordRequest),
				MediaType.get("application/json"));

		String url = UriBuilder.builder()
				.config(clientConfig)
				.path("/current/user/password")
				.build();

		Request request = new Request.Builder()
				.url(url)
				.header("Authorization", "Basic " + authorization)
				.put(requestBody)
				.build();

		return new GotifyRequest.Builder()
				.client(client)
				.request(request)
				.clientAuthInfoWriter(ClientAuthInfoWriterFactory.writer(clientConfig.getCredential()))
				.build()
				.execute(Boolean.class);
	}

	@Override
	public Result<List<User>, GotifyResponseError> getUsers() {
		String authInfo = clientConfig.getUserName() + ":" + clientConfig.getPassword();
		String authorization = Base64.getEncoder().encodeToString(authInfo.getBytes());

		OkHttpClient client = new OkHttpClient.Builder()
				.build();

		String url = UriBuilder.builder()
				.config(clientConfig)
				.path("/user")
				.build();
		Request request = new Request.Builder()
				.url(url)
				.header("Authorization", "Basic " + authorization)
				.get()
				.build();

		return new GotifyRequest.Builder()
				.client(client)
				.request(request)
				.clientAuthInfoWriter(ClientAuthInfoWriterFactory.writer(clientConfig.getCredential()))
				.build()
				.executeReturnList(User.class);
	}

	@Override
	public Result<User, GotifyResponseError> createUser(User user) {
		String authInfo = clientConfig.getUserName() + ":" + clientConfig.getPassword();
		String authorization = Base64.getEncoder().encodeToString(authInfo.getBytes());

		OkHttpClient client = new OkHttpClient.Builder()
				.build();

		RequestBody requestBody = RequestBody.create(JacksonUtil.object2String(user),
				MediaType.get("application/json"));

		String url = UriBuilder.builder()
				.config(clientConfig)
				.path("/user")
				.build();

		Request request = new Request.Builder()
				.url(url)
				.header("Authorization", "Basic " + authorization)
				.post(requestBody)
				.build();

		return new GotifyRequest.Builder()
				.client(client)
				.request(request)
				.clientAuthInfoWriter(ClientAuthInfoWriterFactory.writer(clientConfig.getCredential()))
				.build()
				.execute(User.class);
	}

	@Override
	public Result<User, GotifyResponseError> getUser(Integer id) {
		String authInfo = clientConfig.getUserName() + ":" + clientConfig.getPassword();
		String authorization = Base64.getEncoder().encodeToString(authInfo.getBytes());

		OkHttpClient client = new OkHttpClient.Builder()
				.build();

		Map<String, Object> pathParas = new HashMap<>();
		pathParas.put("id", id);

		String url = UriBuilder.builder()
				.config(clientConfig)
				.path("/user/{id}")
				.pathParams(pathParas)
				.build();
		Request request = new Request.Builder()
				.url(url)
				.header("Authorization", "Basic " + authorization)
				.get()
				.build();
		return new GotifyRequest.Builder()
				.client(client)
				.request(request)
				.clientAuthInfoWriter(ClientAuthInfoWriterFactory.writer(clientConfig.getCredential()))
				.build()
				.execute(User.class);
	}

	@Override
	public Result<User, GotifyResponseError> updateUser(User user) {
		String authInfo = clientConfig.getUserName() + ":" + clientConfig.getPassword();
		String authorization = Base64.getEncoder().encodeToString(authInfo.getBytes());

		OkHttpClient client = new OkHttpClient.Builder()
				.build();

		RequestBody requestBody = RequestBody.create(JacksonUtil.object2String(user),
				MediaType.get("application/json"));

		String url = UriBuilder.builder()
				.config(clientConfig)
				.path("/user")
				.build();

		Request request = new Request.Builder()
				.url(url)
				.header("Authorization", "Basic " + authorization)
				.put(requestBody)
				.build();
		return new GotifyRequest.Builder()
				.client(client)
				.request(request)
				.clientAuthInfoWriter(ClientAuthInfoWriterFactory.writer(clientConfig.getCredential()))
				.build()
				.execute(User.class);
	}

	@Override
	public Result<Boolean, GotifyResponseError> deleteUser(Integer id) {
		String authInfo = clientConfig.getUserName() + ":" + clientConfig.getPassword();
		String authorization = Base64.getEncoder().encodeToString(authInfo.getBytes());

		OkHttpClient client = new OkHttpClient.Builder()
				.build();

		Map<String, Object> pathParas = new HashMap<>();
		pathParas.put("id", id);

		String url = UriBuilder.builder()
				.config(clientConfig)
				.path("/user/{id}")
				.pathParams(pathParas)
				.build();
		Request request = new Request.Builder()
				.url(url)
				.header("Authorization", "Basic " + authorization)
				.delete()
				.build();

		return new GotifyRequest.Builder()
				.client(client)
				.request(request)
				.clientAuthInfoWriter(ClientAuthInfoWriterFactory.writer(clientConfig.getCredential()))
				.build()
				.execute(Boolean.class);
	}
}
