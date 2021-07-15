package io.github.ctlove0523.gotify;

import java.util.List;

import io.github.ctlove0523.gotify.app.Application;
import io.github.ctlove0523.gotify.app.CreateApplicationRequest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AppClientTest {
	private ApplicationMockServer server = new ApplicationMockServer();

	@Before
	public void starServer() throws Exception {
		server.start();
	}

	@After
	public void stopServer() throws Exception {
		server.stop();
	}

	@Test
	public void test_getApplications_Success() {
		Application application = new Application();
		application.setId(1);
		application.setName("first app");
		application.setInternal(true);

		server.addApplication(application);

		GotifyClientConfig config = new GotifyClientConfig.Builder()
				.scheme("http")
				.host("localhost")
				.port(server.port())
				.build();
		GotifyClient gotifyClient = GotifyClient.build(config);

		AppClient appClient = gotifyClient.getAppClient();

		Result<List<Application>, GotifyResponseError> result = appClient.getApplications();

		Assert.assertTrue(result.isSuccessful());
		List<Application> apps = result.result();
		Assert.assertEquals(1, apps.size());
	}

	@Test
	public void test_createApplication_Success() {
		CreateApplicationRequest request = new CreateApplicationRequest();
		request.setId(1);
		request.setDescription("test create application");
		request.setName("app");
		request.setInternal(true);
		request.setImage("image");

		GotifyClientConfig config = new GotifyClientConfig.Builder()
				.scheme("http")
				.host("localhost")
				.port(server.port())
				.build();
		GotifyClient gotifyClient = GotifyClient.build(config);

		AppClient appClient = gotifyClient.getAppClient();

		Result<Application,GotifyResponseError> result = appClient.createApplication(request);

		Assert.assertTrue(result.isSuccessful());

		Application application = result.result();

		Assert.assertNotNull(application);

		Assert.assertEquals(request.getId(), application.getId());
		Assert.assertEquals(request.getDescription(), application.getDescription());
		Assert.assertEquals(request.getName(), application.getName());
		Assert.assertEquals(request.getInternal(), application.getInternal());
		Assert.assertEquals(request.getImage(), application.getImage());
	}
}
