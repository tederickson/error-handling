package com.lpl.errorhandling.controller;

import com.lpl.errorhandling.domain.City;
import com.lpl.errorhandling.service.ICityService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log
public class CityController {

    @Autowired
    private ICityService cityService;

    @GetMapping(value = "/cities",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<City> findAll() {
        log.info("CityController.findAll");
        return cityService.findAll();
    }

    @GetMapping(value = "/cities/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public City getCity(@PathVariable Long id) {
        log.info("CityController.getCity(" + id + ")");
        return cityService.findById(id);
    }

    @PostMapping(value = "/cities",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public City createCity(@RequestBody City city) {
        log.info("CityController.createCity city = " + city);

        return cityService.save(city);
    }

    @PutMapping(value = "/cities",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateCity(@RequestBody City city) {
        log.info("CityController.updateCity city = " + city);

        cityService.update(city);
    }

    @GetMapping(value = "/bad", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<City> demoGenericHandler() {
        log.info("CityController.demoGenericHandler");

        throw new NullPointerException("demo handling Java exceptions");
    }


}
