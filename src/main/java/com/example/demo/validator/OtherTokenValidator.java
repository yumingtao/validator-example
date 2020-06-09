package com.example.demo.validator;

import com.example.demo.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author yumingtao
 */
@Component
public class OtherTokenValidator extends AbstractTokenValidator{

    @Autowired
    public OtherTokenValidator(AbstractService abstractService) {
        super(abstractService);
    }

    @Override
    public String verify(String kid) {
        return "Other token verify.";
    }

    @Override
    public boolean isSupported(String kid) {
        return isOtherToken(kid);
    }
}
