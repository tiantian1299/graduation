package com.example.office.wx.service.impl;

import com.example.office.wx.db.mapper.TbAssetsMappper;
import com.example.office.wx.db.pojo.TbAssets;
import com.example.office.wx.service.AssetsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class AssetsServiceImpl implements AssetsService {

    @Autowired
    TbAssetsMappper tbAssetsMappper;

    /**
     * 分页查询采购申请信息
     *
     * @param param
     * @return
     */
    @Override
    public ArrayList<TbAssets> getAssetsListByPage(HashMap param) {
        ArrayList<TbAssets> tbAssets = tbAssetsMappper.getAssetsList(param);
        return tbAssets;
    }

    /**
     * 入库
     *
     * @param entity
     */
    @Override
    public void insertAssets(TbAssets entity) {
        tbAssetsMappper.insertSelective(entity);
    }

    /**
     * 根据主键查询采购申请信息
     *
     * @param id
     * @return
     */
    @Override
    public TbAssets searchAssetsById(int id) {
        return tbAssetsMappper.selectByPrimaryKey(id);
    }

    /**
     * 修改库存信息
     *
     * @param entity
     */
    @Override
    public void updateAssets(TbAssets entity) {
        tbAssetsMappper.updateByPrimaryKeySelective(entity);
    }

    /**
     * 删除库存信息
     *
     * @param id
     */
    @Override
    public void deleteAssetsById(Integer id) {
        tbAssetsMappper.deleteByPrimaryKey(id);
    }

    /**
     * 查询库存列表
     * @return
     */
    @Override
    public ArrayList<TbAssets> searchAssetsList() {
        return tbAssetsMappper.searchAssetsList();
    }
}
