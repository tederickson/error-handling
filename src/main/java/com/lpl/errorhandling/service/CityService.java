package com.lpl.errorhandling.service;

import java.util.List;
import java.util.stream.Collectors;

import com.lpl.errorhandling.domain.City;
import com.lpl.errorhandling.exception.MyCityNotFoundException;
import com.lpl.errorhandling.exception.MyInvalidParameterException;
import com.lpl.errorhandling.exception.MyNoDataFoundException;
import com.lpl.errorhandling.repository.CityRepository;
import com.lpl.errorhandling.transform.CityTransform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityService implements ICityService {
    @Autowired
    private CityRepository cityRepository;

    @Override
    public City findById(Long id) {
        final var cityTable = cityRepository.findById(id);

        if (cityTable.isPresent()) {
            return CityTransform.createCity(cityTable.get());
        }

        throw new MyCityNotFoundException(id);
    }

    @Override
    public City save(City city) {
        // Validate city fields
        if (city.getId() != null) {
            throw new MyInvalidParameterException("id", city.getId().toString(),
                    "ID not part of save, use update instead");
        }
        validate(city);

        final var cityEntity = CityTransform.buildEntity(city);

        return CityTransform.createCity(cityRepository.save(cityEntity));
    }


    @Override
    public void update(City city) {
        // Validate city fields
        if (city.getId() == null) {
            throw new MyInvalidParameterException("id");
        }
        validate(city);

        final var cityEntity = CityTransform.buildEntity(city);

        cityRepository.save(cityEntity);
    }

    @Override
    public List<City> findAll() {
        final var cities = cityRepository.findAll();

        if (cities.isEmpty()) {
            throw new MyNoDataFoundException();
        }
        return cities.stream().map(CityTransform::createCity).collect(Collectors.toList());
    }

    private void validate(City city) {
        // Id is validated by calling program since value is null for save and required for update
        if (city.getName() == null) {
            throw new MyInvalidParameterException("name");
        }
        if (city.getPopulation() < 1) {
            throw new MyInvalidParameterException("population", String.valueOf(city.getPopulation()),
                    "Population can not be less than 1");
        }
    }
}
