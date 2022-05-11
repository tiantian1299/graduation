package com.example.office.wx.db.mapper;

import com.example.office.wx.db.pojo.TbItemCollection;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TbItemCollectionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbItemCollection record);

    int insertSelective(TbItemCollection record);

    TbItemCollection selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbItemCollection record);

    int updateByPrimaryKey(TbItemCollection record);
}