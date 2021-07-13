package io.github.ctlove0523.gotify;

public interface GotifyClient {
	static GotifyClient build(InnerGotifyClientConfig config) {
		return new GotifyClientImpl(config);
	}

	AppClient getAppClient();

	MessageClient getMessageClient();

	DeviceClient getDeviceClient();
}
