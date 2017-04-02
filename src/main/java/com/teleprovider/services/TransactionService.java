package com.teleprovider.services;

import com.teleprovider.model.Transaction;

import java.util.List;

/**
 * Created by olegs on 31.03.2017.
 */
public interface TransactionService {
    Long addNewTransaction(Transaction transaction);
    Transaction findById(Long id);
    void updateTransaction(Transaction transaction);
    void deleteTransaction(Transaction transaction);
    List<Transaction> getAllTransactions();
    List<Transaction> getClientTransactions(Long id);
}
