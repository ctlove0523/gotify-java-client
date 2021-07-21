package io.github.ctlove0523.gotify;

import io.github.ctlove0523.gotify.health.Health;
import okhttp3.Request;

class HealthClientImpl implements HealthClient {
	private final GotifyClientConfig config;

	public HealthClientImpl(GotifyClientConfig config) {
		this.config = config;
	}

	@Override
	public Result<Health, ResponseError> getHealth() {
		String url = UriBuilder.builder()
				.config(config)
				.path("/health")
				.build();
		Request request = new Request.Builder()
				.url(url)
				.get()
				.build();
		return new GotifyRequest.Builder()
				.request(request)
				.clientAuthInfoWriter(ClientAuthInfoWriterFactory.writer(config.getCredential()))
				.build()
				.execute(Health.class);
	}
}
