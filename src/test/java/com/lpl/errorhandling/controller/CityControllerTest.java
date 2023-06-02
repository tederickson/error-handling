package com.lpl.errorhandling.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpl.errorhandling.domain.City;
import com.lpl.errorhandling.model.CityEntity;
import com.lpl.errorhandling.repository.CityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc
class CityControllerTest {

    private final ObjectMapper mapper = new ObjectMapper();
    @MockBean
    private CityRepository cityRepository;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void findAll() throws Exception {
        // andDo(print()) prints the request and response.
        mockMvc.perform(get("/cities"))
                .andDo(print())
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.statusMessage").value("No data found"))
                .andExpect(jsonPath("$.code").value("15"));
    }

    @Test
    void getCity() throws Exception {
        mockMvc.perform(get("/cities/{id}", 1792))
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.statusMessage").value("City with Id 1792 not found"))
                .andExpect(jsonPath("$.code").value("111"));
    }


    @Test
    void createCity() throws Exception {
        final var city = City.builder().population(182031).name("Silverado").build();

        Long id = 13452L;
        CityEntity cityEntity = new CityEntity();
        cityEntity.setId(id);
        cityEntity.setName(city.getName());
        cityEntity.setPopulation(city.getPopulation());

        doReturn(cityEntity).when(cityRepository).save(any());

        final var json = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(city);

        mockMvc.perform(post("/cities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(city.getName()))
                .andExpect(jsonPath("$.population").value(city.getPopulation()));
    }

    @Test
    void createCity_setId() throws Exception {
        Long id = 13124L;
        final var city = City.builder().id(id).population(182031).name("Silverado").build();

        final var json = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(city);

        mockMvc.perform(post("/cities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.statusMessage").value("ID not part of save, use update instead"))
                .andExpect(jsonPath("$.code").value("11234"))
                .andExpect(jsonPath("$.field").value("id"))
                .andExpect(jsonPath("$.value").value(id.toString()));
    }

    @Test
    void updateCity_missingId() throws Exception {
        final var json = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(City.builder().build());
        mockMvc.perform(put("/cities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.statusMessage").value("Null parameter"))
                .andExpect(jsonPath("$.code").value("11234"))
                .andExpect(jsonPath("$.field").value("id"));
    }

    @Test
    void updateCity_missingNameAndPopulation() throws Exception {
        final var city = City.builder().id(13124L).build();


        final var json = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(city);
        mockMvc.perform(put("/cities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.statusMessage").value("Null parameter"))
                .andExpect(jsonPath("$.code").value("11234"))
                .andExpect(jsonPath("$.field").value("name"));
    }

    @Test
    void updateCity_missingPopulation() throws Exception {
        final var city = City.builder().id(13124L).name("Silverado").build();

        final var json = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(city);

        mockMvc.perform(put("/cities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.statusMessage").value("Population can not be less than 1"))
                .andExpect(jsonPath("$.code").value("11234"))
                .andExpect(jsonPath("$.field").value("population"));
    }

    @Test
    void updateCity() throws Exception {
        final var city = City.builder().id(13124L).population(182031).name("Silverado").build();

        final var json = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(city);

        mockMvc.perform(put("/cities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void demoGenericHandler() throws Exception {
        mockMvc.perform(get("/bad"))
                .andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .andExpect(jsonPath("$.status").value(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .andExpect(jsonPath("$.statusMessage").value("demo handling Java exceptions"));
    }
}