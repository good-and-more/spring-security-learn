package com.atguigu.security.security;

import io.jsonwebtoken.CompressionCodec;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Component
public class TokenManager {

    //token有效时常
    private long tokenExpiration = 24*60*60*1000;

    //编码密钥
    private String tokenSignKey = "123456";

    //1,使用jwt根据用户名生成token
    public String createToken(String username) {
        String token = Jwts.builder().setSubject(username)
                //.setExpiration(new Date(System.currentTimeMillis()+ tokenExpiration))
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .signWith(SignatureAlgorithm.HS512, tokenSignKey)
                .compressWith(CompressionCodecs.GZIP)
                .compact();
        return token;
    }

    //2.根据token字符串获得用户信息  此处疑问,生成的时候只用了用户名和过期时间,那获得用户信息会得到什么
    public String getUserInfoFromToken(String token) {
        return Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token).getBody().getSubject();
    }

    //3.删除该token  此处疑问,删除token不是应该直接去redis里删吗
    public void removeToken(String token) {

    }
}
