package com.xdclass.xdvideo.service.impl;

import com.xdclass.xdvideo.config.WeChatConfig;
import com.xdclass.xdvideo.domain.User;
import com.xdclass.xdvideo.mapper.UserMapper;
import com.xdclass.xdvideo.service.UserService;
import com.xdclass.xdvideo.utils.HttpUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

/**
 * @author 10782
 * @date 2020-04-02 10:34
 **/
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private WeChatConfig weChatConfig;

    @Resource
    private UserMapper userMapper;

    @Override
    public User saveWeChatUser(String code) {
        String accessTokenUrl = String.format(WeChatConfig.getOpenAccessTokenUrl(), weChatConfig.getOpenAppid(), weChatConfig.getOpenAppsecret(), code);

        //获取access_token
        Map<String,Object> baseMap = HttpUtils.doGet(accessTokenUrl);
        String accessToken = (String)baseMap.get("access_token");
        String openId  = (String) baseMap.get("openid");

        //获取用户基本信息
        String userInfoUrl = String.format(WeChatConfig.getOpenUserInfoUrl(),accessToken,openId);

        Map<String ,Object> baseUserMap =  HttpUtils.doGet(userInfoUrl);

        if(baseUserMap == null || baseUserMap.isEmpty()){ return  null; }
        String nickname = (String)baseUserMap.get("nickname");

        User dbUser = userMapper.findByOpenid(openId);

        if(dbUser!=null) { //更新用户，直接返回
            return dbUser;
        }

        Double sexTemp  = (Double) baseUserMap.get("sex");
        int sex = sexTemp.intValue();
        String province = (String)baseUserMap.get("province");
        String city = (String)baseUserMap.get("city");
        String country = (String)baseUserMap.get("country");
        String headimgurl = (String)baseUserMap.get("headimgurl");
        StringBuilder sb = new StringBuilder(country).append("||").append(province).append("||").append(city);
        String finalAddress = sb.toString();
        try {
            //解决乱码
            nickname = new String(nickname.getBytes("ISO-8859-1"), "UTF-8");
            finalAddress = new String(finalAddress.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        User user = new User();
        user.setName(nickname);
        user.setHeadImg(headimgurl);
        user.setCity(finalAddress);
        user.setOpenid(openId);
        user.setSex(sex);
        user.setCreateTime(new Date());
        userMapper.save(user);
        return user;
    }

}
