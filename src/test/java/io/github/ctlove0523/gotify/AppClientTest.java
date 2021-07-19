package io.github.ctlove0523.gotify;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;

import io.github.ctlove0523.gotify.app.Application;
import io.github.ctlove0523.gotify.app.CreateApplicationRequest;
import io.github.ctlove0523.gotify.app.UpdateApplicationRequest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AppClientTest {
	private ApplicationMockServer server = new ApplicationMockServer();
	private Random random = new Random();

	@Before
	public void starServer() throws Exception {
		server.start();
	}

	@After
	public void stopServer() throws Exception {
		server.stop();
	}

	@Test
	public void test_getApplications_success() {
		Application application = new Application();
		application.setId(1);
		application.setName("first app");
		application.setInternal(true);

		server.addApplication(application);

		GotifyClient gotifyClient = newGotifyClient();

		AppClient appClient = gotifyClient.getAppClient();

		Result<List<Application>, GotifyResponseError> result = appClient.getApplications();

		Assert.assertTrue(result.isSuccessful());
		List<Application> apps = result.result();
		Assert.assertEquals(1, apps.size());
	}

	@Test
	public void test_createApplication_success() {
		CreateApplicationRequest request = new CreateApplicationRequest();
		request.setId(1);
		request.setDescription("test create application");
		request.setName("app");
		request.setInternal(true);
		request.setImage("image");

		GotifyClient gotifyClient = newGotifyClient();

		AppClient appClient = gotifyClient.getAppClient();

		Result<Application, GotifyResponseError> result = appClient.createApplication(request);

		Assert.assertTrue(result.isSuccessful());

		Application application = result.result();

		Assert.assertNotNull(application);

		Assert.assertEquals(request.getId(), application.getId());
		Assert.assertEquals(request.getDescription(), application.getDescription());
		Assert.assertEquals(request.getName(), application.getName());
		Assert.assertEquals(request.getInternal(), application.getInternal());
		Assert.assertEquals(request.getImage(), application.getImage());
	}

	@Test
	public void test_updateApplication_success() {
		int id = random.nextInt();
		Application application = new Application();
		application.setId(id);
		application.setName("first app");
		application.setInternal(true);

		server.addApplication(application);

		UpdateApplicationRequest request = new UpdateApplicationRequest();
		request.setName("updated name");
		request.setDescription("new description");

		AppClient appClient = newGotifyClient().getAppClient();
		Result<Application, GotifyResponseError> result = appClient.updateApplication(id, request);

		Assert.assertTrue(result.isSuccessful());

		Application app = result.result();
		Assert.assertEquals((int) app.getId(), id);
		Assert.assertEquals(app.getName(), request.getName());
		Assert.assertEquals(app.getDescription(), request.getDescription());
	}

	@Test
	public void test_deleteApplication_success() {
		int id = random.nextInt();
		Application application = new Application();
		application.setId(id);
		application.setName("first app");
		application.setInternal(true);

		server.addApplication(application);

		AppClient appClient = newGotifyClient().getAppClient();
		Result<Boolean, GotifyResponseError> result = appClient.deleteApplication(id);

		Assert.assertTrue(result.isSuccessful());

		result = appClient.deleteApplication(random.nextInt());

		Assert.assertFalse(result.isSuccessful());
	}

	@Test
	public void test_uploadApplicationImage_success() {
		int id = random.nextInt();
		Application application = new Application();
		application.setId(id);
		application.setName("first app");
		application.setInternal(true);

		server.addApplication(application);

		AppClient appClient = newGotifyClient().getAppClient();

		byte[] image = "image".getBytes(StandardCharsets.UTF_8);
		Result<Application, GotifyResponseError> result = appClient.uploadApplicationImage(id, image);

		Assert.assertTrue(result.isSuccessful());

		Assert.assertTrue(result.result().getImage().contains("image"));

		result = appClient.uploadApplicationImage(random.nextInt(), image);

		Assert.assertFalse(result.isSuccessful());
	}

	private GotifyClient newGotifyClient() {
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
