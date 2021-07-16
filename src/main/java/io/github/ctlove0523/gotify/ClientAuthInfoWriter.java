package io.github.ctlove0523.gotify;

/**
 * A ClientAuthInfoWriter implementor knows how to write authentication info to a request
 *
 * @author chentong
 */
public interface ClientAuthInfoWriter {

	GotifyRequest authenticateRequest(GotifyRequest request);
}
