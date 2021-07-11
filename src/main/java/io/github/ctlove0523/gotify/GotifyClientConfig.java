package io.github.ctlove0523.gotify;

public class GotifyClientConfig implements InnerGotifyClientConfig {
	private String scheme;
	private String host;
	private int port;
	private String userName;
	private String password;

	public GotifyClientConfig(Builder builder) {
		this.scheme = builder.scheme;
		this.host = builder.host;
		this.port = builder.port;
		this.userName = builder.userName;
		this.password = builder.password;
	}


	@Override
	public String getScheme() {
		return scheme;
	}

	@Override
	public String getHost() {
		return host;
	}

	@Override
	public int getPort() {
		return port;
	}

	@Override
	public String getUserName() {
		return userName;
	}

	@Override
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
