package com.xdclass.xdvideo.service;

import com.xdclass.xdvideo.domain.Video;

import java.util.List;

/**
 * @author 10782
 * @date 2020-03-27 15:01
 **/
public interface VideoService {

    List<Video> findAll();

    Video findById(int id);

    int update(Video Video);

    int delete(int id);

    int save(Video video);
}
