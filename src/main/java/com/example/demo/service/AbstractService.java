package com.example.demo.service;

import com.example.demo.validator.AbstractTokenValidator;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yumingtao
 */
public abstract class AbstractService {
    @Getter(value = AccessLevel.PROTECTED)
    private List<AbstractTokenValidator> tokenValidators = new ArrayList<>();

    public void registerTokenValidator(AbstractTokenValidator tokenValidator){
        this.tokenValidators.add(tokenValidator);
    }

    abstract String sayHello(String kid);
}
