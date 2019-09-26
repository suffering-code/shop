package com.qf.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.qf.dao.IGoodsDao;
import com.qf.entity.Goods;
import com.qf.service.IGoodsService;
import com.qf.service.ISearchServer;
import com.qf.utils.HttpUitls;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;

@Service
public class GoodsServiceImpl extends BaseServiecImpl<Goods> implements IGoodsService {

    @Autowired
    private IGoodsDao goodsDao;

    @Autowired
    private RabbitTemplate template;

    @Override
    protected Mapper<Goods> getMapper() {
        return goodsDao;
    }

    @Override
    public int add(Goods goods) {
        int insert = goodsDao.insert(goods);

        // 2.添加商品到索引库
        //searchServer.addSolrCore(goods);

        // 3.生成静态页面 发送一个请求
        // http://localhost:80803/item/createHtml?gid=goods.getId();
        //HttpUitls.sendRequset("http://localhost:8083/item/createHtml?gid="+goods.getId());
        template.convertAndSend("goodsExchange","",goods);
        System.out.println("GoodsServiceImpl.add 添加交换机成功");
        return insert;
    }
}
