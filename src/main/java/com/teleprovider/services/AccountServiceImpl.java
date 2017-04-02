package com.teleprovider.services;

import com.teleprovider.dao.AccountDao;
import com.teleprovider.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by olegs on 29.03.2017.
 */
@Service("accountService")
public class AccountServiceImpl implements AccountService {
    private AccountDao accountDao;

    @Autowired(required = true)
    public void setCaseDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Transactional
    public Long addNewAccount(Account account) {
        return accountDao.create(account);
    }

    @Transactional
    public Account findById(Long client_id) {
        return accountDao.read(client_id);
    }

    @Transactional
    public void updateAccount(Account account) {
        accountDao.update(account);
    }

    @Transactional
    public void deleteAccount(Account account) {
        accountDao.delete(account);
    }

    @Transactional(readOnly = true)
    public List<Account> getAllAccounts() {
        return accountDao.findAll();
    }
}
