package com.example.office.wx.service;

import com.example.office.wx.db.pojo.TbWeeklyReport;

import java.util.ArrayList;
import java.util.HashMap;

public interface WeeklyReportService {


    /**
     * 新增周报记录
     *
     * @param weeklyReport
     */
    void addWeeklyReport(TbWeeklyReport weeklyReport);


    /**
     * 分页查询周报信息
     *
     * @param param
     * @return
     */
    ArrayList<HashMap> getWeeklyReportListByPage(HashMap param);

    /**
     * 查询本周是否有周报
     * @return
     */
    TbWeeklyReport haveWeeklyReport(int userId);

    /**
     * 编辑周报
     * @param tbWeeklyReport
     */
    void editWeeklyReport(TbWeeklyReport tbWeeklyReport);

    /**
     * 删除周报
     * @param id
     */
    void deleteWeeklyReport(int id);


    /**
     * 根据周报id查询周报详情
     * @param id
     * @return
     */
    HashMap searchReportById(int id);
}
