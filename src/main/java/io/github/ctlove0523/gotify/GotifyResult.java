package io.github.ctlove0523.gotify;

import java.io.IOException;
import java.util.Objects;

public class GotifyResult<T, GotifyResponseError> implements Result<T, GotifyResponseError> {
	private final T result;

	private final GotifyResponseError error;

	private final IOException exception;

	private GotifyResult(T result, GotifyResponseError error, IOException exception) {
		this.result = result;
		this.error = error;
		this.exception = exception;
	}

	public static <T, GotifyResponseError> Result<T, GotifyResponseError> ok(T result) {
		return new GotifyResult<>(result, null, null);
	}

	public static <T, GotifyResponseError> Result<T, GotifyResponseError> error(GotifyResponseError error) {
		return new GotifyResult<>(null, error, null);
	}

	public static <T, GotifyResponseError> Result<T, GotifyResponseError> exception(IOException exception) {
		return new GotifyResult<>(null, null, exception);
	}


	@Override

	public T result() {
		return result;
	}

	@Override
	public GotifyResponseError error() {
		return error;
	}

	@Override
	public IOException exception() {
		return exception;
	}

	@Override
	public boolean isSuccessful() {
		return Objects.isNull(error) && Objects.isNull(exception);
	}
}
