package com.teleprovider.dao;

import com.teleprovider.model.Account;
import com.teleprovider.model.Tariff;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by olegs on 29.03.2017.
 */

@Repository("accountDao")
public class AccountDaoImpl implements AccountDao {
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    public Long create(Account account) {
        Session session = sessionFactory.getCurrentSession();
        Long client_id = (Long) session.save(account);
        return client_id;
    }

    public Account read(Long client_id) {
        Session session = sessionFactory.getCurrentSession();
        Account account = (Account) session.get(Account.class, client_id);
        return account;
    }

    public void update(Account account) {
        Session session = sessionFactory.getCurrentSession();
        session.update(account);
    }

    public void delete(Account account) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(account);
    }

    public List<Account> findAll() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Account");
        return query.list();
    }

    @Transactional
    public List<Account> findAllWithTariff(Long tariff_id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT account FROM Account account WHERE account.tariff.id = :id");
        query.setParameter("id", tariff_id);
        return query.list();
    }
}
