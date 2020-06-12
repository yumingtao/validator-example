package com.example.demo.handler;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.*;

import java.io.IOException;

/**
 * Sometimes, try-catch block is not enough to handle errors for RestTemplate to call Api,
 * because it is not scalable when the number of HTTP request increases.
 * Use a reusable custom error handler by extending DefaultResponseErrorHandler.
 *
 * @author ymingta
 */
@Slf4j
public class HelloDefaultErrorHandler extends DefaultResponseErrorHandler {

    @Override
    protected void handleError(ClientHttpResponse response, HttpStatus statusCode) throws IOException {
        switch (statusCode.series()) {
            case CLIENT_ERROR:
                log.error("Client error : {}", response.toString());
                return;
            case SERVER_ERROR:
            default:
                super.handleError(response, statusCode);
        }
    }
}
