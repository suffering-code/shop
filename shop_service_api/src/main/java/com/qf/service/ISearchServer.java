package com.qf.service;

import com.qf.entity.Goods;

import java.util.List;

public interface ISearchServer {

    // 查询方法
    public List<Goods> searchByKeyWorld(String keyWorld);

    // 把商品添加到索引库
    public void addSolrCore(Goods goods);
}
