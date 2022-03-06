package com.example.office.wx.db.mapper;

import com.example.office.wx.db.pojo.TbFaceModel;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface TbFaceModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbFaceModel record);

    int insertSelective(TbFaceModel record);

    TbFaceModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbFaceModel record);

    int updateByPrimaryKey(TbFaceModel record);
}