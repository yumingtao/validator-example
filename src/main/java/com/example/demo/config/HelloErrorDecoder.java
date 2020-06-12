package com.example.demo.config;


import com.example.demo.exceptions.OAtuhException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import java.io.IOException;

import static feign.FeignException.errorStatus;

/**
 * @author yumingtao
 */
@Configuration
@Slf4j
public class HelloErrorDecoder implements ErrorDecoder {

    private ObjectMapper objectMapper;

    @Autowired
    public HelloErrorDecoder(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            String bodyJson = Util.toString(response.body().asReader());
            log.info("===========" + bodyJson);
            int status = response.status();
            if (status != 0) {
                HttpStatus httpStatus = HttpStatus.valueOf(response.status());
                if (httpStatus.is4xxClientError()) {
                    return new OAtuhException(bodyJson);
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return errorStatus(methodKey, response);
    }
}
