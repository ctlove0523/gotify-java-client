package io.github.ctlove0523.gotify;

import io.github.ctlove0523.gotify.message.Message;
import io.github.ctlove0523.gotify.message.PagedMessages;

public interface MessageClient extends CloseableClient {
	Result<PagedMessages, GotifyResponseError> getAppMessages(Integer appId, Integer limit, Integer since);

	Result<PagedMessages, GotifyResponseError> getAppMessages(Integer appId, Integer limit);

	Result<PagedMessages, GotifyResponseError> getAppMessages(Integer appId);

	Result<Boolean, GotifyResponseError> deleteAppMessages(Integer appId);

	Result<PagedMessages, GotifyResponseError> getMessages();

	Result<PagedMessages, GotifyResponseError> getMessages(Integer limit);

	Result<PagedMessages, GotifyResponseError> getMessages(Integer limit, Integer since);

	Result<Message, GotifyResponseError> createMessage(Integer appId, Message message);

	Result<Boolean, GotifyResponseError> deleteMessages();

	Result<Boolean, GotifyResponseError> deleteMessage(Integer id);

	void registerMessageHandler(MessageHandler handler);
}
