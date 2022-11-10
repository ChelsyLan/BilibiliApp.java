package com.bilibili.service.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.bilibili.domain.exception.ConditionException;

import java.util.Calendar;
import java.util.Date;

/*JWT:Json Web Token
* Safe delivery of "statements" in space-constrained situations
* parts:1.header(statement type, encryption algorithm)
* 2.payload(valid information) 3.signature*/
public class TokenUtil {
    private static final String ISSUER="LAN";

    public static String generateToken(Long userID) throws Exception{
        Algorithm algorithm=Algorithm.RSA256(RSAUtil.getPublicKey(),RSAUtil.getPrivateKey());
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND,90);
        return JWT.create().withKeyId(String.valueOf(userID)).
                withIssuer(ISSUER)
                .withExpiresAt(calendar.getTime())
                .sign(algorithm);
    }

    public static Long verifyToken(String token){
        try{
            Algorithm algorithm=Algorithm.RSA256(RSAUtil.getPublicKey(),RSAUtil.getPrivateKey());
            JWTVerifier verifier=JWT.require(algorithm).build();//generate JWTVerifier class
            DecodedJWT jwt=verifier.verify(token);
            String userID=jwt.getKeyId();
            return Long.valueOf(userID);
        }catch(TokenExpiredException e){
            throw new ConditionException("555","Token has expired.");
        }catch(Exception e){
            throw new ConditionException("Token is illegal");
        }



    }
}
