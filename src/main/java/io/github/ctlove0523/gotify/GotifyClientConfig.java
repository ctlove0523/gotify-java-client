package io.github.ctlove0523.gotify;

public class GotifyClientConfig {
	private final String scheme;
	private final String host;
	private final int port;
	private final Credential credential;

	GotifyClientConfig(Builder builder) {
		this.scheme = builder.scheme;
		this.host = builder.host;
		this.port = builder.port;
		this.credential = builder.credential;
	}


	public String getScheme() {
		return scheme;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	Credential getCredential() {
		return credential;
	}

	public static class Builder {
		private String scheme;
		private String host;
		private int port;
		private Credential credential;

		public Builder builder() {
			return new Builder();
		}

		public Builder scheme(String scheme) {
			this.scheme = scheme;
			return this;
		}

		public Builder host(String host) {
			this.host = host;
			return this;
		}

		public Builder port(int port) {
			this.port = port;
			return this;
		}

		public Builder credential(Credential credential) {
			this.credential = credential;
			return this;
		}

		public GotifyClientConfig build() {
			return new GotifyClientConfig(this);
		}

	}

}
