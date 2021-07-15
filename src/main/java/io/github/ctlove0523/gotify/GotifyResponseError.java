package io.github.ctlove0523.gotify;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GotifyResponseError {
	private String error;
	private Integer errorCode;
	private String errorDescription;
}
