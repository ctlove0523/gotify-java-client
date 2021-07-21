package io.github.ctlove0523.gotify;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class GotifyRequest {
	private OkHttpClient client;

	private Request request;

	GotifyRequest(Builder builder) {
		this.client = builder.client;
		this.request = builder.request;
	}

	Request getRequest() {
		return request;
	}

	public Builder newBuilder() {
		return new Builder(this);
	}

	public <T, E> Result<T, ResponseError> execute(Class<T> clazz) {
		try (Response response = client.newCall(request).execute();
			 ResponseBody responseBody = response.body()) {
			if (responseBody != null) {
				String content = responseBody.string();
				if (!content.isEmpty()) {
					if (response.isSuccessful()) {
						return GotifyResult.ok(JacksonUtil.string2Object(content, clazz));
					}
					else {
						return GotifyResult.error(JacksonUtil.string2Object(content, ResponseError.class));
					}
				} else {
					return GotifyResult.ok(clazz.cast(response.isSuccessful()));
				}
			}
			else {
				if (response.isSuccessful()) {
					return GotifyResult.ok(clazz.cast(true));
				}
				else {
					return GotifyResult.ok(clazz.cast(false));
				}

			}
		}
		catch (IOException e) {

			return GotifyResult.exception(e);
		}

	}

	public <T, E> Result<List<T>, ResponseError> executeReturnList(Class<T> clazz) {
		try (Response response = client.newCall(request).execute();
			 ResponseBody responseBody = response.body()) {
			String content = responseBody.string();

			if (response.isSuccessful()) {
				ObjectMapper mapper = new ObjectMapper();
				CollectionLikeType arrayType = mapper.getTypeFactory().constructCollectionLikeType(List.class, clazz);
				return GotifyResult.ok(mapper.readValue(content, arrayType));
			}
			else {
				return GotifyResult.error(JacksonUtil.string2Object(content, ResponseError.class));
			}
		}
		catch (IOException e) {

			return GotifyResult.exception(e);
		}

	}

	public static class Builder {
		private OkHttpClient client;

		private Request request;

		private ClientAuthInfoWriter writer;

		public Builder() {

		}

		public Builder(GotifyRequest request) {
			this.client = request.client;
			this.request = request.request;
		}

		public Builder request(Request request) {
			this.request = request;
			return this;
		}

		public Builder clientAuthInfoWriter(ClientAuthInfoWriter writer) {
			this.writer = writer;
			return this;
		}

		public GotifyRequest build() {
			if (client == null) {
				this.client = HttpClientFactory.getHttpClient();
			}

			GotifyRequest request = new GotifyRequest(this);
			if (writer != null) {
				return writer.authenticateRequest(request);
			}

			return request;
		}

	}
}
