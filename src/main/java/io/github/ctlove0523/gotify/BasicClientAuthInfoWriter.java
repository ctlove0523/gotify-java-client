package io.github.ctlove0523.gotify;

import java.util.Base64;

import okhttp3.Request;

class BasicClientAuthInfoWriter implements ClientAuthInfoWriter {
	private Credential credential;

	BasicClientAuthInfoWriter(Credential credential) {
		this.credential = credential;
	}

	@Override
	public GotifyRequest authenticateRequest(GotifyRequest gotifyRequest) {
		String authInfo = credential.getUserName() + ":" + credential.getPassword();
		String authorization = Base64.getEncoder().encodeToString(authInfo.getBytes());
		Request request = gotifyRequest.getRequest().newBuilder()
				.header("Authorization", "Basic " + authorization)
				.build();

		return gotifyRequest.newBuilder().request(request).build();
	}
}
