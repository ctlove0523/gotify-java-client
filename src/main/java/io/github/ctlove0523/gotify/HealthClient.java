package io.github.ctlove0523.gotify;

import io.github.ctlove0523.gotify.health.Health;

/**
 * @author chentong
 */
public interface HealthClient extends CloseableClient {

    Result<Health, ResponseError> getHealth();
}
