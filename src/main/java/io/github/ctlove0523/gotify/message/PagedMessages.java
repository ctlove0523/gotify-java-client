package io.github.ctlove0523.gotify.message;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagedMessages {
	private List<Message> messages;
	private Paging paging;
}
