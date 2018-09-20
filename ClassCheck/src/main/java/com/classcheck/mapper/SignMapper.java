package com.classcheck.mapper;

import com.alibaba.fastjson.JSON;
import com.classcheck.model.Sign;
import com.classcheck.model.SignItem;
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

    @Select("select * from tb_sign where signid = #{id}")
    List<Sign> getsignbyid(@Param("id")String id);


    @Insert("insert into tb_sign(adminid,createtime,time,stulist,content) values (#{sign.adminid},#{sign.createtime},#{sign.time},#{sign.stulist},#{sign.content}) ")
    @Options(useGeneratedKeys = true, keyProperty = "sign.signid")
    void buildsign(@Param("sign")Sign sign);


    @Update("update tb_sign SET stulist = JSON_ARRAY_INSERT(stulist,'$[0]',#{item}) WHERE signid = #{signid}")
    int insertstusign(@Param("item") String item,@Param("signid")Integer signid);


    @Update("update tb_sign SET stulist = #{item}  WHERE signid = #{signid}")
    int insertsigndata(@Param("item") String item,@Param("signid")Integer signid);


}
