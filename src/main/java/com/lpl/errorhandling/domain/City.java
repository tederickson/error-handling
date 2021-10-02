package com.lpl.errorhandling.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class City {
    private Long id;
    private String name;
    private int population;
}
