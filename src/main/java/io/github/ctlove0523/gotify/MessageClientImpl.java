package io.github.ctlove0523.gotify;

import io.github.ctlove0523.gotify.message.Message;

class MessageClientImpl implements MessageClient {
	private InnerGotifyClientConfig clientConfig;

	public MessageClientImpl(InnerGotifyClientConfig clientConfig) {
		this.clientConfig = clientConfig;
	}

	@Override
	public void close() {

	}

	@Override
	public Iterable<Message> listMessageOfApplication(String appId) {
		return null;
	}

	@Override
	public boolean deleteOneMessage(String id) {
		return false;
	}
}
