package io.github.ctlove0523.gotify;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.ctlove0523.gotify.device.Client;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class DeviceClientImpl implements DeviceClient {
	private final GotifyClientConfig clientConfig;

	DeviceClientImpl(GotifyClientConfig clientConfig) {
		this.clientConfig = clientConfig;
	}

	@Override
	public Result<List<Client>, GotifyResponseError> getClients() {
		String authInfo = clientConfig.getUserName() + ":" + clientConfig.getPassword();
		String authorization = Base64.getEncoder().encodeToString(authInfo.getBytes());

		OkHttpClient client = new OkHttpClient.Builder()
				.build();
		String url = UriBuilder.builder()
				.config(clientConfig)
				.path("/client")
				.build();
		Request request = new Request.Builder()
				.url(url)
				.header("Authorization", "Basic " + authorization)
				.get()
				.build();
		return GotifyRequest.builder().client(client).request(request).executeReturnList(Client.class);
	}

	@Override
	public Result<Client, GotifyResponseError> createClient(Client client) {
		String authInfo = clientConfig.getUserName() + ":" + clientConfig.getPassword();
		String authorization = Base64.getEncoder().encodeToString(authInfo.getBytes());

		OkHttpClient okHttpClient = new OkHttpClient.Builder()
				.build();
		RequestBody requestBody = RequestBody
				.create(JacksonUtil.object2String(client), MediaType.get("application/json"));

		String url = UriBuilder.builder()
				.config(clientConfig)
				.path("/client")
				.build();
		Request request = new Request.Builder()
				.url(url)
				.header("Authorization", "Basic " + authorization)
				.post(requestBody)
				.build();
		return GotifyRequest.builder().client(okHttpClient).request(request).execute(Client.class);
	}

	@Override
	public Result<Client, GotifyResponseError> updateClient(Integer id, Client client) {
		String authInfo = clientConfig.getUserName() + ":" + clientConfig.getPassword();
		String authorization = Base64.getEncoder().encodeToString(authInfo.getBytes());

		OkHttpClient okHttpClient = new OkHttpClient.Builder()
				.build();
		RequestBody requestBody = RequestBody
				.create(JacksonUtil.object2String(client), MediaType.get("application/json"));
		Map<String, Object> pathParas = new HashMap<>();
		pathParas.put("id", id);

		String url = UriBuilder.builder()
				.config(clientConfig)
				.path("/client/{id}")
				.pathParams(pathParas)
				.build();
		Request request = new Request.Builder()
				.url(url)
				.header("Authorization", "Basic " + authorization)
				.put(requestBody)
				.build();
		return GotifyRequest.builder().client(okHttpClient).request(request).execute(Client.class);
	}

	@Override
	public Result<Boolean, GotifyResponseError> deleteClient(Integer id) {
		String authInfo = clientConfig.getUserName() + ":" + clientConfig.getPassword();
		String authorization = Base64.getEncoder().encodeToString(authInfo.getBytes());

		OkHttpClient okHttpClient = new OkHttpClient.Builder()
				.build();
		Map<String, Object> pathParas = new HashMap<>();
		pathParas.put("id", id);

		String url = UriBuilder.builder()
				.config(clientConfig)
				.path("/client/{id}")
				.pathParams(pathParas)
				.build();
		Request request = new Request.Builder()
				.url(url)
				.header("Authorization", "Basic " + authorization)
				.delete()
				.build();
		return GotifyRequest.builder().client(okHttpClient).request(request).execute(Boolean.class);
	}

	@Override
	public void close() {

	}
}
