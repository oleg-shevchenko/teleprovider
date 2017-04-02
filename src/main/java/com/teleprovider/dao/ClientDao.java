package com.teleprovider.dao;

import com.teleprovider.model.Client;

import java.util.List;

/**
 * Created by olegs on 29.03.2017.
 */
public interface ClientDao {
    Long create(Client client);

    Client read(Long id);

    void update(Client client);

    void delete(Client client);

    List<Client> findAll();
}
