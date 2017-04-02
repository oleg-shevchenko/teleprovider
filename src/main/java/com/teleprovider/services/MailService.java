package com.teleprovider.services;

import com.teleprovider.model.Account;

/**
 * Created by olegs on 02.05.2017.
 */
public interface MailService {
    void sendInsufficientFundsEmail(String recipientMail, Account account);
}
