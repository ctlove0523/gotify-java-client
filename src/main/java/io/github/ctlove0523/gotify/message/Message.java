package io.github.ctlove0523.gotify.message;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {
    private Integer id;
    private Integer appid;
    private String date;
    private Object extras;
    private String message;
    private Integer priority;
    private String title;
}
