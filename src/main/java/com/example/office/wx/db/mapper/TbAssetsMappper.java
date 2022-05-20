package com.example.office.wx.db.mapper;

import com.example.office.wx.db.pojo.TbAssets;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TbAssetsMappper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbAssets record);

    int insertSelective(TbAssets record);

    TbAssets selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbAssets record);

    int updateByPrimaryKey(TbAssets record);
}