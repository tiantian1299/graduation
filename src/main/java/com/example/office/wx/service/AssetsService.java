package com.example.office.wx.service;

import com.example.office.wx.db.pojo.TbAssets;

import java.util.ArrayList;
import java.util.HashMap;

public interface AssetsService {

    /**
     * 分页查询采购申请信息
     *
     * @param param
     * @return
     */
    ArrayList<TbAssets> getAssetsListByPage(HashMap param);

    /**
     * 入库
     *
     * @param entity
     */
    void insertAssets(TbAssets entity);

    /**
     * 根据主键查询采购申请信息
     *
     * @param id
     * @return
     */
    TbAssets searchAssetsById(int id);

    /**
     * 修改库存信息
     *
     * @param entity
     */
    void updateAssets(TbAssets entity);

    /**
     * 删除库存信息
     *
     * @param id
     */
    void deleteAssetsById(Integer id);
}
