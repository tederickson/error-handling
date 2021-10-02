package com.lpl.errorhandling.repository;

import com.lpl.errorhandling.model.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

// see https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html
public interface CityRepository extends JpaRepository<CityEntity, Long> {
}
