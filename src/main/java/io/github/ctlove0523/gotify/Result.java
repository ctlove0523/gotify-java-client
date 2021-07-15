package io.github.ctlove0523.gotify;

import java.io.IOException;

public interface Result<T, E> {

	T result();

	E error();

	IOException exception();

	boolean isSuccessful();
}
