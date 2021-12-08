package com.lpl.errorhandling.service;

import com.lpl.errorhandling.domain.City;
import com.lpl.errorhandling.model.CityEntity;
import com.lpl.errorhandling.repository.CityRepository;
import com.lpl.errorhandling.transform.CityTransform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CityService implements ICityService {
    @Autowired
    private CityRepository cityRepository;

    @Override
    public City findById(Long id) {
        Optional<CityEntity> cityTable = cityRepository.findById(id);

        if (cityTable.isPresent())
            return CityTransform.createCity(cityTable.get());

        throw new com.lpl.errorhandling.exception.MyCityNotFoundException(id);
    }

    @Override
    public City save(City city) {
        // Validate city fields
        if (city.getId() != null) {
            throw new com.lpl.errorhandling.exception.MyInvalidParameterException("id", city.getId()
                    .toString(),
                    "ID not part of save, use update instead");
        }
        validate(city);

        CityEntity cityEntity = CityTransform.buildEntity(city);

        return CityTransform.createCity(cityRepository.save(cityEntity));
    }


    @Override
    public void update(City city) {
        // Validate city fields
        if (city.getId() == null) {
            throw new com.lpl.errorhandling.exception.MyInvalidParameterException("id");
        }
        validate(city);

        CityEntity cityEntity = CityTransform.buildEntity(city);

        cityRepository.save(cityEntity);
    }

    @Override
    public List<City> findAll() {
        List<CityEntity> cities = cityRepository.findAll();

        if (cities.isEmpty())
            throw new com.lpl.errorhandling.exception.MyNoDataFoundException();

        List<City> cityList = new ArrayList<>();
        for (CityEntity cityEntity : cities) {
            cityList.add(CityTransform.createCity(cityEntity));
        }
        return cityList;
    }

    private void validate(City city) {
        // Id is validated by calling program since value is null for save and required for update
        if (city.getName() == null) {
            throw new com.lpl.errorhandling.exception.MyInvalidParameterException("name");
        }
        if (city.getPopulation() < 1) {
            throw new com.lpl.errorhandling.exception.MyInvalidParameterException("population", String.valueOf(city.getPopulation()),
                    "Population can not be less than 1");
        }
    }
}
