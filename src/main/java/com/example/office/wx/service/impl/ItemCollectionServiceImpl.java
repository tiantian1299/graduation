package com.example.office.wx.service.impl;

import com.example.office.wx.db.mapper.TbItemCollectionMapper;
import com.example.office.wx.service.ItemCollectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
@Slf4j
public class ItemCollectionServiceImpl implements ItemCollectionService {

    @Autowired
    TbItemCollectionMapper tbItemCollectionMapper;

    /**
     * 分页查询物品领用列表
     * @param param
     * @return
     */
    @Override
    public ArrayList<HashMap> getItemCollectionListByPage(HashMap param) {
        return tbItemCollectionMapper.getItemCollectionList(param);
    }
}
