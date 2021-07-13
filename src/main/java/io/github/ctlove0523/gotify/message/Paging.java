package io.github.ctlove0523.gotify.message;

import lombok.Getter;
import lombok.Setter;

/**
 * @author chentong
 */
@Getter
@Setter
public class Paging {
	private Integer limit;
	private String next;
	private Integer since;
	private Integer size;
}
