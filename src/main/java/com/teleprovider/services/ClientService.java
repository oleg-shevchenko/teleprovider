package com.teleprovider.services;

import com.teleprovider.model.Client;

import java.util.List;

/**
 * Created by olegs on 29.03.2017.
 */
public interface ClientService {
    Long addNewClient(Client client);
    Client findById(Long id);
    void updateClient(Client client);
    void deleteClient(Client client);
    List<Client> getAllClients();
}
