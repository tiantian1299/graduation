package com.example.office.wx.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONArray;
import com.example.office.wx.db.dto.TbWeeklyReportDto;
import com.example.office.wx.db.mapper.TbWeeklyReportMapper;
import com.example.office.wx.db.pojo.TbWeeklyReport;
import com.example.office.wx.exception.OfficeException;
import com.example.office.wx.service.WeeklyReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

@Service
@Slf4j
public class WeeklyReportServiceImpl implements WeeklyReportService {

    @Autowired
    TbWeeklyReportMapper tbWeeklyReportMapper;

    /**
     * 新增周报记录
     *
     * @param weeklyReport
     */
    @Override
    public void addWeeklyReport(TbWeeklyReport weeklyReport) {
        weeklyReport.setCreateDate(new Date());
        int i = tbWeeklyReportMapper.insert(weeklyReport);
        if (i != 1) {
            throw new OfficeException("新增周报失败");
        }
    }

    /**
     * 分页查询周报信息
     *
     * @param param
     * @return
     */
    @Override
    public ArrayList<HashMap> getWeeklyReportListByPage(HashMap param) {
        ArrayList<TbWeeklyReportDto> weeklyReportList = tbWeeklyReportMapper.getWeeklyReportList(param);
        String date = null;
        ArrayList resultList = new ArrayList();
        HashMap resultMap;
        JSONArray array = null;
        DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        for (TbWeeklyReportDto report : weeklyReportList) {
            String temp = dateformat.format(report.getCreateDate());
            if (!temp.equals(date)) {
                date = temp;
                resultMap = new HashMap();
                array = new JSONArray();
                resultMap.put("date", date);
                resultMap.put("list", array);
                resultList.add(resultMap);
            }
            array.put(report);
        }
        return resultList;
    }

    /**
     * 查询本周是否有周报
     * @return
     */
    @Override
    public TbWeeklyReport haveWeeklyReport(int userId){
        //一周的开始时间和结束时间
        DateTime startTime = DateUtil.beginOfWeek(DateUtil.date());
        DateTime endTime = DateUtil.endOfWeek(DateUtil.date());
        HashMap map = new HashMap();
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        map.put("userId",userId);
        TbWeeklyReport weeklyByDay = tbWeeklyReportMapper.getWeeklyByDay(map);
        return weeklyByDay;

    }

    /**
     * 编辑周报
     * @param tbWeeklyReport
     */
    @Override
    public void editWeeklyReport(TbWeeklyReport tbWeeklyReport){
        tbWeeklyReport.setCreateDate(new Date());
        int i = tbWeeklyReportMapper.updateByPrimaryKeySelective(tbWeeklyReport);
        if (i!=1){
            throw new OfficeException("编辑周报失败");
        }
    }

    /**
     * 删除周报
     * @param id
     */
    @Override
    public void deleteWeeklyReport(int id){
        // 查询是否存在周报
        TbWeeklyReport report = tbWeeklyReportMapper.selectByPrimaryKey(id);
        if (Objects.isNull(report)){
            throw new OfficeException("不存在周报记录");
        }else{
            int i = tbWeeklyReportMapper.deleteByPrimaryKey(id);
            if (i!=1){
                throw new OfficeException("删除周报失败");
            }
        }
    }

    /**
     * 根据周报id查询周报详情
     *
     * @param id
     * @return
     */
    @Override
    public HashMap searchReportById(int id) {
        HashMap map = tbWeeklyReportMapper.searchReportById(id);
        ArrayList<HashMap> hashMaps = tbWeeklyReportMapper.searchCcUser(id);
        map.put("members", hashMaps);
        return map;
    }
}
