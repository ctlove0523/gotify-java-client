package io.github.ctlove0523.gotify;

/**
 * @author chentong
 */
public class BasicCredential implements Credential {
    private final String userName;
    private final String password;

    BasicCredential(Builder builder) {
        this.userName = builder.userName;
        this.password = builder.password;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public static class Builder {
        private String userName;
        private String password;

        public Builder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Credential build() {
            return new BasicCredential(this);
        }
    }
}
