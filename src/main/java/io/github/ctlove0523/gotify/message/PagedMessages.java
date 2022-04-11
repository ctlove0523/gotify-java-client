package io.github.ctlove0523.gotify.message;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PagedMessages {
    private List<Message> messages;
    private Paging paging;
}
