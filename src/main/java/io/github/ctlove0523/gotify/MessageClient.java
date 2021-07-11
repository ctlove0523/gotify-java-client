package io.github.ctlove0523.gotify;

import io.github.ctlove0523.gotify.message.Message;

public interface MessageClient extends CloseableClient {
	Iterable<Message> listMessageOfApplication(String appId);

	boolean deleteOneMessage(String id);
}
