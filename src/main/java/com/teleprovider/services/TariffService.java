package com.teleprovider.services;

import com.teleprovider.model.Tariff;

import java.util.List;

/**
 * Created by olegs on 30.03.2017.
 */
public interface TariffService {
    Long addNewTariff(Tariff tariff);
    Tariff findById(Long id);
    void updateTariff(Tariff tariff);
    void deleteTariff(Tariff tariff);
    List<Tariff> getAllTariffs();
}
