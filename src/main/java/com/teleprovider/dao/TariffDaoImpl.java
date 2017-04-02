package com.teleprovider.dao;

import com.teleprovider.model.Tariff;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by olegs on 30.03.2017.
 */
@Repository("tariffDao")
public class TariffDaoImpl implements TariffDao {
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    public Long create(Tariff tariff) {
        Session session = sessionFactory.getCurrentSession();
        Long tariff_id = (Long) session.save(tariff);
        return tariff_id;
    }

    public Tariff read(Long tariff_id) {
        Session session = sessionFactory.getCurrentSession();
        Tariff tariff = (Tariff) session.get(Tariff.class, tariff_id);
        return tariff;
    }

    public void update(Tariff tariff) {
        Session session = sessionFactory.getCurrentSession();
        session.update(tariff);
    }

    public void delete(Tariff tariff) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(tariff);
    }

    public List<Tariff> findAll() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Tariff");
        return query.list();
    }
}
