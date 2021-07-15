package io.github.ctlove0523.gotify;

import java.util.Base64;

import io.github.ctlove0523.gotify.health.Health;
import okhttp3.OkHttpClient;
import okhttp3.Request;

class HealthClientImpl implements HealthClient {
	private final GotifyClientConfig config;

	public HealthClientImpl(GotifyClientConfig config) {
		this.config = config;
	}

	@Override
	public Result<Health, GotifyResponseError> getHealth() {
		String authInfo = config.getUserName() + ":" + config.getPassword();
		String authorization = Base64.getEncoder().encodeToString(authInfo.getBytes());

		OkHttpClient client = new OkHttpClient.Builder()
				.build();

		String url = UriBuilder.builder()
				.config(config)
				.path("/health")
				.build();
		Request request = new Request.Builder()
				.url(url)
				.header("Authorization", "Basic " + authorization)
				.get()
				.build();
		return GotifyRequest.builder().client(client).request(request).execute(Health.class);
	}


	@Override
	public void close() {

	}
}
