package com.example.office.wx.db.mapper;

import com.example.office.wx.db.dto.TbWeeklyReportDto;
import com.example.office.wx.db.pojo.TbWeeklyReport;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.HashMap;

@Mapper
public interface TbWeeklyReportMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbWeeklyReport record);

    int insertSelective(TbWeeklyReport record);

    TbWeeklyReport selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbWeeklyReport record);

    int updateByPrimaryKey(TbWeeklyReport record);

    /**
     * 查询所有的周报列表
     * @return
     */
    ArrayList<TbWeeklyReportDto> getWeeklyReportList(HashMap map);

    TbWeeklyReport getWeeklyByDay(HashMap map);

    HashMap searchReportById (int id);

    ArrayList<HashMap> searchCcUser (int id);
}