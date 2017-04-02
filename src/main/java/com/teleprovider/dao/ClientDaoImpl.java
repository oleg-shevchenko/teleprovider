package com.teleprovider.dao;

import com.teleprovider.model.Client;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by olegs on 29.03.2017.
 */
@Repository("clientDao")
public class ClientDaoImpl implements ClientDao {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    public Long create(Client client) {
        Session session = sessionFactory.getCurrentSession();
        Long id = (Long) session.save(client);
        return id;
    }

    public Client read(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Client appUser = (Client) session.get(Client.class, id);
        return appUser;
    }

    public void update(Client client) {
        Session session = sessionFactory.getCurrentSession();
        session.update(client);
    }

    public void delete(Client client) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(client);
    }

    public List<Client> findAll() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Client");
        return query.list();
    }
}
