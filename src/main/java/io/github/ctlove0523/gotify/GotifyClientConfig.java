package io.github.ctlove0523.gotify;

public class GotifyClientConfig {
	private final String endpoint;
	private final Credential credential;

	GotifyClientConfig(Builder builder) {
		this.credential = builder.credential;
		this.endpoint = builder.endpoint;
	}

	public String getEndpoint() {
		return endpoint;
	}

	Credential getCredential() {
		return credential;
	}

	public static class Builder {
		private Credential credential;
		private String endpoint;

		public Builder builder() {
			return new Builder();
		}

		public Builder credential(Credential credential) {
			this.credential = credential;
			return this;
		}

		public Builder endpoint(String endpoint) {
			this.endpoint = endpoint;
			return this;
		}

		public GotifyClientConfig build() {
			return new GotifyClientConfig(this);
		}

	}

}
