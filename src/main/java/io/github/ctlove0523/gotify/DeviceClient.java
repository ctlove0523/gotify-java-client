package io.github.ctlove0523.gotify;

import java.util.List;

import io.github.ctlove0523.gotify.device.Client;

/**
 * @author chentong
 */
public interface DeviceClient extends CloseableClient {

	Result<List<Client>, GotifyResponseError> getClients();

	Result<Client, GotifyResponseError> createClient(Client client);

	Result<Client, GotifyResponseError> updateClient(Integer id, Client client);

	Result<Boolean, GotifyResponseError> deleteClient(Integer id);
}
