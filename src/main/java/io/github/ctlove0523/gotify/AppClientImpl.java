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
import io.github.ctlove0523.gotify.app.Application;
import io.github.ctlove0523.gotify.app.CreateApplicationRequest;
import io.github.ctlove0523.gotify.app.UpdateApplictionRequest;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

class AppClientImpl implements AppClient {
	private GotifyClientConfig clientConfig;

	public AppClientImpl(GotifyClientConfig clientConfig) {
		this.clientConfig = clientConfig;
	}

	@Override
	public Iterable<Application> listApplication() {
		String authInfo = clientConfig.getUserName() + ":" + clientConfig.getPassword();
		String authorization = Base64.getEncoder().encodeToString(authInfo.getBytes());

		String url = UriBuilder.builder()
				.config(clientConfig)
				.path("/application")
				.build();
		Request request = new Request.Builder()
				.url(url)
				.header("Authorization", "Basic " + authorization)
				.get()
				.build();

		OkHttpClient client = new OkHttpClient.Builder()
				.build();
		try (Response response = client.newCall(request).execute();
			 ResponseBody body = response.body()) {

			if (Objects.nonNull(body)) {
				String content = body.string();

				ObjectMapper mapper = new ObjectMapper();

				return mapper.readValue(content, new TypeReference<List<Application>>() { });
			}

		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return new ArrayList<>();
	}

	@Override
	public Application createApplication(CreateApplicationRequest createApplicationRequest) {
		String authInfo = clientConfig.getUserName() + ":" + clientConfig.getPassword();
		String authorization = Base64.getEncoder().encodeToString(authInfo.getBytes());

		OkHttpClient client = new OkHttpClient.Builder()
				.build();
		RequestBody requestBody = RequestBody
				.create(JacksonUtil.object2String(createApplicationRequest), MediaType.get("application/json"));

		String url = UriBuilder.builder()
				.config(clientConfig)
				.path("/application")
				.build();
		Request request = new Request.Builder()
				.url(url)
				.header("Authorization", "Basic " + authorization)
				.post(requestBody)
				.build();
		try (Response response = client.newCall(request).execute();
			 ResponseBody body = response.body()) {

			if (Objects.nonNull(body)) {
				String content = body.string();

				return JacksonUtil.string2Object(content, Application.class);
			}

		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Application updateApplication(int id, UpdateApplictionRequest updateApplictionRequest) {
		String authInfo = clientConfig.getUserName() + ":" + clientConfig.getPassword();
		String authorization = Base64.getEncoder().encodeToString(authInfo.getBytes());

		OkHttpClient client = new OkHttpClient.Builder()
				.build();
		RequestBody requestBody = RequestBody
				.create(JacksonUtil.object2String(updateApplictionRequest), MediaType.get("application/json"));

		Map<String, Object> pathPars = new HashMap<>();
		pathPars.put("id", id);

		String url = UriBuilder.builder()
				.config(clientConfig)
				.path("/application/{id}")
				.pathParams(pathPars)
				.build();

		Request request = new Request.Builder()
				.url(url)
				.header("Authorization", "Basic " + authorization)
				.put(requestBody)
				.build();
		try (Response response = client.newCall(request).execute();
			 ResponseBody body = response.body()) {

			if (Objects.nonNull(body)) {
				String content = body.string();

				return JacksonUtil.string2Object(content, Application.class);
			}

		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public boolean deleteApplication(int id) {
		String authInfo = clientConfig.getUserName() + ":" + clientConfig.getPassword();
		String authorization = Base64.getEncoder().encodeToString(authInfo.getBytes());

		OkHttpClient client = new OkHttpClient.Builder()
				.build();
		Map<String, Object> pathPars = new HashMap<>();
		pathPars.put("id", id);

		String url = UriBuilder.builder()
				.config(clientConfig)
				.path("/application/{id}")
				.pathParams(pathPars)
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
	public Application uploadApplicationImage(int id, byte[] image) {
		String authInfo = clientConfig.getUserName() + ":" + clientConfig.getPassword();
		String authorization = Base64.getEncoder().encodeToString(authInfo.getBytes());

		OkHttpClient client = new OkHttpClient.Builder()
				.build();
		RequestBody requestBody = new MultipartBody.Builder()
				.setType(MultipartBody.FORM)
				.addFormDataPart("file", "fileName",
						RequestBody.create(image, MediaType.parse("multipart/form-data")))
				.build();
		Map<String, Object> pathPars = new HashMap<>();
		pathPars.put("id", id);

		String url = UriBuilder.builder()
				.config(clientConfig)
				.path("/application/{id}/image")
				.pathParams(pathPars)
				.build();
		Request request = new Request.Builder()
				.url(url)
				.header("Authorization", "Basic " + authorization)
				.post(requestBody)
				.build();
		try (Response response = client.newCall(request).execute();
			 ResponseBody body = response.body()) {

			if (Objects.nonNull(body)) {
				String content = body.string();

				return JacksonUtil.string2Object(content, Application.class);
			}

		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void close() {

	}
}
