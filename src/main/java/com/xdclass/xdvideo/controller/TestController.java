package com.xdclass.xdvideo.controller;

import com.xdclass.xdvideo.config.WeChatConfig;
import com.xdclass.xdvideo.mapper.VideoMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author 10782
 * @date 2020-03-26 00:07
 **/
@RestController
@RequestMapping("test")
public class TestController {

    @Resource
    private WeChatConfig weChatConfig;

    @Resource
    private VideoMapper videoMapper;

    @RequestMapping("test_config")
    public String testConfig(){

        System.out.println(weChatConfig.getAppId());
        System.out.println(weChatConfig.getAppsecret());
        return "hello xdclass.net";
    }

    @RequestMapping("test_db")
    public Object testDB(){
        return videoMapper.findAll();
    }

    @GetMapping("/{id}")
    public Object findUser(@PathVariable(value = "id") String userid){
        System.out.println("find user who id is: " + userid);
        return userid;
    }

    @PutMapping("/{id}")
    public Object updateUser(@PathVariable(value = "id") String userid){
        System.out.println("update user who id is: " + userid);
        return userid;
    }

    @GetMapping("/{id}/username")
    public Object findUserName(@PathVariable(value = "id") String userid){
        System.out.println("find user who id is: Mobo");
        return userid;
    }

}
