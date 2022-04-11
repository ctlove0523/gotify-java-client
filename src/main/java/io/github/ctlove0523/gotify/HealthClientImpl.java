package io.github.ctlove0523.gotify;

import io.github.ctlove0523.gotify.health.Health;
import okhttp3.Request;

class HealthClientImpl implements HealthClient {
    private final GotifyClientConfig clientConfig;

    public HealthClientImpl(GotifyClientConfig clientConfig) {
        this.clientConfig = clientConfig;
    }

    @Override
    public Result<Health, ResponseError> getHealth() {
        String url = UriBuilder.builder()
                .config(clientConfig)
                .path("/health")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        return new GotifyRequest.Builder(clientConfig)
                .request(request)
                .clientAuthInfoWriter(ClientAuthInfoWriterFactory.writer(clientConfig.getCredential()))
                .build()
                .execute(Health.class);
    }
}
