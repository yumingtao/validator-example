package com.example.demo.api.client;

import com.example.demo.api.dto.IntrospectionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * @author ymingta
 */
@FeignClient(value = "ciam", url = "${ciam.baseUrl}")
public interface OAuthClientApi {
    @PostMapping(path = "/as/introspect.oauth2", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.ALL_VALUE)
    IntrospectionResponse verifyToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String basicAuth, @RequestParam(value = "token") String accessToken);
}
