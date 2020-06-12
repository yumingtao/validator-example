package com.example.demo.service;

import com.example.demo.api.client.OAuthClientApi;
import com.example.demo.api.client.SecurityExampleClientApi;
import com.example.demo.api.dto.IntrospectionErrorResponse;
import com.example.demo.api.dto.IntrospectionResponse;
import com.example.demo.exceptions.OAtuhException;
import com.example.demo.validator.AbstractTokenValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;
import java.util.Optional;


/**
 * @author yumingtao
 */
@Component
@Slf4j
public class HelloService extends AbstractService {

    private SecurityExampleClientApi securityExampleClientApi;
    private OAuthClientApi oauthClientApi;
    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;

    @Value("${oauth.introspection.endpoint}")
    private String oAuthEndpoint;

    @Autowired
    public HelloService(SecurityExampleClientApi securityExampleClientApi, OAuthClientApi oauthClientApi, RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.securityExampleClientApi = securityExampleClientApi;
        this.oauthClientApi = oauthClientApi;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public String sayHello(String kid) {
        Optional<AbstractTokenValidator> tokenValidatorOptional = getTokenValidators().stream().filter(t -> t.isSupported(kid)).findFirst();
        if (tokenValidatorOptional.isPresent()) {
            return tokenValidatorOptional.get().verify(kid);
        }
        return null;
    }

    public String sayHello3(String name) {
        String token = generateAuthToken("user:admin12345");
        String result = securityExampleClientApi.hello(token, name);
        return result;
    }

    public IntrospectionResponse sayHello4() {
        String token = generateAuthToken("user:admin123456");
        IntrospectionResponse result = oauthClientApi.verifyToken(token, "12222");
        return result;
    }

    /**
     * When getting client error, process the response body.
     *
     * @return
     * @throws URISyntaxException
     * @throws JsonProcessingException
     */
    public Object sayHello6() throws URISyntaxException, JsonProcessingException {
        String authToken = generateAuthToken("user:admin12345");
        try {
            RequestEntity<Object> requestEntity = createRequestEntity(authToken);
            ResponseEntity<Object> result = restTemplate.exchange(requestEntity, Object.class);
            return result;
        } catch (HttpClientErrorException e) {
            String respJson = e.getResponseBodyAsString();
            if (!StringUtils.isEmpty(respJson)) {
                log.info(respJson);
                IntrospectionErrorResponse errorResponse = objectMapper.readValue(respJson, IntrospectionErrorResponse.class);
                return errorResponse;
            }
        }
        return null;
    }

    /**
     * When using HelloErrorHandler, only can get client/server exception, so need to check if the status code is client error.
     *
     * @return
     * @throws URISyntaxException
     */
    public Object sayHello7() throws URISyntaxException {
        String authToken = generateAuthToken("user:admin12345");
        ResponseEntity<Object> result = restTemplate.exchange(createRequestEntity(authToken), Object.class);

        if (result.getStatusCode() == HttpStatus.OK) {
            IntrospectionResponse response = objectMapper.convertValue(result.getBody(), IntrospectionResponse.class);
            log.info(response.toString());
        } else if (result.getStatusCode().is4xxClientError()) {
            IntrospectionErrorResponse response = objectMapper.convertValue(result.getBody(), IntrospectionErrorResponse.class);
            log.info(response.toString());
        }
        return result;
    }

    /**
     * When using HelloDefaultErrorHandler, only can get client exception, so don't need to check if the status code is client error.
     *
     * @return
     * @throws URISyntaxException
     */
    public Object sayHello8() throws URISyntaxException {
        String authToken = generateAuthToken("user:admin12345");
        ResponseEntity<Object> result = restTemplate.exchange(createRequestEntity(authToken), Object.class);

        if (result.getStatusCode() == HttpStatus.OK) {
            IntrospectionResponse response = objectMapper.convertValue(result.getBody(), IntrospectionResponse.class);
            log.info(response.toString());
        } else {
            IntrospectionErrorResponse response = objectMapper.convertValue(result.getBody(), IntrospectionErrorResponse.class);
            log.info(response.toString());
        }
        return result;
    }

    private String generateAuthToken(String s) {
        byte[] auth = Base64.getEncoder().encode(s.getBytes());
        String authToken = "Basic ".concat(new String(auth));
        log.info("The token is {}", authToken);
        return authToken;
    }

    private RequestEntity<Object> createRequestEntity(String authToken) throws URISyntaxException {
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        postParameters.add("token", "12333333");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        headers.add("Authorization", authToken);
        return RequestEntity
                .post(new URI(oAuthEndpoint))
                //.header("Authorization", token)
                .headers(headers)
                .body(postParameters);
    }
}
