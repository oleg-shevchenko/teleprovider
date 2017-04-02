package com.teleprovider.services;

import com.teleprovider.model.Account;

import java.util.List;

/**
 * Created by olegs on 29.03.2017.
 */
public interface AccountService {
    Long addNewAccount(Account account);
    Account findById(Long id);
    void updateAccount(Account account);
    void deleteAccount(Account account);
    List<Account> getAllAccounts();
}
