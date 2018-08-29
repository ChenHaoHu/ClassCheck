package com.classcheck.mapper;

import com.classcheck.model.Stu;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Auther: 简单DI年华
 * @Date: 18-8-27 15:58
 * @Description:
 */

@Mapper
@Component(value = "stuMapper")
public interface StuMapper {

    @Insert("insert into tb_stu(name,stuid) values (#{stu.name},#{stu.stuid}) ")
    @Options(useGeneratedKeys = true, keyProperty = "stu.userid")
    void insertuser(@Param("stu") Stu stu);


    @Select("select * from tb_stu where userid = #{userid}")
    List<Stu> findnamebyuserid(@Param("userid") String userid);
}
