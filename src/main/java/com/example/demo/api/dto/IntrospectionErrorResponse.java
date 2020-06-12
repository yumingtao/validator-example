package com.example.demo.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author yumingtao
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IntrospectionErrorResponse {
    private String error;
    private String error_description;
}
