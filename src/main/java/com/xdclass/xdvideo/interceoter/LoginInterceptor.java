package com.xdclass.xdvideo.interceoter;

import com.google.gson.Gson;
import com.xdclass.xdvideo.domain.JsonData;
import com.xdclass.xdvideo.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author 10782
 * @date 2020-04-03 08:52
 **/
public class LoginInterceptor implements HandlerInterceptor {

    private static final Gson gson = new Gson();

    /**
     * 进入controller之前进行拦截
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accessToken = request.getHeader("token");
        if(accessToken == null){
            accessToken = request.getParameter("token");
        }
        if(accessToken != null){
            Claims claims = JwtUtils.checkJWT(accessToken);
            if(claims != null){
                Integer id = (Integer) claims.get("id");
                String name = (String) claims.get("name");
                request.setAttribute("user_id",id);
                request.setAttribute("name",name);
                //普通用户
                return true;
            }
        }
        sendJsonMessage(response, JsonData.buildError("请登录"));
        return false;
    }

    /**
     * 响应数据给前端
     * @param response
     * @param object
     */
    public static void sendJsonMessage(HttpServletResponse response, Object object) throws IOException {
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.println(gson.toJson(object));
        writer.close();
        response.flushBuffer();
    }
}
