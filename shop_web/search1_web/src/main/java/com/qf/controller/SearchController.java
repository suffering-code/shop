package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Goods;
import com.qf.service.ISearchServer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/search")
public class SearchController {

    @Reference
    private ISearchServer searchServer;

    @RequestMapping(value = "/searchGoods")
    public String searchGoods(String keyWorld, ModelMap modelMap){
        System.out.println("keyWorld:"+keyWorld);
        List<Goods> goodsList = searchServer.searchByKeyWorld(keyWorld);
        modelMap.addAttribute("goodsList",goodsList);
        return "searchList";
    }
}
