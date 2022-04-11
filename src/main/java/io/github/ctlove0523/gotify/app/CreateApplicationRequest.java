package io.github.ctlove0523.gotify.app;

import lombok.Getter;
import lombok.Setter;

/**
 * @author chentong
 */
@Getter
@Setter
public class CreateApplicationRequest {
    private Integer id;
    private String name;
    private String description;
    private String image;
    private Boolean internal;
}
