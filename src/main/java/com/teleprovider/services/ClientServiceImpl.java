package com.teleprovider.services;

import com.teleprovider.dao.ClientDao;
import com.teleprovider.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by olegs on 29.03.2017.
 */
@Service("clientService")
public class ClientServiceImpl implements ClientService {
    private ClientDao clientDao;

    @Autowired(required = true)
    public void setCaseDao(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    @Transactional
    public Long addNewClient(Client client) {
        return clientDao.create(client);
    }

    @Transactional
    public Client findById(Long id) {
        return clientDao.read(id);
    }

    @Transactional
    public void updateClient(Client client) {
        clientDao.update(client);
    }

    @Transactional
    public void deleteClient(Client client) {
        clientDao.delete(client);
    }

    @Transactional(readOnly = true)
    public List<Client> getAllClients() {
        return clientDao.findAll();
    }
}
