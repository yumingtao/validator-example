package com.example.demo.validator;

import com.example.demo.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Description
 *
 * @author mingtao.yu@daimler.com
 * @date 2020/6/8 13:19
 */
@Component
public class KeycloakTokenValidator extends AbstractTokenValidator {

    @Autowired
    public KeycloakTokenValidator(AbstractService abstractService) {
        super(abstractService);
    }

    @Override
    public String verify(String kid) {
        return "Keycloak token verify.";
    }

    @Override
    public boolean isSupported(String kid) {
        return !isOtherToken(kid);
    }
}
