package com.teleprovider.dao;

import com.teleprovider.model.Transaction;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by olegs on 31.03.2017.
 */

@Repository("transactionDao")
public class TransactionDaoImpl implements TransactionDao {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    @Override
    public Long create(Transaction transaction) {
        Session session = sessionFactory.getCurrentSession();
        Long id = (Long) session.save(transaction);
        return id;
    }

    @Override
    public Transaction read(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = (Transaction) session.get(Transaction.class, id);
        return transaction;
    }

    @Override
    public void update(Transaction transaction) {
        Session session = sessionFactory.getCurrentSession();
        session.update(transaction);
    }

    @Override
    public void delete(Transaction transaction) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(transaction);
    }

    @Override
    public List<Transaction> findAll() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Transaction");
        return query.list();
    }

    @Override
    public List<Transaction> findByClientId(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT transaction FROM Transaction transaction WHERE transaction.account.client_id = :id");
        query.setParameter("id", id);
        return query.list();
    }
}
