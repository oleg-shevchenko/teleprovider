package com.teleprovider.controller;

import com.teleprovider.model.Account;
import com.teleprovider.model.Client;
import com.teleprovider.model.Tariff;
import com.teleprovider.model.Transaction;
import com.teleprovider.services.AccountService;
import com.teleprovider.services.ClientService;
import com.teleprovider.services.TariffService;
import com.teleprovider.services.TransactionService;
import com.teleprovider.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by olegs on 29.03.2017.
 */
@RestController
public class ClientsController {
    private static final Logger log = LoggerFactory.getLogger(ClientsController.class);

    @Autowired
    ClientService clientService;

    @Autowired
    AccountService accountService;

    @Autowired
    TariffService tariffService;

    @Autowired
    TransactionService transactionService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("clientsPage");
        List<Client> clients = clientService.getAllClients();
        mav.addObject("clients", clients);
        return mav;
    }

    @RequestMapping(value = "/clients/filter", method = RequestMethod.POST)
    public ModelAndView namefilter(@RequestParam(value = "filtername", required = false) String filtername,
                                   @RequestParam(value = "filteraddress", required = false) String filteraddress) {
        ModelAndView mav = new ModelAndView("clientsPage");
        List<Client> clients = clientService.getAllClients();
        List<Client> filtered = new ArrayList<>();
        if(filtername != null || filteraddress != null) {
            String loName = filtername != null ? filtername.toLowerCase() : null;
            String loAddr = filteraddress != null ? filteraddress.toLowerCase() : null;
            if(loName != null && loAddr != null) {
                for(Client client : clients) {
                    if(client.getName().toLowerCase().contains(loName)
                            && client.getAddress().toLowerCase().contains(loAddr)) {
                        filtered.add(client);
                    }
                }
            } else if(loName != null) {
                for(Client client : clients) {
                    if(client.getName().toLowerCase().contains(loName)) {
                        filtered.add(client);
                    }
                }
            } else {
                for(Client client : clients) {
                    if(client.getAddress().toLowerCase().contains(loAddr)) {
                        filtered.add(client);
                    }
                }
            }
        } else {
            filtered = clients;
        }
        mav.addObject("clients", filtered);
        return mav;
    }

    List<Tariff> tariffList;
    private List<Tariff> getTariffList() {
        if(tariffList != null) return tariffList;
        else return tariffService.getAllTariffs();
    }
    private void updateTariffList() {
        tariffList = tariffService.getAllTariffs();
    }

    @RequestMapping(value = "/clientinfo/{clientID}", method = RequestMethod.GET)
    public ModelAndView clientInfo(@PathVariable Long clientID) {
        ModelAndView mav = new ModelAndView("clientInfo");
        Client client = clientService.findById(clientID);
        if(client == null) throw new NullPointerException("Error! No such user");
        mav.addObject("client", client);
        mav.addObject("tariffs", getTariffList());
        return mav;
    }

    @RequestMapping(value = "/saveclient", method = RequestMethod.POST)
    public void saveClient(@RequestParam(value = "id", required = false) Long id,
                           @RequestParam(value = "name", required = true) String name,
                           @RequestParam(value = "address", required = true) String address,
                           @RequestParam(value = "email", required = false) String email,
                           @RequestParam(value = "info", required = false) String info,
                           HttpServletResponse response) throws IOException {
        Client client = new Client(name, address, email, info);
        //Create new client and account
        if(id == null) {
            id = clientService.addNewClient(client);
            Account account = new Account();
            account.setClient_id(id);
            account.setFunds(0.0);
            accountService.addNewAccount(account);
            response.sendRedirect("/");
        //Update client info
        } else {
            client.setId(id);
            clientService.updateClient(client);
            response.sendRedirect("/clientinfo/" + id);
        }
    }

    @RequestMapping(value = "/transaction", method = RequestMethod.POST)
    public void transaction(@RequestParam (value = "client_id", required = true) Long client_id,
                            @RequestParam (value = "funds", required = true) Double funds,
                            @RequestParam (value = "comment", required = false) String comment,
                            HttpServletResponse response) throws IOException {
        Account account = accountService.findById(client_id);
        executeTransaction(account, funds, comment);
        response.sendRedirect("/clientinfo/" + account.getClient_id());
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

    @RequestMapping(value = "/changetariff/{clientID}/{tariffID}", method = RequestMethod.GET)
    public void changeTariff(@PathVariable Long clientID,
                             @PathVariable Long tariffID,
                             HttpServletResponse response) throws IOException {
        Account account = accountService.findById(clientID);
        if(account != null) {
            Tariff tariff = null;
            if(tariffID != 0L) {
                tariff = tariffService.findById(tariffID);
                String comment = "Change tariff to " + tariff.getName() + " - $" + tariff.getCost();
                if(executeTransaction(account, 0 - tariff.getCost(), comment)) {
                    account.setActivationDate(Calendar.getInstance().getTime());
                    account.setTariff(tariff);
                } else {
                    //Do not change tariff if insufficient funds
                    log.warn("FAIL. Unsucces change tariff for account id=" + account.getClient_id());
                }
            } else {
                account.setActivationDate(null);
                account.setTariff(null);
            }
            accountService.updateAccount(account);
            response.sendRedirect("/clientinfo/" + account.getClient_id());
        }
    }

    @RequestMapping(value = "/transactions/{accountID}", method = RequestMethod.GET)
    public ModelAndView accTransactions(@PathVariable Long accountID) {
        ModelAndView mav = new ModelAndView("transactionsPage");
        List<Transaction> list = null;
        if(accountID == 0L) {
            list = transactionService.getAllTransactions();
        } else {
            list = transactionService.getClientTransactions(accountID);
        }
        mav.addObject("transactions", list);
        return mav;
    }

    @RequestMapping(value = "/transactions/filter", method = RequestMethod.POST)
    public ModelAndView filterTransactions(@RequestParam (value = "filtername", required = false) String name,
                                           @RequestParam (value = "datestart", required = false) String datestart,
                                           @RequestParam (value = "dateend", required = false) String dateend) {
        ModelAndView mav = new ModelAndView("transactionsPage");
        List<Transaction> finalList;
        List<Client> filteredClients = null;
        Date sDate = null, eDate = null;
        try {
            sDate = datestart != null ? Consts.formDateFormat.parse(datestart) : null;
        } catch (ParseException e) {e.printStackTrace();}
        try {
            eDate = dateend != null ? Consts.formDateFormat.parse(dateend) : null;
        } catch (ParseException e) {e.printStackTrace();}

        String loName = name != null ? name.toLowerCase() : null;

        //If we got name parameter (or name part), than find suitable clients
        if(loName != null) {
            List<Client> clients = clientService.getAllClients();
            filteredClients = new ArrayList<>();
            for(Client client : clients) {
                if(client.getName().toLowerCase().contains(loName)) {
                    filteredClients.add(client);
                }
            }
        }

        //If we have filtered clients
        if(filteredClients != null) {
            List<Transaction> clTranactions = new ArrayList<>();
            for (Client client : filteredClients) {
                clTranactions.addAll(client.getAccount().getTransactions());
            }
            finalList = filterTransactionsByDate(clTranactions, sDate, eDate);
        //If no name filter
        } else {
            List<Transaction> allList = transactionService.getAllTransactions();
            finalList = filterTransactionsByDate(allList, sDate, eDate);
        }
        mav.addObject("transactions", finalList);
        return mav;
    }

    //Filter transactions list by start and end dates
    List<Transaction> filterTransactionsByDate(List<Transaction> inList, Date sDate, Date eDate) {
        //Add one day time to include all end date records
        if(eDate != null) eDate.setTime(eDate.getTime() + 24L*60L*60L*1000L);

        List<Transaction> outList = new ArrayList<>();
        if(sDate != null || eDate != null) {
            for (Transaction tr : inList) {
                if (sDate != null && eDate != null) {
                    if (tr.getDate().getTime() >= sDate.getTime() && tr.getDate().getTime() < eDate.getTime()) {
                        outList.add(tr);
                    }
                } else if (sDate != null) {
                    if (tr.getDate().getTime() >= sDate.getTime()) {
                        outList.add(tr);
                    }
                } else if (eDate != null) {
                    if (tr.getDate().getTime() < eDate.getTime()) {
                        outList.add(tr);
                    }
                }
            }
        } else {
            outList = inList;
        }
        return outList;
    }

    //TODO not working at this time
    @ExceptionHandler(Exception.class)
    public ModelAndView globalExceptionHandler(Exception e) {
        e.printStackTrace();
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", e.getMessage());
        return modelAndView;
    }
}
