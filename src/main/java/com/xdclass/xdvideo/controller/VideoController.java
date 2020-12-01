package com.xdclass.xdvideo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xdclass.xdvideo.domain.Video;
import com.xdclass.xdvideo.domain.VideoOrder;
import com.xdclass.xdvideo.service.VideoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 10782
 * @date 2020-03-27 15:23
 **/
@RestController
@RequestMapping("/api/v1/video")
public class VideoController {

    @Resource
    private VideoService videoService;


    /**
     * 分页接口
     * @param pageNum 当前第几页，默认为第一页
     * @param pageSize 每页显示几条，默认为10条
     * @return
     */
    @GetMapping("page")
    public Object pageVideo(@RequestParam(value = "page", defaultValue = "1")Integer pageNum,
                            @RequestParam(value = "size", defaultValue = "10") Integer pageSize){
        //还未写分页代码
        PageHelper.startPage(pageNum,pageSize);
        List<Video> videoList = videoService.findAll();
        PageInfo<Video> videoPageInfo = new PageInfo<>(videoList);
        return videoPageInfo;
    }

    /**
     * 根据ID查找视频
     * @param videoId
     * @return
     */
    @GetMapping("find_by_id")
    public Object findById(@RequestParam(value = "video_id", required = true) int videoId){
        return videoService.findById(videoId);
    }

}
