package io.github.ctlove0523.gotify;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import io.github.ctlove0523.gotify.message.Message;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class GotifyClientWebSocketClient extends WebSocketClient {
	private static final Logger log = LoggerFactory.getLogger(GotifyClientWebSocketClient.class);
	private final List<MessageHandler> handlers = new ArrayList<>();

	public GotifyClientWebSocketClient(URI serverUri) {
		super(serverUri);
	}

	public void addHandler(MessageHandler handler) {
		this.handlers.add(handler);
	}

	@Override
	public void onOpen(ServerHandshake serverHandshake) {
		log.debug("web socket open {},{}", serverHandshake.getHttpStatus(), serverHandshake.getHttpStatusMessage());
	}

	@Override
	public void onMessage(String s) {
		Message message = JacksonUtil.string2Object(s, Message.class);
		handlers.forEach(new Consumer<MessageHandler>() {
			@Override
			public void accept(MessageHandler handler) {
				handler.handle(message);
			}
		});
	}

	@Override
	public void onClose(int i, String s, boolean b) {
		log.debug("web socket close");
	}

	@Override
	public void onError(Exception e) {
		log.warn("web socket error ", e);
	}
}
