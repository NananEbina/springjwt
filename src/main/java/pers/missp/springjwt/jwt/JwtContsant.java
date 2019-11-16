package pers.missp.springjwt.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtContsant {
    /**
     * 不能直接给静态变量用@Value赋值
     */
    static String SIGNINGKEY;
    static long EXPIRATION;

    @Value("${jwt.signingKey")
    public void setSIGNINGKEY(String signingkey){
        SIGNINGKEY = signingkey;
    }

    @Value("${jwt.expiration}")
    public void setEXPIRATION(long expiration){
        EXPIRATION = expiration;
    }
}
