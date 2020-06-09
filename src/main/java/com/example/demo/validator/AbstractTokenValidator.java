package com.example.demo.validator;

import com.example.demo.service.AbstractService;
import org.springframework.util.StringUtils;

import static com.example.demo.constants.TokenConstants.OTHER_TOKEN_SYM;
import static com.example.demo.constants.TokenConstants.OTHER_TOKEN_SYM_PREFIX;


/**
 * @author yumingtao
 */
public abstract class AbstractTokenValidator {

    public AbstractTokenValidator(AbstractService abstractService) {
        abstractService.registerTokenValidator(this);
    }

    /**
     * Verify token
     * @param kid the kid value in JWT header
     * @return
     */
    public abstract String verify(String kid);

    /**
     * Check if the validator support the token with kid value in JWT header
     * @param kid kid the kid value in JWT header
     * @return true, support the token, else false
     */
    public abstract boolean isSupported(String kid);

    protected boolean isOtherToken(String kid){
        return StringUtils.isEmpty(kid)? false: (kid.equalsIgnoreCase(OTHER_TOKEN_SYM) || kid.toUpperCase().startsWith(OTHER_TOKEN_SYM_PREFIX));
    }
}
