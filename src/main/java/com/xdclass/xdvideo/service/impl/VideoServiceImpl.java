package com.xdclass.xdvideo.service.impl;

import com.xdclass.xdvideo.domain.Video;
import com.xdclass.xdvideo.mapper.VideoMapper;
import com.xdclass.xdvideo.service.VideoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 10782
 * @date 2020-03-27 15:01
 **/
@Service
public class VideoServiceImpl implements VideoService {
    @Resource
    private VideoMapper videoMapper;


    @Override
    public List<Video> findAll() {
        return videoMapper.findAll();
    }

    @Override
    public Video findById(int id) {
        return videoMapper.findById(id);
    }

    @Override
    public int update(Video video) {
        return videoMapper.update(video);
    }

    @Override
    public int delete(int id) {
        return videoMapper.delete(id);
    }

    @Override
    public int save(Video video) {
        int rows = videoMapper.save(video);
        System.out.println("保存对象的id= "+video.getId());

        return rows;
    }

}
