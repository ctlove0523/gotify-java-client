package io.github.ctlove0523.gotify;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.ctlove0523.gotify.device.Client;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

public class DeviceClientImpl implements DeviceClient {
	private final GotifyClientConfig clientConfig;

	DeviceClientImpl(GotifyClientConfig clientConfig) {
		this.clientConfig = clientConfig;
	}

	@Override
	public Result<List<Client>, GotifyResponseError> getClients() {
		String url = UriBuilder.builder()
				.config(clientConfig)
				.path("/client")
				.build();
		Request request = new Request.Builder()
				.url(url)
				.get()
				.build();
		return new GotifyRequest.Builder()
				.request(request)
				.clientAuthInfoWriter(ClientAuthInfoWriterFactory.writer(clientConfig.getCredential()))
				.build()
				.executeReturnList(Client.class);
	}

	@Override
	public Result<Client, GotifyResponseError> createClient(Client client) {
		RequestBody requestBody = RequestBody
				.create(JacksonUtil.object2String(client), MediaType.get("application/json"));

		String url = UriBuilder.builder()
				.config(clientConfig)
				.path("/client")
				.build();
		Request request = new Request.Builder()
				.url(url)
				.post(requestBody)
				.build();

		return new GotifyRequest.Builder()
				.request(request)
				.clientAuthInfoWriter(ClientAuthInfoWriterFactory.writer(clientConfig.getCredential()))
				.build()
				.execute(Client.class);
	}

	@Override
	public Result<Client, GotifyResponseError> updateClient(Integer id, Client client) {
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
				.put(requestBody)
				.build();
		return new GotifyRequest.Builder()
				.request(request)
				.clientAuthInfoWriter(ClientAuthInfoWriterFactory.writer(clientConfig.getCredential()))
				.build()
				.execute(Client.class);
	}

	@Override
	public Result<Boolean, GotifyResponseError> deleteClient(Integer id) {

		Map<String, Object> pathParas = new HashMap<>();
		pathParas.put("id", id);

		String url = UriBuilder.builder()
				.config(clientConfig)
				.path("/client/{id}")
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
}
