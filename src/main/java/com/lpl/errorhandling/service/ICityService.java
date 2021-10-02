package com.lpl.errorhandling.service;

import com.lpl.errorhandling.domain.City;

import java.util.List;

public interface ICityService {
    City findById(Long id);

    City save(City city);

    void update(City city);

    List<City> findAll();
}
