package com.qf.listener;

import com.qf.entity.Goods;
import com.qf.service.ISearchServer;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SolrListener {

    @Autowired
    private ISearchServer searchServer;

    @RabbitListener(queues = "solr_queue")
    public void addSolr(Goods goods){
        // 添加到索引库
        searchServer.addSolrCore(goods);
    }
}
