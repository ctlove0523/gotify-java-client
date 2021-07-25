package io.github.ctlove0523.gotify;

public interface GotifyClient {
	static GotifyClient build(GotifyClientConfig config) {
		return new GotifyClientImpl(config);
	}

	AppClient getAppClient();

	MessageClient getMessageClient();

	DeviceClient getDeviceClient();

	HealthClient getHealthClient();

	VersionClient getVersionClient();

	UserClient getUserClient();

	PluginClient getPluginClient();
}
