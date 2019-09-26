package com.qf.listener;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.controller.ItemController;
import com.qf.entity.Goods;
import com.qf.service.IGoodsService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@Component
public class ItemListener {

    @Reference
    private IGoodsService goodsService;

    @Autowired
    private Configuration configuration;

    @RabbitListener(queues = "item_queue")
    public void createHtml(Goods goodsParam){
        // 1.根据商品Id查询对象
        Goods goods = goodsService.getById(goodsParam.getId());

        Map<String,Object> map = new HashMap<String ,Object>();
        map.put("contextPath","/");
        map.put("picList",goods.getGpic().split("\\|")); // ?
        map.put("goods",goods);


        // 2.获取模板
        Template template = null;
        Writer writer =null;
        try {
            template = configuration.getTemplate("goodsItem.ftl");

            String path = ItemController.class.getClassLoader().getResource("static").getPath();

            File file = new File(path+ File.separator+"pages");

            // 如果没有就创建
            if(!file.exists()){
                file.mkdirs();
            }

//            /xxxxx/static/page/1.html
            // 3.设置生成页面的路径
            writer = new FileWriter( file.getAbsolutePath()+File.separator+goods.getId()+".html");

            // 4.根据模板和数据生成页面
            template.process(map,writer);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }finally {
            if(writer != null){
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
