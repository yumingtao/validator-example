package com.example.demo.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * @author yumingtao
 */
@FeignClient(value = "client", url = "${client.baseUrl}")
public interface ClientApi {
    /**
     *
     * @param token
     * @param name
     * @return
     */
    @PostMapping(path = "/hello", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.ALL_VALUE)
    String hello(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestParam(name = "name") String name);
}
