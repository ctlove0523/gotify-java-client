package io.github.ctlove0523.gotify;

import io.github.ctlove0523.gotify.device.Client;

/**
 * @author chentong
 */
public interface DeviceClient extends CloseableClient {

	Iterable<Client> getClients();

	Client createClient(Client client);

	Client updateClient(Integer id, Client client);

	Boolean deleteClient(Integer id);
}
