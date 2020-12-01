package com.xdclass.xdvideo.service;

import com.xdclass.xdvideo.domain.User;

/**
 * 用户业务接口
 * @author 10782
 * @date 2020-04-02 10:31
 **/
public interface UserService {

    User saveWeChatUser(String code);

}
