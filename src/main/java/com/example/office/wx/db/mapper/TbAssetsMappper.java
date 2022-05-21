package com.example.office.wx.db.mapper;

import com.example.office.wx.db.pojo.TbAssets;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Mapper
public interface TbAssetsMappper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbAssets record);

    int insertSelective(TbAssets record);

    TbAssets selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbAssets record);

    int updateByPrimaryKey(TbAssets record);

    ArrayList<TbAssets> getAssetsList(HashMap map);

    ArrayList<TbAssets>searchAssetsList();
}