package io.github.ctlove0523.gotify;

import java.io.IOException;
import java.util.Base64;
import java.util.Objects;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ctlove0523.gotify.version.GotifyVersion;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

class VersionClientImpl implements VersionClient {
	private GotifyClientConfig config;

	VersionClientImpl(GotifyClientConfig config) {
		this.config = config;
	}

	@Override
	public GotifyVersion getVersion() {
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
		try (Response response = client.newCall(request).execute();
			 ResponseBody body = response.body()) {

			if (Objects.nonNull(body)) {
				String content = body.string();

				ObjectMapper mapper = new ObjectMapper();

				return mapper.readValue(content, GotifyVersion.class);
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
