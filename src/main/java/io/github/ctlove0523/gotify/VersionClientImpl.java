package io.github.ctlove0523.gotify;

import io.github.ctlove0523.gotify.version.Version;
import okhttp3.Request;

class VersionClientImpl implements VersionClient {
    private final GotifyClientConfig clientConfig;

    VersionClientImpl(GotifyClientConfig clientConfig) {
        this.clientConfig = clientConfig;
    }

    @Override
    public Result<Version, ResponseError> getVersion() {
        String url = UriBuilder.builder()
                .config(clientConfig)
                .path("/version")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        return new GotifyRequest.Builder(clientConfig)
                .request(request)
                .clientAuthInfoWriter(ClientAuthInfoWriterFactory.writer(clientConfig.getCredential()))
                .build()
                .execute(Version.class);
    }
}
