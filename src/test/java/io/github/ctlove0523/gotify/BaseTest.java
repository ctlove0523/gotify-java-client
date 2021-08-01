package io.github.ctlove0523.gotify;

import java.util.Random;

public abstract class BaseTest {

	protected final Random random = new Random();

	protected GotifyClient newGotifyClient(MockerServer server) {
		Credential credential = new BasicCredential.Builder()
				.userName("admin")
				.password("admin")
				.build();

		GotifyClientConfig config = new GotifyClientConfig.Builder()
				.scheme("http")
				.host("localhost")
				.port(server.port())
				.credential(credential)
				.build();

		return GotifyClient.build(config);
	}
}
