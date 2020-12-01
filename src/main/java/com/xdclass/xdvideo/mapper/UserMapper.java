package com.xdclass.xdvideo.mapper;

import com.xdclass.xdvideo.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author 10782
 * @date 2020-04-02 14:29
 **/
public interface UserMapper {

    /**
     * 根据主键id查找
     * @param userid
     * @return
     */
    @Select("select * from user where id = #{id}")
    User findById(@Param("id") int userid);

    /**
     * 根据openid查找
     * @param openid
     * @return
     */
    @Select("select * from user where openid = #{openid}")
    User findByOpenid(@Param("openid") String openid);


    /**
     * 保存用户新
     * @param user
     * @return
     */
    @Insert("INSERT INTO `user` ( `openid`, `name`, `head_img`, `phone`, `sign`, `sex`, `city`, `create_time`)" +
            "VALUES" +
            "(#{openid},#{name},#{headImg},#{phone},#{sign},#{sex},#{city},#{createTime});")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int save(User user);
}
