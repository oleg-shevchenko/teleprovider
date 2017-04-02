package com.teleprovider.dao;

import com.teleprovider.model.Account;

import java.util.List;

/**
 * Created by olegs on 29.03.2017.
 */
public interface AccountDao {
    Long create(Account account);

    Account read(Long client_id);

    void update(Account account);

    void delete(Account account);

    List<Account> findAll();

    List<Account> findAllWithTariff(Long tariff_id);
}
