package com.example.office.wx.db.mapper;

import com.example.office.wx.db.pojo.SysConfig;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysConfigMapper {

    /**
     * 查询所有常量参数
     * @return
     */
    public List<SysConfig> selectAllParam();
}