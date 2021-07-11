package io.github.ctlove0523.gotify;

interface InnerGotifyClientConfig {
	default String getScheme() {
		return "http";
	}

	String getHost();

	int getPort();

	String getUserName();

	String getPassword();
}
