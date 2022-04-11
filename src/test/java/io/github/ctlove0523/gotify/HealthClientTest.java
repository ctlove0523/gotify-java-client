package io.github.ctlove0523.gotify;

import io.github.ctlove0523.gotify.health.Health;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HealthClientTest extends BaseTest {
    private final HealthMockerServer server = new HealthMockerServer();

    @Before
    public void startServer() throws Exception {
        server.start();
    }

    @After
    public void stopServer() throws Exception {
        server.stop();
    }

    @Test
    public void test_getHealth() {

        HealthClient client = newGotifyClient(server).getHealthClient();

        Result<Health, ResponseError> result = client.getHealth();

        Assert.assertTrue(result.isSuccessful());
        Assert.assertEquals(result.result().getDatabase(), "mysql");
    }
}
