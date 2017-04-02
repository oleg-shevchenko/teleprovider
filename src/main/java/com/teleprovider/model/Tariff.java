package com.teleprovider.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by olegs on 29.03.2017.
 */

@Entity
@Table(name = "tariffs")
public class Tariff {

    private Long id;
    private String name;
    private Double cost;
    private Integer months;

//    @OneToMany(mappedBy = "tariff", cascade = CascadeType.ALL)
//    private Set<Account> accounts = new HashSet<Account>(0);

    public Tariff() {}

    public Tariff(String name, Double cost, Integer months) {
        this.name = name;
        this.cost = cost;
        this.months = months;
    }

//    public Set<Account> getAccounts() {return this.accounts;}

    @Id
    @GeneratedValue(generator = "increment2")
    @GenericGenerator(name = "increment2", strategy = "increment")
    @Column(name = "id")
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    @Column(name = "name")
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    @Column(name = "cost")
    public Double getCost() {return cost;}
    public void setCost(Double cost) {this.cost = cost;}

    @Column(name = "months")
    public Integer getMonths() {return months;}
    public void setMonths(Integer months) {this.months = months;}

    @Override
    public String toString() {
        return "Tariff{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                ", months=" + months +
                '}';
    }
}
