package io.github.ctlove0523.gotify;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ctlove0523.gotify.device.Client;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class DeviceClientImpl implements DeviceClient {
	private GotifyClientConfig clientConfig;

	DeviceClientImpl(GotifyClientConfig clientConfig) {
		this.clientConfig = clientConfig;
	}

	@Override
	public Iterable<Client> getClients() {
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
		try (Response response = client.newCall(request).execute();
			 ResponseBody body = response.body()) {

			if (Objects.nonNull(body)) {
				String content = body.string();

				ObjectMapper mapper = new ObjectMapper();

				return mapper.readValue(content, new TypeReference<List<Client>>() { });
			}

		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return new ArrayList<>();
	}

	@Override
	public Client createClient(Client client) {
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
		try (Response response = okHttpClient.newCall(request).execute();
			 ResponseBody body = response.body()) {

			if (Objects.nonNull(body)) {
				String content = body.string();

				ObjectMapper mapper = new ObjectMapper();

				return mapper.readValue(content, Client.class);
			}

		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Client updateClient(Integer id, Client client) {
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
		try (Response response = okHttpClient.newCall(request).execute();
			 ResponseBody body = response.body()) {

			if (Objects.nonNull(body)) {
				String content = body.string();
				System.out.println(content);

				ObjectMapper mapper = new ObjectMapper();

				return mapper.readValue(content, Client.class);
			}

		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Boolean deleteClient(Integer id) {
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
		try (Response response = okHttpClient.newCall(request).execute()) {
			return response.isSuccessful();

		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public void close() {

	}
}
