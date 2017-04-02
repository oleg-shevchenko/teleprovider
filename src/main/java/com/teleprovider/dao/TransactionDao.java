package com.teleprovider.dao;

import com.teleprovider.model.Transaction;

import java.util.List;

/**
 * Created by olegs on 31.03.2017.
 */
public interface TransactionDao {
    Long create(Transaction transaction);
    Transaction read(Long id);
    void update(Transaction transaction);
    void delete(Transaction transaction);
    List<Transaction> findAll();
    List<Transaction> findByClientId(Long id);
}
