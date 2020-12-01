package com.xdclass.xdvideo.controller.admin;

import com.xdclass.xdvideo.domain.Video;
import com.xdclass.xdvideo.service.VideoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author 10782
 * @date 2020-03-29 22:34
 **/
@RestController
@RequestMapping("/admin/api/v1/video")
public class VideoAdminController {

    @Resource
    private VideoService videoService;

    /**
     * 根据ID删除视频
     * @param videoId
     * @return
     */
    @DeleteMapping("del_by_id")
    public Object delById(@RequestParam(value = "video_id",required = true)int videoId){

        return videoService.delete(videoId);
    }


    /**
     * 根据id更新视频
     * @param video
     * @return
     */
    @PutMapping("update_by_id")
    public Object update(@RequestBody Video video){
        return videoService.update(video);
    }

    /**
     * 保存视频对象
     * @param video
     * @return
     */
    @PostMapping("save")
    public Object save(@RequestBody Video video){
        return videoService.save(video);
    }
}
