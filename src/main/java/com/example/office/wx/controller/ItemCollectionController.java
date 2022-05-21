package com.example.office.wx.controller;

import com.example.office.wx.common.util.R;
import com.example.office.wx.config.shiro.JwtUtil;
import com.example.office.wx.controller.form.SearchPageForm;
import com.example.office.wx.service.AssetsService;
import com.example.office.wx.service.ItemCollectionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
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
}
