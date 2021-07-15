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

	public static GotifyRequest builder() {
		return new GotifyRequest();
	}

	public GotifyRequest client(OkHttpClient client) {
		this.client = client;
		return this;
	}

	public GotifyRequest request(Request request) {
		this.request = request;
		return this;
	}


	public <T, E> Result<T, GotifyResponseError> execute(Class<T> clazz) {
		try (Response response = client.newCall(request).execute();
			 ResponseBody responseBody = response.body()) {
			if (responseBody != null) {
				String content = responseBody.string();

				if (response.isSuccessful()) {
					return GotifyResult.ok(JacksonUtil.string2Object(content, clazz));
				}
				else {
					return GotifyResult.error(JacksonUtil.string2Object(content, GotifyResponseError.class));
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

	public <T, E> Result<List<T>, GotifyResponseError> executeReturnList(Class<T> clazz) {
		try (Response response = client.newCall(request).execute();
			 ResponseBody responseBody = response.body()) {
			String content = responseBody.string();

			if (response.isSuccessful()) {
				ObjectMapper mapper = new ObjectMapper();
				CollectionLikeType arrayType = mapper.getTypeFactory().constructCollectionLikeType(List.class, clazz);
				return GotifyResult.ok(mapper.readValue(content, arrayType));
			}
			else {
				return GotifyResult.error(JacksonUtil.string2Object(content, GotifyResponseError.class));
			}
		}
		catch (IOException e) {

			return GotifyResult.exception(e);
		}

	}
}
