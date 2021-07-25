package io.github.ctlove0523.gotify.plugin;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @author chentong
 */
@Getter
@Setter
public class PluginConf {
	private String author;

	private List<String> capabilities;

	private boolean enabled;

	private int id;

	private String license;

	private String modulePath;

	private String name;

	private String token;

	private String website;
}
