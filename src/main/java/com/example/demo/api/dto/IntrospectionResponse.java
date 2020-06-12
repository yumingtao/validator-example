package com.example.demo.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CIAM token introspection response
 *
 * @author mingtao.yu@daimler.com
 * @date 2020/6/9 16:22
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class IntrospectionResponse {
    private Boolean active;
    private String scope;
    @JsonProperty("client_id")
    private String clientId;
    @JsonProperty("token_type")
    private String tokenType;
    private Integer exp;
    private String sub;
    private String iss;
}
