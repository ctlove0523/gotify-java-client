package io.github.ctlove0523.gotify;

import io.github.ctlove0523.gotify.message.Message;
import io.github.ctlove0523.gotify.message.PagedMessages;

public interface MessageClient extends CloseableClient {
	PagedMessages getAppMessages(Integer appId, Integer limit, Integer since);

	PagedMessages getAppMessages(Integer appId, Integer limit);

	PagedMessages getAppMessages(Integer appId);

	Boolean deleteAppMessages(Integer appId);

	PagedMessages getMessages();

	PagedMessages getMessages(Integer limit);

	PagedMessages getMessages(Integer limit, Integer since);

	Message createMessage(Integer appId, Message message);

	Boolean deleteMessages();

	Boolean deleteMessage(Integer id);

	void registerMessageHandler(MessageHandler handler);
}
