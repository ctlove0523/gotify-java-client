package io.github.ctlove0523.gotify;

import java.util.Random;

import org.junit.After;
import org.junit.Before;

public abstract class BaseTest {

	protected final Random random = new Random();

	@Before
	public abstract void startServer() throws Exception;

	@After
	public abstract void stopServer() throws Exception;

	protected GotifyClient newGotifyClient(MockerServer server) {
		Credential credential = new BasicCredential.Builder()
				.userName("admin")
				.password("admin")
				.build();

		GotifyClientConfig config = new GotifyClientConfig.Builder()
				.endpoint("http://localhost:" + server.port())
				.credential(credential)
				.build();

		return GotifyClient.build(config);
	}
}
