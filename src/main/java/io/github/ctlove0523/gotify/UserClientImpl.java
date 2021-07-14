package io.github.ctlove0523.gotify;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ctlove0523.gotify.user.UpdateCurrentUserPasswordRequest;
import io.github.ctlove0523.gotify.user.User;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

class UserClientImpl implements UserClient {
	private GotifyClientConfig clientConfig;

	UserClientImpl(GotifyClientConfig clientConfig) {
		this.clientConfig = clientConfig;
	}

	@Override
	public void close() {

	}

	@Override
	public User currentUser() {
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

		try (Response response = client.newCall(request).execute();
			 ResponseBody body = response.body()) {

			if (Objects.nonNull(body)) {
				String content = body.string();

				ObjectMapper mapper = new ObjectMapper();

				return mapper.readValue(content, User.class);
			}

		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Boolean updateCurrentUserPassword(UpdateCurrentUserPasswordRequest updateCurrentUserPasswordRequest) {
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

		try (Response response = client.newCall(request).execute()) {
			return response.isSuccessful();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public Iterable<User> getUsers() {
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

		try (Response response = client.newCall(request).execute();
			 ResponseBody responseBody = response.body()) {
			if (Objects.nonNull(responseBody)) {
				String content = responseBody.string();

				ObjectMapper mapper = new ObjectMapper();

				return mapper.readValue(content, new TypeReference<Iterable<User>>() { });
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return new ArrayList<>();
	}

	@Override
	public User createUser(User user) {
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

		try (Response response = client.newCall(request).execute();
			 ResponseBody responseBody = response.body()) {
			if (Objects.nonNull(responseBody)) {
				String content = responseBody.string();
				return JacksonUtil.string2Object(content, User.class);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public User getUser(Integer id) {
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

		try (Response response = client.newCall(request).execute();
			 ResponseBody responseBody = response.body()) {
			if (Objects.nonNull(responseBody)) {
				String content = responseBody.string();
				return JacksonUtil.string2Object(content, User.class);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public User updateUser(User user) {
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

		try (Response response = client.newCall(request).execute();
			 ResponseBody responseBody = response.body()) {
			if (Objects.nonNull(responseBody)) {
				String content = responseBody.string();
				return JacksonUtil.string2Object(content, User.class);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Boolean deleteUser(Integer id) {
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

		try (Response response = client.newCall(request).execute()) {
			return response.isSuccessful();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}
}
