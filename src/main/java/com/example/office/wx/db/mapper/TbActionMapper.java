package com.example.office.wx.db.mapper;

import com.example.office.wx.db.pojo.TbAction;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface TbActionMapper {
   
    int deleteByPrimaryKey(Integer id);

    int insert(TbAction record);

    int insertSelective(TbAction record);

    TbAction selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbAction record);
    
    //测试
    int updateByPrimaryKey(TbAction record);
    
}
