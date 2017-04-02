package com.teleprovider.dao;

import com.teleprovider.model.Tariff;

import java.util.List;

/**
 * Created by olegs on 30.03.2017.
 */
public interface TariffDao {
    Long create(Tariff tariff);

    Tariff read(Long tariff_id);

    void update(Tariff tariff);

    void delete(Tariff tariff);

    List<Tariff> findAll();
}
