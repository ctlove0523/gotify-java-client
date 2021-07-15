package io.github.ctlove0523.gotify;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.ctlove0523.gotify.app.Application;
import io.github.ctlove0523.gotify.app.CreateApplicationRequest;
import io.github.ctlove0523.gotify.app.UpdateApplictionRequest;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

class AppClientImpl implements AppClient {
	private final GotifyClientConfig clientConfig;

	public AppClientImpl(GotifyClientConfig clientConfig) {
		this.clientConfig = clientConfig;
	}

	@Override
	public Result<List<Application>, GotifyResponseError> getApplications() {
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

		return GotifyRequest.builder().client(client).request(request).executeReturnList(Application.class);
	}

	@Override
	public Result<Application, GotifyResponseError> createApplication(CreateApplicationRequest createApplicationRequest) {
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
		return GotifyRequest.builder().client(client).request(request).execute(Application.class);
	}

	@Override
	public Result<Application, GotifyResponseError> updateApplication(int id, UpdateApplictionRequest updateApplictionRequest) {
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
		return GotifyRequest.builder().client(client).request(request).execute(Application.class);
	}

	@Override
	public Result<Boolean, GotifyResponseError> deleteApplication(int id) {
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
		return GotifyRequest.builder().client(client).request(request).execute(Boolean.class);
	}

	@Override
	public Result<Application, GotifyResponseError> uploadApplicationImage(int id, byte[] image) {
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

		return GotifyRequest.builder().client(client).request(request).execute(Application.class);
	}

	@Override
	public void close() {

	}
}
