package com.example.office.wx.controller;

import com.example.office.wx.common.util.R;
import com.example.office.wx.config.shiro.JwtUtil;
import com.example.office.wx.controller.form.InsertAssetsForm;
import com.example.office.wx.controller.form.SearchPageForm;
import com.example.office.wx.db.pojo.TbAssets;
import com.example.office.wx.service.AssetsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
}
