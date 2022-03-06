package com.example.office.wx.db.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;

@Mapper
public interface TbCheckinMapper {
    Integer haveCheckin(HashMap param);
}