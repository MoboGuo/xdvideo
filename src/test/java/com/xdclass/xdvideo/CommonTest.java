package com.xdclass.xdvideo;

import com.xdclass.xdvideo.domain.User;
import com.xdclass.xdvideo.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;

/**
 * @author 10782
 * @date 2020-03-30 10:55
 **/
public class CommonTest {

    @Test
    public void testGeneJwt(){
        User user = new User();
        user.setId(999);
        user.setHeadImg("www.xdclass.net");
        user.setName("xd");

        String token = JwtUtils.geneJsonWebToken(user);
        System.out.println(token);
    }

    @Test
    public void testCheck(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ4ZGNsYXNzIiwiaWQiOjk5OSwibmFtZSI6InhkIiwiaW" +
                "1nIjoid3d3LnhkY2xhc3MubmV0IiwiaWF0IjoxNTg1NTM3MDMyLCJleHAiOjE1ODYxNDE4MzJ9.aiYLX59NiGqxyR9FOOOqZ47Lj8qBXYTh7pzYkvwGyEE";
        Claims claims = JwtUtils.checkJWT(token);
        if(claims != null){
            String name = (String)claims.get("name");
            String img = (String)claims.get("img");
            int id =(Integer) claims.get("id");
            System.out.println(name);
            System.out.println(img);
            System.out.println(id);
        }else{
            System.out.println("非法token");
        }
    }
}
