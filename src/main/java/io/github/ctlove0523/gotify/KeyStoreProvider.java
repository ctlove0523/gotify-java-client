package io.github.ctlove0523.gotify;

import java.security.KeyStore;

public interface KeyStoreProvider {

    KeyStore getTrustStore();
}
