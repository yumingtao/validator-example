package com.example.demo.service;

import com.example.demo.api.client.ClientApi;
import com.example.demo.validator.AbstractTokenValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Optional;


/**
 * @author yumingtao
 */
@Component
@Slf4j
public class HelloService extends AbstractService {

    private ClientApi clientApi;

    @Autowired
    public HelloService(ClientApi clientApi) {
        this.clientApi = clientApi;
    }

    @Override
    public String sayHello(String kid) {
        Optional<AbstractTokenValidator> tokenValidatorOptional = getTokenValidators().stream().filter(t -> t.isSupported(kid)).findFirst();
        if(tokenValidatorOptional.isPresent()){
            return tokenValidatorOptional.get().verify(kid);
        }
        return null;
    }

    public String sayHello3(String name){
        byte[] auth = Base64.getEncoder().encode("user:admin12345".getBytes());
        String token = "Basic ".concat(new String(auth));
        log.info("The token is {}", token);
        return clientApi.hello(token, name);
    }

    /*public static void main(String[] args) {
        System.out.println(new String(Base64.getEncoder().encode("user:admin12345".getBytes())));
    }*/
}
