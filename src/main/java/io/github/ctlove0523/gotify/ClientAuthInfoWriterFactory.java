package io.github.ctlove0523.gotify;

class ClientAuthInfoWriterFactory {

    public static ClientAuthInfoWriter writer(Credential credential) {
        return new BasicClientAuthInfoWriter(credential);
    }
}
