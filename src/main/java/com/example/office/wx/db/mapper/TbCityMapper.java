package com.example.office.wx.db.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TbCityMapper {

    /**
     * 根据城市名字查询城市编码
     * @param city
     * @return
     */
    String searchCode(String city);
}