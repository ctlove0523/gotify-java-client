package io.github.ctlove0523.gotify;

import io.github.ctlove0523.gotify.version.GotifyVersion;

public interface VersionClient extends CloseableClient {

	GotifyVersion getVersion();
}
