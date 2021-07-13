package io.github.ctlove0523.gotify;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ctlove0523.gotify.health.Health;
import io.github.ctlove0523.gotify.version.GotifyVersion;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

class VersionClientImpl implements VersionClient {
	private InnerGotifyClientConfig config;

	VersionClientImpl(InnerGotifyClientConfig config) {
		this.config = config;
	}

	@Override
	public GotifyVersion getVersion() {
		String authInfo = config.getUserName() + ":" + config.getPassword();
		String authorization = Base64.getEncoder().encodeToString(authInfo.getBytes());

		OkHttpClient client = new OkHttpClient.Builder()
				.build();
		Request request = new Request.Builder()
				.url(buildUri("/version", null))
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

	private String buildUri(String path, Map<String, Object> pathParams) {
		StringBuilder sb = new StringBuilder();
		sb.append(config.getScheme());
		sb.append("://");
		sb.append(config.getHost());
		sb.append(":");
		sb.append(config.getPort());

		if (pathParams == null || pathParams.isEmpty()) {
			sb.append(path);
		}
		else {
			for (Map.Entry<String, Object> entry : pathParams.entrySet()) {
				path = path.replace("{" + entry.getKey() + "}", entry.getValue().toString());
			}
		}

		return sb.toString();
	}

	@Override
	public void close() {

	}
}
