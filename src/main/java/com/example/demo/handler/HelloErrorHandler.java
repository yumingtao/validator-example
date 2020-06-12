package com.example.demo.handler;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

/**
 * Sometimes, try-catch block is not enough to handle errors for RestTemplate to call Api,
 * because it is not scalable when the number of HTTP request increases.
 * Use a reusable custom error handler by implementing ResponseErrorHandler interface.
 *
 * @author ymingta
 */
@Slf4j
public class HelloErrorHandler implements ResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return new DefaultResponseErrorHandler().hasError(response);
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        log.error(response.toString());
    }
}
