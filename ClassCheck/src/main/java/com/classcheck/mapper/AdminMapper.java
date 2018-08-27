package com.classcheck.mapper;

import com.classcheck.model.Stu;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Auther: 简单DI年华
 * @Date: 18-8-27 19:43
 * @Description:
 */
public interface AdminMapper {
    @Select("select * from tb_admin")
    void insertOrder(@Param("stu") Stu stu);
}
