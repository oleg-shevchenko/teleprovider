package com.teleprovider.services;

import com.teleprovider.dao.TariffDao;
import com.teleprovider.model.Tariff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by olegs on 30.03.2017.
 */
@Service("tariffService")
public class TariffServiceImpl implements TariffService{
    private TariffDao tariffDao;

    @Autowired(required = true)
    public void setCaseDao(TariffDao tariffDao) {
        this.tariffDao = tariffDao;
    }

    @Override
    @Transactional
    public Long addNewTariff(Tariff tariff) {
        return tariffDao.create(tariff);
    }

    @Override
    @Transactional
    public Tariff findById(Long id) {
        return tariffDao.read(id);
    }

    @Override
    @Transactional
    public void updateTariff(Tariff tariff) {
        tariffDao.update(tariff);
    }

    @Override
    @Transactional
    public void deleteTariff(Tariff tariff) {
        tariffDao.delete(tariff);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tariff> getAllTariffs() {
        return tariffDao.findAll();
    }
}
