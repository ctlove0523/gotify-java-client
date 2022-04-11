package io.github.ctlove0523.gotify;

import io.github.ctlove0523.gotify.device.Client;

import java.util.List;

/**
 * @author chentong
 */
public interface DeviceClient extends CloseableClient {

    Result<List<Client>, ResponseError> getClients();

    Result<Client, ResponseError> createClient(Client client);

    Result<Client, ResponseError> updateClient(Integer id, Client client);

    Result<Boolean, ResponseError> deleteClient(Integer id);
}
