package io.github.ctlove0523.gotify;

import java.io.IOException;
import java.util.Base64;
import java.util.Objects;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ctlove0523.gotify.health.Health;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

class HealthClientImpl implements HealthClient {
	private GotifyClientConfig config;

	public HealthClientImpl(GotifyClientConfig config) {
		this.config = config;
	}

	@Override
	public Health getHealth() {
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
		try (Response response = client.newCall(request).execute();
			 ResponseBody body = response.body()) {

			if (Objects.nonNull(body)) {
				String content = body.string();

				ObjectMapper mapper = new ObjectMapper();

				return mapper.readValue(content, Health.class);
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
