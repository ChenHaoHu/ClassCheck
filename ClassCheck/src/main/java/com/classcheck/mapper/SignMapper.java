package com.classcheck.mapper;

import com.classcheck.model.Sign;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Auther: 简单DI年华
 * @Date: 18-8-27 19:45
 * @Description:
 */
@Mapper
@Component(value = "signMapper")
public interface SignMapper {

    @Select("select * from tb_sign where pass = #{pass}")
    List<Sign> getsignbypass(@Param("pass")String pass);

    @Insert("insert into tb_sign(adminid,createtime,endtime,stulist,content) values (#{sign.adminid},#{sign.createtime},#{sign.endtime},#{sign.stulist},#{sign.content}) ")
    @Options(useGeneratedKeys = true, keyProperty = "sign.pass")
    void insertsign(@Param("sign")Sign sign);
}
