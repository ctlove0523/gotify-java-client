package io.github.ctlove0523.gotify;

/**
 * @author chentong
 */
public class GotifyClientBuilder {
    private String endpoint;

    private String userName;

    private String password;

    public GotifyClientBuilder endpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    public GotifyClientBuilder userName(String userName) {
        this.userName = userName;
        return this;
    }

    public GotifyClientBuilder password(String password) {
        this.password = password;
        return this;
    }

    public GotifyClient build() {
        GotifyClientConfig config = new GotifyClientConfig.Builder()
                .credential(new Credential() {
                    @Override
                    public String getUserName() {
                        return userName;
                    }

                    @Override
                    public String getPassword() {
                        return password;
                    }
                })
                .endpoint(this.endpoint)
                .build();
        return new GotifyClientImpl(config);
    }
}
