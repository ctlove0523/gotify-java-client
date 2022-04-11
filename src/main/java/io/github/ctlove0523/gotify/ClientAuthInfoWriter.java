package io.github.ctlove0523.gotify;

/**
 * A ClientAuthInfoWriter implementor knows how to write authentication info to a request
 *
 * @author chentong
 */
interface ClientAuthInfoWriter {

    /**
     * write authentication to original request
     *
     * @param request original request without authentication
     * @return {@link GotifyRequest} with authentication
     */
    GotifyRequest authenticateRequest(GotifyRequest request);
}
