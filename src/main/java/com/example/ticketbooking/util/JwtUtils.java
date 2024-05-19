package com.example.ticketbooking.util;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.type.descriptor.DateTimeUtils;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;


@Slf4j
public class JwtUtils {
    private final static SecretKey secretKey = Jwts.SIG.HS256.key().build(); //or HS384.key() or HS512.key()
    private static final String ISSUER = "MJSF";

    private JwtUtils(){};
    public static boolean validateToken(String token) {
        log.info("inside the validateToken , token :{}",token);
        return parseToken(token) !=null;

    }

    public static String getUsernameFromToken(String token) {
        log.info("inside the getUsernameFromToken, token :: {} ",token);
        Claims claim = parseToken(token);
        if(claim!=null){
            return claim.getSubject();
        }
        log.info("getUsernameFromToken :: no claim found");
        return null;
    }

    private static Claims parseToken(String token) {
        log.info("inside the parseToken, token:: {} ",token);
        JwtParser jwtParser= Jwts.parser()
                .verifyWith(secretKey)
                .build();
        try{
            return jwtParser.parseSignedClaims(token)
                    .getPayload();
        } catch (JwtException e) {
        log.error("error occur while parsing the jwt token");
        }
        return null;
    }

    public static String generateToken(String username) {
        Date currentDate = new Date();
        Integer jwtExpirationInMinutes=10;
        Date jwtExpiration = DateUtils.addMinutes(currentDate,jwtExpirationInMinutes);
         return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .issuer(ISSUER)
                .subject(username)
                .signWith(secretKey)
                .issuedAt(currentDate)
                .expiration(jwtExpiration)
                 .compact();
    }
}
