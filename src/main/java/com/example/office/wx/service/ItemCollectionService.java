package com.example.office.wx.service;

import com.example.office.wx.db.pojo.TbItemCollection;

import java.util.ArrayList;
import java.util.HashMap;

public interface ItemCollectionService {

    /**
     * 分页查询物品领用列表
     * @param param
     * @return
     */
    ArrayList<HashMap> getItemCollectionListByPage(HashMap param);

    /**
     * 保存物品领用信息
     * @param entity
     */
    void insertItemCollection(TbItemCollection entity);

    /**
     * 查询物品领用详情
     * @param id
     * @return
     */
    HashMap searchItemCollectionById(int id);
}
