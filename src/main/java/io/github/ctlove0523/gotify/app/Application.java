package io.github.ctlove0523.gotify.app;

import lombok.Getter;
import lombok.Setter;

/**
 * @author chentong
 */
@Getter
@Setter
public class Application {
	private Integer id;
	private String name;
	private String description;
	private String image;
	private String token;
	private Boolean internal;
}
