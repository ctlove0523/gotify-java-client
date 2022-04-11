package io.github.ctlove0523.gotify;

import io.github.ctlove0523.gotify.message.Message;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.function.Consumer;

public class Demo {
    public static void main(String[] args) {
        GotifyClientConfig config = new GotifyClientConfig.Builder()
                .endpoint("https://localhost:443")
                .keyStoreProvider(new KeyStoreProvider() {
                    @Override
                    public KeyStore getTrustStore() {
                        File file = new File("D:\\codes\\GitHub\\certs\\server\\truststore.jks");
                        KeyStore trustStore = null;
                        try {
                            trustStore = KeyStore.getInstance("JKS");
                            trustStore.load(new ByteArrayInputStream(Files.readAllBytes(file.toPath())), "serverTRUST@2022".toCharArray());
                        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
                            e.printStackTrace();
                        }

                        return trustStore;
                    }
                })
                .credential(new Credential() {
                    @Override
                    public String getUserName() {
                        return "admin";
                    }

                    @Override
                    public String getPassword() {
                        return "admin";
                    }
                })
                .build();
        GotifyClient gotifyClient = GotifyClient.build(config);

        MessageClient messageClient = gotifyClient.getMessageClient();
        messageClient.getAppMessages(9).result().getMessages()
                .forEach(new Consumer<Message>() {
                    @Override
                    public void accept(Message message) {
                        System.out.println(message);
                    }
                });

        Message message = new Message();
        message.setMessage("hello https");
        Result<Message, ResponseError> res = messageClient.createMessage(9, message);
        System.out.println(res.isSuccessful());
    }
}
