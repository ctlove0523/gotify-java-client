package io.github.ctlove0523.gotify;

import io.github.ctlove0523.gotify.message.Message;
import io.github.ctlove0523.gotify.message.PagedMessages;

/**
 * @author chentong
 */
public interface MessageClient extends CloseableClient {
	Result<PagedMessages, ResponseError> getAppMessages(Integer appId, Integer limit, Integer since);

	Result<PagedMessages, ResponseError> getAppMessages(Integer appId, Integer limit);

	Result<PagedMessages, ResponseError> getAppMessages(Integer appId);

	Result<Boolean, ResponseError> deleteAppMessages(Integer appId);

	Result<PagedMessages, ResponseError> getMessages();

	Result<PagedMessages, ResponseError> getMessages(Integer limit);

	Result<PagedMessages, ResponseError> getMessages(Integer limit, Integer since);

	Result<Message, ResponseError> createMessage(Integer appId, Message message);

	Result<Boolean, ResponseError> deleteMessages();

	Result<Boolean, ResponseError> deleteMessage(Integer id);

	void registerMessageHandler(MessageHandler handler);
}
