package com.classcheck.mapper;

import com.classcheck.model.Admin;
import com.classcheck.model.Stu;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Auther: 简单DI年华
 * @Date: 18-8-27 19:43
 * @Description:
 */

@Mapper
@Component(value = "adminMapper")
public interface AdminMapper {

    @Select("select * from tb_admin where account =  #{account}")
    List<Admin> getPassword(@Param("account") String  account);
}
