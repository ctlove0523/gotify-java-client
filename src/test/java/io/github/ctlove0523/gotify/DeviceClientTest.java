package io.github.ctlove0523.gotify;

import io.github.ctlove0523.gotify.device.Client;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class DeviceClientTest extends BaseTest {
    private final DeviceClientMockServer server = new DeviceClientMockServer();

    @Before
    public void startServer() throws Exception {
        server.start();
    }

    @After
    public void stopServer() throws Exception {
        server.stop();
    }

    @Test
    public void test_getClients() {
        Client client1 = new Client();
        client1.setId(random.nextInt());
        client1.setName("client1");
        client1.setToken("token1");

        Client client2 = new Client();
        client2.setId(random.nextInt());
        client2.setName("client2");
        client2.setToken("token2");

        server.addClient(client1);
        server.addClient(client2);

        DeviceClient deviceClient = newGotifyClient(server).getDeviceClient();

        Result<List<Client>, ResponseError> result = deviceClient.getClients();
        Assert.assertTrue(result.isSuccessful());

        List<Client> clients = result.result();
        Assert.assertEquals(2, clients.size());
    }

    @Test
    public void test_createClient() {
        Client client1 = new Client();
        client1.setId(random.nextInt());
        client1.setName("client1");
        client1.setToken("token1");

        DeviceClient deviceClient = newGotifyClient(server).getDeviceClient();

        Result<Client, ResponseError> result = deviceClient.createClient(client1);
        Assert.assertTrue(result.isSuccessful());
        Client client = result.result();
        Assert.assertEquals(client1.getId(), client.getId());
        Assert.assertEquals(client1.getName(), client.getName());
        Assert.assertEquals(client1.getToken(), client.getToken());

        Client storedClient = server.getClient(client1.getId());
        Assert.assertNotNull(storedClient);
        Assert.assertEquals(storedClient.getToken(), client1.getToken());
        Assert.assertEquals(storedClient.getName(), client1.getName());
    }

    @Test
    public void test_updateClients() {
        Client client1 = new Client();
        client1.setId(random.nextInt());
        client1.setName("client1");
        client1.setToken("token1");
        server.addClient(client1);

        DeviceClient deviceClient = newGotifyClient(server).getDeviceClient();

        Client updatedClient = new Client();
        updatedClient.setToken("update token");
        updatedClient.setName("update name");

        Result<Client, ResponseError> result = deviceClient.updateClient(client1.getId(), updatedClient);
        Assert.assertTrue(result.isSuccessful());

        Client newClient = result.result();
        Assert.assertEquals(newClient.getName(), updatedClient.getName());
        Assert.assertEquals(newClient.getToken(), updatedClient.getToken());
    }

    @Test
    public void test_deleteClients() {
        Client client1 = new Client();
        client1.setId(random.nextInt());
        client1.setName("client1");
        client1.setToken("token1");
        server.addClient(client1);
        Assert.assertNotNull(server.getClient(client1.getId()));

        DeviceClient deviceClient = newGotifyClient(server).getDeviceClient();


        Result<Boolean, ResponseError> result = deviceClient.deleteClient(client1.getId());
        Assert.assertTrue(result.isSuccessful());
        Assert.assertNull(server.getClient(client1.getId()));
    }

}
