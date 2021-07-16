package io.github.ctlove0523.gotify;

import okhttp3.OkHttpClient;

class HttpClientFactory {
	private static OkHttpClient client;

	synchronized public static OkHttpClient getHttpClient() {
		if (client == null) {
			client = new OkHttpClient.Builder()
					.build();
		}

		return client;
	}
}
