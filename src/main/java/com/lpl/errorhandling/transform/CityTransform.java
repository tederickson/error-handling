package com.lpl.errorhandling.transform;

import com.lpl.errorhandling.domain.City;
import com.lpl.errorhandling.model.CityEntity;

public class CityTransform {
    public static City createCity(CityEntity cityEntity) {
        return City.builder()
                .name(cityEntity.getName())
                .id(cityEntity.getId())
                .population(cityEntity.getPopulation())
                .build();
    }

    public static CityEntity buildEntity(City city) {
        CityEntity cityEntity = new CityEntity();
        cityEntity.setId(city.getId());
        cityEntity.setName(city.getName());
        cityEntity.setPopulation(city.getPopulation());
        return cityEntity;
    }
}
