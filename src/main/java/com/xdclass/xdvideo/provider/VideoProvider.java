package com.xdclass.xdvideo.provider;

import com.xdclass.xdvideo.domain.Video;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.jdbc.SQL;

/**
 * video构建动态sql语句
 * @author 10782
 * @date 2020-03-29 23:24
 **/
public class VideoProvider {

    public String updateVideo(final Video video){
         return new SQL(){{
             UPDATE("video");

             //条件写法
             if(video.getTitle() != null){
                 SET("title=#{title}");
             }
             if(video.getSummary() != null){
                 SET("summary=#{summary}");
             }
             if(video.getCoverImg()!=null){
                 SET("cover_img=#{coverImg}");
             }
             if(video.getViewNum()!=null){
                 SET("view_num=#{viewNum}");
             }
             if(video.getPrice()!=null){
                 SET("price=#{price}");
             }
             if(video.getOnline()!=null){
                 SET("online=#{online}");
             }
             if(video.getPoint()!=null){
                 SET("point=#{point}");
             }

             WHERE("id=#{id}");
         }}.toString();
    }
}
