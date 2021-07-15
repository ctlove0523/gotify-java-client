package io.github.ctlove0523.gotify;

import java.util.Base64;

import io.github.ctlove0523.gotify.version.GotifyVersion;
import okhttp3.OkHttpClient;
import okhttp3.Request;

class VersionClientImpl implements VersionClient {
	private final GotifyClientConfig config;

	VersionClientImpl(GotifyClientConfig config) {
		this.config = config;
	}

	@Override
	public Result<GotifyVersion, GotifyResponseError> getVersion() {
		String authInfo = config.getUserName() + ":" + config.getPassword();
		String authorization = Base64.getEncoder().encodeToString(authInfo.getBytes());

		OkHttpClient client = new OkHttpClient.Builder()
				.build();

		String url = UriBuilder.builder()
				.config(config)
				.path("/version")
				.build();

		Request request = new Request.Builder()
				.url(url)
				.header("Authorization", "Basic " + authorization)
				.get()
				.build();

		return GotifyRequest.builder().client(client).request(request).execute(GotifyVersion.class);
	}

	@Override
	public void close() {

	}
}
