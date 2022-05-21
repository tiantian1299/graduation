package com.example.office.wx.controller;

import com.example.office.wx.common.util.R;
import com.example.office.wx.config.shiro.JwtUtil;
import com.example.office.wx.controller.form.InsertItemCollectionForm;
import com.example.office.wx.controller.form.SearchPageForm;
import com.example.office.wx.db.pojo.TbAssets;
import com.example.office.wx.db.pojo.TbItemCollection;
import com.example.office.wx.exception.OfficeException;
import com.example.office.wx.service.AssetsService;
import com.example.office.wx.service.ItemCollectionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping("/itemCollection")
@Api("物品领用模块Web接口")
public class ItemCollectionController {

    @Autowired
    AssetsService assetsService;

    @Autowired
    ItemCollectionService itemCollectionService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/getItemCollectionListByPage")
    @ApiOperation("分页查询物品领用列表")
    public R getItemCollectionListByPage(@Valid @RequestBody SearchPageForm form) {
        int page = form.getPage();
        int length = form.getLength();
        long start = (page - 1) * length;
        HashMap map = new HashMap();
        map.put("start", start);
        map.put("length", length);
        ArrayList list = itemCollectionService.getItemCollectionListByPage(map);
        return R.ok().put("result", list);
    }

    @PostMapping("/insertItemCollection")
    @ApiOperation("物品领用")
    public R insertItemCollection(@Valid @RequestBody InsertItemCollectionForm form, @RequestHeader("token") String token) {
        TbAssets assets=assetsService.searchAssetsById(form.getItemName());
        if (form.getCount()==0){
            throw new OfficeException("领用数量不能为0");
        }
        if (form.getCount()>assets.getInventory()){
            throw new OfficeException("领用数量超过库存量");
        }
        TbItemCollection entity = new TbItemCollection();
        entity.setItemName(form.getItemName());
        entity.setCount(form.getCount());
        entity.setUse(form.getUse());
        entity.setReceivers(form.getMembers());
        entity.setHandleId(jwtUtil.getUserId(token));
        entity.setCreateTime(new Date());
        itemCollectionService.insertItemCollection(entity);

        assets.setInventory(assets.getInventory()-form.getCount());
        assetsService.updateAssets(assets);
        return R.ok().put("result", "success");
    }

    @GetMapping("/searchItemCollectionById")
    @ApiOperation("根据ID物品领用详情")
    public R searchMeetingById(@RequestParam Integer id) {
        HashMap map = itemCollectionService.searchItemCollectionById(id);
        return R.ok().put("result", map);
    }
}
