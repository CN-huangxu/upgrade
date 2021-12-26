package com.example.demo1.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 */
@Component
public class JwtUtils {

    private static final String CLAIM_KEY_ID = "id";

    private static final String CLAIM_KEY_USERNAME = "username";

    private static final String CLAIM_KEY_PHONE = "phone";

    private static final String CLAIM_KEY_CREATED = "createTime";

    private static final String CLAIM_KEY_TYPE = "type";

    /**
     * 通过@Value将外部的值动态注入到Bean中
     * 这里是： @Value将外部配置文件的值动态注入到Bean
     * */
    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.token.expired}")
    private Long expired;

    private Key key;

    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generateToken(UserDetail userDetail) {
        Map<String, Object> claims = new HashMap<>(5);
        claims.put(CLAIM_KEY_USERNAME, userDetail.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + expired))
                .signWith(key)
                .compact();
    }


    /**
     * 获取过期时间戳
     *
     * @param token
     * @return
     */
    public long getExpirationTime(String token) {
        final Claims claims = getClaimsFromToken(token);
        if (claims == null) {
            return 0;
        }
        return claims.getExpiration().getTime();
    }

    /**
     * 从token中获取username,如果过期则抛出异常
     *
     * @param token token
     * @return username
     */
    public String getUsernameFromToken(String token) {
        try {
            final Claims claims = getClaimsFromToken(token);
            if (claims == null) {
                return null;
            }
            Date expiration = claims.getExpiration();
            if (new Date().after(expiration)) {
                throw new InsufficientAuthenticationException("session timeout");
            }
            return (String) claims.get(CLAIM_KEY_USERNAME);
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 从token中获取用户信息,如果过期则抛出异常
     *
     * @param token token
     * @return UserDetail
     */
    public UserDetail getUserDetailFromToken(String token) {
        try {
            final Claims claims = getClaimsFromToken(token);
            if (claims == null) {
                return null;
            }
            Date expiration = claims.getExpiration();
            if (new Date().after(expiration)) {
                throw new InsufficientAuthenticationException("session timeout");
            }
            String username = (String) claims.get(CLAIM_KEY_USERNAME);
            Integer type = (Integer) claims.get(CLAIM_KEY_TYPE);
            String phone = (String) claims.get(CLAIM_KEY_PHONE);
            Long id = (Long) claims.get(CLAIM_KEY_ID);
            UserDetail userDetail = new UserDetail();
            userDetail.setUsername(username);
            userDetail.setId(id);
            return userDetail;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
        return claims;
    }

}