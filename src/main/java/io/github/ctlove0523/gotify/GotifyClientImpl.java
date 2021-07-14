package io.github.ctlove0523.gotify;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

class GotifyClientImpl implements GotifyClient {
	private InnerGotifyClientConfig clientConfig;

	private AtomicReference<AppClient> appClientRef = new AtomicReference<>();

	private AtomicReference<MessageClient> messageClientRef = new AtomicReference<>();

	private AtomicReference<DeviceClient> deviceClientRef = new AtomicReference<>();

	private AtomicReference<HealthClient> healthClientRef = new AtomicReference<>();

	private AtomicReference<VersionClient> versionClientRef = new AtomicReference<>();

	private AtomicReference<UserClient> userClientRef = new AtomicReference<>();

	public GotifyClientImpl(InnerGotifyClientConfig clientConfig) {
		this.clientConfig = clientConfig;
	}

	@Override
	public AppClient getAppClient() {
		return newClient(appClientRef, AppClientImpl::new);
	}

	@Override
	public MessageClient getMessageClient() {
		return newClient(messageClientRef, MessageClientImpl::new);
	}

	@Override
	public DeviceClient getDeviceClient() {
		return newClient(deviceClientRef, DeviceClientImpl::new);
	}

	@Override
	public HealthClient getHealthClient() {
		return newClient(healthClientRef, HealthClientImpl::new);
	}

	@Override
	public VersionClient getVersionClient() {
		return newClient(versionClientRef, VersionClientImpl::new);
	}

	@Override
	public UserClient getUserClient() {
		return newClient(userClientRef, UserClientImpl::new);
	}

	private synchronized <T extends CloseableClient> T newClient(AtomicReference<T> reference,
			Function<InnerGotifyClientConfig, T> factory) {
		T client = reference.get();

		if (Objects.isNull(client)) {
			client = factory.apply(clientConfig);
			reference.lazySet(client);
		}

		return client;
	}
}
