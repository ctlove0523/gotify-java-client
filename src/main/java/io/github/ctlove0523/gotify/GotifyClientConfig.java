package io.github.ctlove0523.gotify;

public class GotifyClientConfig {
    private final String endpoint;
    private final Credential credential;
    private final String messagePath;
    private final KeyStoreProvider keyStoreProvider;

    GotifyClientConfig(Builder builder) {
        this.credential = builder.credential;
        this.endpoint = builder.endpoint;
        this.messagePath = builder.messagePath;
        this.keyStoreProvider = builder.keyStoreProvider;
    }

    String getEndpoint() {
        return endpoint;
    }

    Credential getCredential() {
        return credential;
    }

    String getMessagePath() {
        return messagePath;
    }

    KeyStoreProvider getKeyStoreProvider() {
        return this.keyStoreProvider;
    }

    public static class Builder {
        private Credential credential;
        private String endpoint;
        private String messagePath = "/stream";
        private KeyStoreProvider keyStoreProvider;

        public Builder builder() {
            return new Builder();
        }

        public Builder credential(Credential credential) {
            this.credential = credential;
            return this;
        }

        public Builder endpoint(String endpoint) {
            this.endpoint = endpoint;
            return this;
        }

        /**
         * set web socket path used to receive messages
         *
         * @param messagePath web socket path
         * @return {@see Builder}
         * @since 0.0.2
         */
        public Builder messagePath(String messagePath) {
            this.messagePath = messagePath;
            return this;
        }

        public Builder keyStoreProvider(KeyStoreProvider keyStoreProvider) {
            this.keyStoreProvider = keyStoreProvider;
            return this;
        }

        public GotifyClientConfig build() {
            return new GotifyClientConfig(this);
        }

    }

}
