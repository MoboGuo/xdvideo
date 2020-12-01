package com.xdclass.xdvideo.utils;

import com.xdclass.xdvideo.domain.User;
import io.jsonwebtoken.*;

import java.util.Date;

/**
 * jwt工具类
 * @author 10782
 * @date 2020-03-30 10:15
 **/
public class JwtUtils {

    public static final String SUBJECT = "xdclass";

    public static final long EXPIR = 1000*60*60*24*7;  //过期时间，毫秒，一周

    //秘钥
    public static final  String APPSECRET = "xd666";

    public static String geneJsonWebToken(User user){
        if(user == null || user.getId() == null || user.getName() == null
                || user.getHeadImg()==null){
            return null;
        }
        String token = Jwts.builder().setSubject(SUBJECT)
                .claim("id", user.getId())
                .claim("name", user.getName())
                .claim("img", user.getHeadImg())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIR))
                .signWith(SignatureAlgorithm.HS256, APPSECRET).compact();

        return token;
    }

    public static Claims checkJWT(String token){
        try {
            final Claims claims = Jwts.parser().setSigningKey(APPSECRET).parseClaimsJws(token).getBody();
            return claims;
        } catch (Exception e) { }
        return null;
    }
}
