package io.github.ctlove0523.gotify;

import io.github.ctlove0523.gotify.version.Version;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class VersionClientTests extends BaseTest {
	private final VersionMockerServer server = new VersionMockerServer();

	@Before
	public void startServer() throws Exception {
		server.start();
	}

	@After
	public void stopServer() throws Exception {
		server.stop();
	}

	@Test
	public void test_getVersion() {

		VersionClient client = newGotifyClient(server).getVersionClient();

		Result<Version, ResponseError> result = client.getVersion();

		Assert.assertTrue(result.isSuccessful());

		Version version = result.result();
		Assert.assertNotNull(version);
		Assert.assertEquals("0.0.1", version.getVersion());
		Assert.assertEquals("2021-08-01", version.getBuildDate());
		Assert.assertEquals("commit", version.getCommit());
	}
}
