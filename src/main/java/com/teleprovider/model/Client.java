package com.teleprovider.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by olegs on 29.03.2017.
 */

@Entity
@Table(name = "clients")
public class Client {

    private Long id;
    private String name;
    private String address;
    private String email;
    private String info;
    private Account account;

    public Client() {}

    public Client(String name, String address, String email, String info) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.info = info;
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

    @Column(name = "name")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "address")
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {this.address = address;}

    @Column(name = "email")
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    @Column(name = "info")
    public String getInfo() {
        return info;
    }
    public void setInfo(String info) {
        this.info = info;
    }

    @OneToOne(mappedBy = "client")
    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", info='" + info + '\'' +
                '}';
    }
}
