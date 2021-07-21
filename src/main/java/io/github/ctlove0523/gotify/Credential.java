package io.github.ctlove0523.gotify;

public interface Credential {
	/**
	 * get gotify user name
	 * @return the string format user name
	 */
	String getUserName();

	/**
	 * get password of user
	 *
	 * @return password of user
	 */
	String getPassword();
}
