package io.github.ctlove0523.gotify;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.ctlove0523.gotify.plugin.PluginConf;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

class PluginClientImpl implements PluginClient {

	private final GotifyClientConfig config;

	PluginClientImpl(GotifyClientConfig config) {
		this.config = config;
	}

	@Override
	public Result<List<PluginConf>, ResponseError> getPlugins() {
		String url = UriBuilder.builder()
				.config(config)
				.path("/plugin")
				.build();

		Request request = new Request.Builder()
				.url(url)
				.get()
				.build();

		return new GotifyRequest.Builder()
				.request(request)
				.clientAuthInfoWriter(ClientAuthInfoWriterFactory.writer(config.getCredential()))
				.build()
				.executeReturnList(PluginConf.class);
	}

	@Override
	public Result<Boolean, ResponseError> updatePluginConfig(int id) {
		Map<String, Object> params = new HashMap<>(1);
		params.put("id", id);

		String url = UriBuilder.builder()
				.config(config)
				.path("/plugin/{id}/config")
				.pathParams(params)
				.build();

		RequestBody requestBody = RequestBody.create(new byte[] {}, MediaType.get("application/json"));

		Request request = new Request.Builder()
				.url(url)
				.post(requestBody)
				.build();

		return new GotifyRequest.Builder()
				.request(request)
				.clientAuthInfoWriter(ClientAuthInfoWriterFactory.writer(config.getCredential()))
				.build()
				.execute(Boolean.class);
	}

	@Override
	public Result<String, ResponseError> getPluginDisplay(int id) {
		Map<String, Object> params = new HashMap<>(1);
		params.put("id", id);

		String url = UriBuilder.builder()
				.config(config)
				.path("/plugin/{id}/display")
				.pathParams(params)
				.build();

		Request request = new Request.Builder()
				.url(url)
				.get()
				.build();

		return new GotifyRequest.Builder()
				.request(request)
				.clientAuthInfoWriter(ClientAuthInfoWriterFactory.writer(config.getCredential()))
				.build()
				.execute(String.class);
	}

	@Override
	public Result<Boolean, ResponseError> disablePlugin(int id) {
		Map<String, Object> params = new HashMap<>(1);
		params.put("id", id);

		String url = UriBuilder.builder()
				.config(config)
				.path("/plugin/{id}/disable")
				.pathParams(params)
				.build();

		Request request = new Request.Builder()
				.url(url)
				.get()
				.build();

		return new GotifyRequest.Builder()
				.request(request)
				.clientAuthInfoWriter(ClientAuthInfoWriterFactory.writer(config.getCredential()))
				.build()
				.execute(Boolean.class);
	}

	@Override
	public Result<Boolean, ResponseError> enablePlugin(int id) {
		Map<String, Object> params = new HashMap<>(1);
		params.put("id", id);

		String url = UriBuilder.builder()
				.config(config)
				.path("/plugin/{id}/enable")
				.pathParams(params)
				.build();

		Request request = new Request.Builder()
				.url(url)
				.get()
				.build();

		return new GotifyRequest.Builder()
				.request(request)
				.clientAuthInfoWriter(ClientAuthInfoWriterFactory.writer(config.getCredential()))
				.build()
				.execute(Boolean.class);
	}
}
