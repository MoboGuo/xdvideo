package com.xdclass.xdvideo.mapper;

import com.xdclass.xdvideo.domain.VideoOrder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author 10782
 * @date 2020-04-03 19:05
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class VideoOrderMapperTest {

    @Resource
    private VideoOrderMapper videoOrderMapper;

    @Test
    public void insert() {
        VideoOrder videoOrder = new VideoOrder();
        videoOrder.setCreateTime(new Date());
        videoOrder.setNotifyTime(new Date());
        videoOrder.setNickname("mobo");
        videoOrder.setState(0);
        videoOrder.setDel(0);
        videoOrderMapper.insert(videoOrder);
        Assert.assertNotNull(videoOrder.getId());
    }

    @Test
    public void findById() {
        VideoOrder videoOrder = videoOrderMapper.findById(1);
        Assert.assertNotNull(videoOrder);
    }

    @Test
    public void findByOutTradeNo() {
    }

    @Test
    public void del() {
        int i = videoOrderMapper.del(5,1);
        Assert.assertEquals(1,(long)i);
    }

    @Test
    public void findMyOrderList() {
        List<VideoOrder> list = videoOrderMapper.findMyOrderList(1);
        Assert.assertNotNull(list);
    }

    @Test
    public void updateVideoOrderByOutTradeNo() {
    }
}