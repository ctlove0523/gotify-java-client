package io.github.ctlove0523.gotify;

import io.github.ctlove0523.gotify.version.GotifyVersion;
import okhttp3.Request;

class VersionClientImpl implements VersionClient {
	private final GotifyClientConfig config;

	VersionClientImpl(GotifyClientConfig config) {
		this.config = config;
	}

	@Override
	public Result<GotifyVersion, GotifyResponseError> getVersion() {
		String url = UriBuilder.builder()
				.config(config)
				.path("/version")
				.build();

		Request request = new Request.Builder()
				.url(url)
				.get()
				.build();

		return new GotifyRequest.Builder()
				.request(request)
				.clientAuthInfoWriter(ClientAuthInfoWriterFactory.writer(config.getCredential()))
				.build()
				.execute(GotifyVersion.class);
	}
}
