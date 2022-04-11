package io.github.ctlove0523.gotify;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

class GotifyClientImpl implements GotifyClient {
    private final GotifyClientConfig clientConfig;

    private final AtomicReference<AppClient> appClientRef = new AtomicReference<>();

    private final AtomicReference<MessageClient> messageClientRef = new AtomicReference<>();

    private final AtomicReference<DeviceClient> deviceClientRef = new AtomicReference<>();

    private final AtomicReference<HealthClient> healthClientRef = new AtomicReference<>();

    private final AtomicReference<VersionClient> versionClientRef = new AtomicReference<>();

    private final AtomicReference<UserClient> userClientRef = new AtomicReference<>();

    private final AtomicReference<PluginClient> pluginClientRef = new AtomicReference<>();

    public GotifyClientImpl(GotifyClientConfig clientConfig) {
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

    @Override
    public PluginClient getPluginClient() {
        return newClient(pluginClientRef, PluginClientImpl::new);
    }

    private synchronized <T extends CloseableClient> T newClient(AtomicReference<T> reference,
                                                                 Function<GotifyClientConfig, T> factory) {
        T client = reference.get();

        if (Objects.isNull(client)) {
            client = factory.apply(clientConfig);
            reference.lazySet(client);
        }

        return client;
    }
}
