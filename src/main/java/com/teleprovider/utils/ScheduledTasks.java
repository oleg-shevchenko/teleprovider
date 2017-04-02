package com.teleprovider.utils;

import com.teleprovider.model.Account;
import com.teleprovider.model.Tariff;
import com.teleprovider.model.Transaction;
import com.teleprovider.services.AccountService;
import com.teleprovider.services.MailService;
import com.teleprovider.services.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by olegs on 01.04.2017.
 */

@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");

    static Calendar prevCal = Calendar.getInstance();

    @Scheduled(fixedRate = 5000) //In real server, maybe, period must be larger
    public void checkNextDay() {
        Calendar currCal = Calendar.getInstance();
        log.info("Check next day. Prev date: " + dateFormat.format(prevCal.getTime()) + " Curr date: " + dateFormat.format(currCal.getTime()));
        //Check if new day of the year number larger than previous
        if(currCal.get(Calendar.DAY_OF_YEAR) > prevCal.get(Calendar.DAY_OF_YEAR)) {
            prevCal = currCal;
            executeEvent();
        //Check if next year
        } else if (currCal.get(Calendar.DAY_OF_YEAR) < prevCal.get(Calendar.DAY_OF_YEAR)
                && currCal.getTimeInMillis() > prevCal.getTimeInMillis()) {
            prevCal = currCal;
            executeEvent();
        } else {
            prevCal = currCal;
        }
    }

    private synchronized void executeEvent() {
        updateTarrifs();
    }

    @Autowired
    AccountService accountService;

    public void updateTarrifs() {
        log.info("Start check tarriffs...");
        List<Account> accList = accountService.getAllAccounts();
        Calendar actualCalendar = Calendar.getInstance();
        for (Account acc : accList) {
            Tariff tariff = acc.getTariff();
            if(tariff != null) {
                Calendar actCalendar = Calendar.getInstance();
                //Set activation date
                actCalendar.setTime(acc.getActivationDate());
                //Add tariff months to activation date
                actCalendar.add(Calendar.MONTH, tariff.getMonths());
                //If actual calendar time larger or equals, than start next tariff period
                if(actCalendar.getTimeInMillis() <= actualCalendar.getTimeInMillis()) {
                    startNextTariffPeriod(acc);
                }
            }
        }
    }

    @Autowired
    TransactionService transactionService;

    @Autowired
    MailService mailService;

    private void startNextTariffPeriod(Account acc) {
        log.info("Try update tariff: " + acc);
        String trComment = "Withdrawing funds for tariff renewal";
        boolean result = executeTransaction(acc, 0 - acc.getTariff().getCost(), trComment);
        //If success transaction
        if(result) {
            acc.setActivationDate(new Date());
            log.info("SUCCESS tariff renewal for account with id=" + acc.getClient_id());
        } else {
            acc.setTariff(null);
            acc.setActivationDate(null);

            //TODO add mail column to clients table
            String email = acc.getClient().getEmail();
            if(email != null) mailService.sendInsufficientFundsEmail(email, acc);
            log.warn("Tariff renewal FAIL for account with id=" + acc.getClient_id() + " Insufficient funds!");
        }
        accountService.updateAccount(acc);
    }

    private boolean executeTransaction(Account account, Double funds, String comment) {
        //Check insufficient funds
        if(account != null && (account.getFunds() + funds) >= 0) {
            Transaction tr = new Transaction(account, funds, comment, Calendar.getInstance().getTime());
            transactionService.addNewTransaction(tr);
            account.setFunds(account.getFunds() + funds);
            accountService.updateAccount(account);
            return true;
        }
        log.warn("Unsucces transaction account id=" + account.getClient_id() +
                ". Insufficient funds: ballance: $" + account.getFunds() + ", transaction: $" + funds);
        return false;
    }
}
