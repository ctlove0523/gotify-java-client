package io.github.ctlove0523.gotify;

import io.github.ctlove0523.gotify.version.Version;

public interface VersionClient extends CloseableClient {

	/**
	 * Get version information.
	 *
	 * @return return a {@link Result} object,if success {@link Result#result()} return
	 * {@link Version} otherwise {@link Result#error()} return a {@link ResponseError}
	 */
	Result<Version, ResponseError> getVersion();
}
