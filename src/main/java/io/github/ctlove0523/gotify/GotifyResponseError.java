package io.github.ctlove0523.gotify;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GotifyResponseError {
	private String error;
	private Integer errorCode;
	private String errorDescription;
}
