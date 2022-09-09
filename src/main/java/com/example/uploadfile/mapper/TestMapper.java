package com.example.uploadfile.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created: by 2022-09-09 12:37
 * Author: 张松
 * Description:
 */
@Mapper
public interface TestMapper {

    //添加URL
    int addUrl(@Param("httpUrl") String url);
}
