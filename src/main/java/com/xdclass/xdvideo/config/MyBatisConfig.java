package com.xdclass.xdvideo.config;

import com.github.pagehelper.PageHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @author 10782
 * @date 2020-03-30 00:44
 **/
//@Configuration
public class MyBatisConfig {
    @Bean
    public PageHelper pageHelper(){
        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        //设置为true时，会将rowbounds第一个参数offset当成pageNum页码使用
        p.setProperty("offsetAsPageNum","true");
        //设置为true时,使用RowBounds会进行count查询
        p.setProperty("rowBoundsWithCount","true");
        p.setProperty("pageSizeZero","true");
        p.setProperty("reasonable","true");
        pageHelper.setProperties(p);
        return pageHelper;
    }
}
