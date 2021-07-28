package io.github.ctlove0523.gotify;

import java.util.Random;

import io.github.ctlove0523.gotify.message.Message;
import io.github.ctlove0523.gotify.message.PagedMessages;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MessageClientTest {

	private MessageMockServer server = new MessageMockServer();

	private Random random = new Random();

	@Before
	public void startServer() throws Exception {
		server.start();
	}

	@After
	public void stopServer() throws Exception {
		server.stop();
	}

	@Test
	public void test_getAppMessages() {
		Integer appId = random.nextInt();

		Message message1 = new Message();
		message1.setAppid(appId);
		message1.setMessage("message1");
		message1.setTitle("title1");
		message1.setId(random.nextInt());
		message1.setPriority(random.nextInt());

		Message message2 = new Message();
		message2.setAppid(appId);
		message2.setMessage("message1");
		message2.setTitle("title1");
		message2.setId(random.nextInt());
		message2.setPriority(random.nextInt());

		server.addMessage(message1);
		server.addMessage(message2);


		MessageClient messageClient = newGotifyClient().getMessageClient();
		Result<PagedMessages,ResponseError> response = messageClient.getAppMessages(appId);

		Assert.assertNotNull(response);
		Assert.assertTrue(response.isSuccessful());

		PagedMessages pagedMessages = response.result();
		Assert.assertNotNull(pagedMessages);
		Assert.assertEquals(2,pagedMessages.getMessages().size());
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
