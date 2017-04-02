package com.teleprovider.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by olegs on 31.03.2017.
 */
@Entity
@Table(name = "transactions")
public class Transaction {
    private Long id;
    private Account account;
    private Double summ;
    private String comment;
    private Date date;

    public Transaction() {}

    public Transaction(Account account, Double summ, String comment, Date date) {
        this.account = account;
        this.summ = summ;
        this.comment = comment;
        this.date = date;
    }

    @Id
    @GeneratedValue(generator = "increment2")
    @GenericGenerator(name = "increment2", strategy = "increment")
    @Column(name = "id")
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "account_id")
    public Account getAccount() {return this.account;}
    public void setAccount(Account account) {this.account = account;}

    @Column(name = "summ")
    public Double getSumm() {
        return summ;
    }
    public void setSumm(Double summ) {
        this.summ = summ;
    }

    @Column(name = "comment")
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

    @Column(name = "time")
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", summ=" + summ +
                ", comment='" + comment + '\'' +
                ", date=" + new SimpleDateFormat("dd.MM.YYYY").format(date) +
                '}';
    }
}
