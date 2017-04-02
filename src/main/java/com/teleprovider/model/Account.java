package com.teleprovider.model;

import javax.persistence.*;
import java.util.*;

/**
 * Created by olegs on 29.03.2017.
 */
@Entity
@Table(name = "accounts")
public class Account {

    private Long client_id;
    private Client client;
    private Double funds;
    private Date activationDate;
    private Tariff tariff;
    private Set<Transaction> transactions = new HashSet<>(0);

    public Account() {}

    public Account(Long client_id, Double funds) {
        this.client_id = client_id;
        this.funds = funds;
    }

    @Id
    @Column(name = "client_id")
    public Long getClient_id() {
        return client_id;
    }
    public void setClient_id(Long client_id) {
        this.client_id = client_id;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }

    @Column(name = "funds")
    public Double getFunds() {
        return funds;
    }
    public void setFunds(Double funds) {
        this.funds = funds;
    }

    @Column(name = "date_activated")
    public Date getActivationDate() {
        return activationDate;
    }
    public void setActivationDate(Date activationDate) {
        this.activationDate = activationDate;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "active_tariff_id")
    public Tariff getTariff() {
        return tariff;
    }
    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }

    @OneToMany(mappedBy = "account")
    public Set<Transaction> getTransactions() {return this.transactions;}
    public void setTransactions(Set<Transaction> transactions) {this.transactions = transactions;}

    @Override
    public String toString() {
        return "Account{" +
                "client_id=" + client_id +
                ", funds=" + funds +
                ", activationDate=" + activationDate +
                //", currentTariff=" + currentTariff +
                '}';
    }
}
