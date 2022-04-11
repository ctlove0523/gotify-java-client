package io.github.ctlove0523.gotify;

import io.github.ctlove0523.gotify.message.Message;

public interface MessageHandler {

    void handle(Message message);
}
