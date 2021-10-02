package com.lpl.errorhandling.domain;

import lombok.Builder;
import lombok.Data;

/**
 * Contract first error structure - mimic a Swagger generated class.
 */
@Data
@Builder
public class LplGenericError {
    private Integer status;         // HTTP status code
    private Integer code;           // Unique program code
    private String statusMessage;   // Human friendly description
}
