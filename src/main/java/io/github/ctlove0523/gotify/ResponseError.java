package io.github.ctlove0523.gotify;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author chentong
 */
@Getter
@Setter
@ToString
public class ResponseError {
	private String error;
	private Integer errorCode;
	private String errorDescription;
}
