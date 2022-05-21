package com.example.office.wx.service;

import java.util.ArrayList;
import java.util.HashMap;

public interface ItemCollectionService {

    /**
     * 分页查询物品领用列表
     * @param param
     * @return
     */
    ArrayList<HashMap> getItemCollectionListByPage(HashMap param);
}
