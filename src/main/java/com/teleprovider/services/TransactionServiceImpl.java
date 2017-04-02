package com.teleprovider.services;

import com.teleprovider.dao.TransactionDao;
import com.teleprovider.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by olegs on 31.03.2017.
 */

@Service("transactionService")
public class TransactionServiceImpl implements TransactionService {

    private TransactionDao transactionDao;

    @Autowired(required = true)
    public void setCaseDao(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    @Override
    @Transactional
    public Long addNewTransaction(Transaction transaction) {
        return transactionDao.create(transaction);
    }

    @Override
    @Transactional
    public Transaction findById(Long id) {
        return transactionDao.read(id);
    }

    @Override
    @Transactional
    public void updateTransaction(Transaction transaction) {
        transactionDao.update(transaction);
    }

    @Override
    @Transactional
    public void deleteTransaction(Transaction transaction) {
        transactionDao.delete(transaction);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Transaction> getAllTransactions() {
        return transactionDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Transaction> getClientTransactions(Long id) {
        return transactionDao.findByClientId(id);
    }
}
