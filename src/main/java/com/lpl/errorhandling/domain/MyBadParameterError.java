package com.lpl.errorhandling.domain;

import lombok.Builder;
import lombok.Data;

/**
 * Contract first error structure - mimic a Swagger generated class.
 */
@Data
@Builder
public class MyBadParameterError {
    private Integer status;         // HTTP status code
    private Integer code;           // Unique program code
    private String statusMessage;   // Human friendly description
    private String field;           // Invalid field name
    private String value;           // Invalid value
}
