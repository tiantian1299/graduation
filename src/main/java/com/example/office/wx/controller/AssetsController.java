package com.example.office.wx.controller;

import com.example.office.wx.common.util.R;
import com.example.office.wx.config.shiro.JwtUtil;
import com.example.office.wx.controller.form.DeleteForm;
import com.example.office.wx.controller.form.InsertAssetsForm;
import com.example.office.wx.controller.form.SearchPageForm;
import com.example.office.wx.controller.form.UpdateAssetsForm;
import com.example.office.wx.db.pojo.TbAssets;
import com.example.office.wx.service.AssetsService;
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
@RequestMapping("/assets")
@Api("采购申请模块Web接口")
public class AssetsController {

    @Autowired
    AssetsService assetsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/getAssetsListByPage")
    @ApiOperation("分页查询采购申请列表")
    public R getAssetsListByPage(@Valid @RequestBody SearchPageForm form) {
        int page = form.getPage();
        int length = form.getLength();
        long start = (page - 1) * length;
        HashMap map = new HashMap();
        map.put("start", start);
        map.put("length", length);
        ArrayList list = assetsService.getAssetsListByPage(map);
        return R.ok().put("result", list);
    }

    @PostMapping("/insertAssets")
    @ApiOperation("入库")
    public R insertAssets(@Valid @RequestBody InsertAssetsForm form, @RequestHeader("token") String token) {
        TbAssets entity = new TbAssets();
        entity.setAssetsName(form.getAssetsName());
        entity.setInventory(form.getInventory());
        entity.setCreateTime(new Date());
        entity.setCreatorId(jwtUtil.getUserId(token));

        assetsService.insertAssets(entity);
        return R.ok().put("result", "success");
    }

    @GetMapping("/searchAssetsById")
    @ApiOperation("根据ID查询会议")
    public R searchAssetsById(@RequestParam Integer id) {
        TbAssets entity = assetsService.searchAssetsById(id);
        return R.ok().put("result", entity);
    }

    @PostMapping("/editAssets")
    @ApiOperation("修改入库信息")
    public R editAssets(@Valid @RequestBody UpdateAssetsForm form, @RequestHeader("token") String token) {
        TbAssets entity = new TbAssets();
        entity.setId(form.getId());
        entity.setAssetsName(form.getAssetsName());
        entity.setInventory(form.getInventory());
        entity.setCreatorId(jwtUtil.getUserId(token));
        assetsService.updateAssets(entity);
        return R.ok().put("result", "success");
    }

    @PostMapping("/deleteAssetsById")
    @ApiOperation("根据ID删除入库信息")
    public R deleteAssetsById(@RequestBody DeleteForm form) {
        assetsService.deleteAssetsById(form.getId());
        return R.ok().put("result", "success");
    }

    @GetMapping("/searchAssetsList")
    @ApiOperation("查询采购申请列表")
    public R searchAssetsList() {
        ArrayList list = assetsService.searchAssetsList();
        return R.ok().put("result", list);
    }
}
