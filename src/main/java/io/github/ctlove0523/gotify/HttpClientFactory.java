package io.github.ctlove0523.gotify;

import okhttp3.OkHttpClient;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Objects;

class HttpClientFactory {
    private static OkHttpClient client;

    synchronized public static OkHttpClient getHttpClient(GotifyClientConfig clientConfig) {
        if (client == null) {
            client = new OkHttpClient.Builder()
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String s, SSLSession sslSession) {
                            return true;
                        }
                    })
                    .build();
            KeyStoreProvider provider = clientConfig.getKeyStoreProvider();
            if (Objects.nonNull(provider)) {
                KeyStore trustStore = provider.getTrustStore();
                X509TrustManager x509TrustManager = createX509TrustManager(trustStore);
                SSLContext sslContext = createSSLContext(trustStore);
                Objects.requireNonNull(x509TrustManager, "x509TrustManager");
                Objects.requireNonNull(sslContext, "sslContext");
                client = client.newBuilder()
                        .sslSocketFactory(sslContext.getSocketFactory(), x509TrustManager)
                        .build();
            }
        }

        return client;
    }

    private static X509TrustManager createX509TrustManager(KeyStore trustStore) {
        try {
            TrustManagerFactory factory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            factory.init(trustStore);
            return (X509TrustManager) factory.getTrustManagers()[0];
        } catch (NoSuchAlgorithmException | KeyStoreException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static SSLContext createSSLContext(KeyStore trustStore) {
        try {
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(trustStore);


            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());

            return sslContext;
        } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
            e.printStackTrace();
        }

        return null;
    }
}
