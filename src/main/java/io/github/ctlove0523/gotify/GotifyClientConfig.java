package io.github.ctlove0523.gotify;

public class GotifyClientConfig {
	private final String scheme;
	private final String host;
	private final int port;
	private final String userName;
	private final String password;

	public GotifyClientConfig(Builder builder) {
		this.scheme = builder.scheme;
		this.host = builder.host;
		this.port = builder.port;
		this.userName = builder.userName;
		this.password = builder.password;
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

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}


	public static class Builder {
		private String scheme;
		private String host;
		private int port;
		private String userName;
		private String password;

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

		public Builder userName(String userName) {
			this.userName = userName;
			return this;
		}

		public Builder password(String password) {
			this.password = password;
			return this;
		}

		public GotifyClientConfig build() {
			return new GotifyClientConfig(this);
		}

	}

}
