package io.github.ctlove0523.gotify;

import java.util.List;
import java.util.Random;

import io.github.ctlove0523.gotify.message.Message;
import io.github.ctlove0523.gotify.message.PagedMessages;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MessageClientTest {

	private final MessageMockServer server = new MessageMockServer();

	private final Random random = new Random();

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
		Result<PagedMessages, ResponseError> response = messageClient.getAppMessages(appId);

		Assert.assertNotNull(response);
		Assert.assertTrue(response.isSuccessful());

		PagedMessages pagedMessages = response.result();
		Assert.assertNotNull(pagedMessages);
		Assert.assertEquals(2, pagedMessages.getMessages().size());
	}

	@Test
	public void test_deleteAppMessages() {
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

		Result<Boolean, ResponseError> result = messageClient.deleteAppMessages(appId);
		Assert.assertTrue(result.isSuccessful());

		List<Message> messages = server.getAppMessage(appId);
		Assert.assertTrue(messages.isEmpty());
	}

	@Test
	public void test_getMessages() {
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

		Result<PagedMessages, ResponseError> result = messageClient.getMessages();
		Assert.assertTrue(result.isSuccessful());

		List<Message> messages = result.result().getMessages();
		Assert.assertEquals(2, messages.size());
	}

	@Test
	public void test_deleteMessages() {
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

		List<Message> messages = server.getMessages();
		Assert.assertEquals(2, messages.size());

		MessageClient messageClient = newGotifyClient().getMessageClient();

		Result<Boolean, ResponseError> result = messageClient.deleteMessages();
		Assert.assertTrue(result.isSuccessful());

		messages = server.getMessages();
		Assert.assertTrue(messages.isEmpty());

	}

	@Test
	public void test_deleteMessage() {
		Integer appId = random.nextInt();

		Integer messageId1 = random.nextInt();
		Message message1 = new Message();
		message1.setAppid(appId);
		message1.setMessage("message1");
		message1.setTitle("title1");
		message1.setId(messageId1);
		message1.setPriority(random.nextInt());

		Integer messageId2 = random.nextInt();
		Message message2 = new Message();
		message2.setAppid(appId);
		message2.setMessage("message1");
		message2.setTitle("title1");
		message2.setId(messageId2);
		message2.setPriority(random.nextInt());

		server.addMessage(message1);
		server.addMessage(message2);

		List<Message> messages = server.getMessages();
		Assert.assertEquals(2, messages.size());

		MessageClient messageClient = newGotifyClient().getMessageClient();

		Result<Boolean, ResponseError> result = messageClient.deleteMessage(messageId1);
		Assert.assertTrue(result.isSuccessful());

		messages = server.getMessages();
		Assert.assertEquals(1, messages.size());

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
