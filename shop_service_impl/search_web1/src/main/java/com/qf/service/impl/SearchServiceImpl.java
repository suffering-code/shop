package com.qf.service.impl;

import com.qf.entity.Goods;
import com.qf.service.ISearchServer;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.service.ISearchServer;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SearchServiceImpl implements ISearchServer {

    // 连接Solr查询

    @Autowired
    private SolrClient client;

    @Override
    public List<Goods> searchByKeyWorld(String keyWorld) {

        List<Goods> goodsList = new ArrayList<Goods>();
        // 1.先设置查询条件
        SolrQuery solrQuery = new SolrQuery();
        if(StringUtils.isEmpty(keyWorld)){
            solrQuery.setQuery("gname:* || gdesc:*"); // 用户没有输入条件就查询所有
        }else{
            String query ="gname:%s || gdesc:%s";
            solrQuery.setQuery(String.format(query,keyWorld,keyWorld)); // 用户没有输入条件就查询所有
        }
        //1sethightlight
        solrQuery.setHighlight(true);
        solrQuery.setHighlightSimplePre("<font color = 'green'>");
        solrQuery.setHighlightSimplePost("</font>");
        solrQuery.addHighlightField("gname");

        // 2.查询
        try {
            QueryResponse queryResponse = client.query(solrQuery);
            SolrDocumentList solrDocumentList = queryResponse.getResults();

            Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();

            for(SolrDocument sd:solrDocumentList){

                // a.先从SolrDocument获取数据
                String id = sd.getFieldValue("id").toString();
                String gname = sd.getFieldValue("gname").toString();
                String gdesc = sd.getFieldValue("gdesc").toString();
                String gpic= sd.getFieldValue("gpic").toString();
                //  String gstock= sd.getFieldValue("gstock").toString();
                String gprice= sd.getFieldValue("gprice").toString();

                //  b.封装成对象
                Goods goods = new Goods();


                goods.setId(Integer.parseInt(id));
                goods.setGname(gname);
                goods.setGdesc(gdesc);
                goods.setGpic(gpic);
                goods.setGprice(Double.parseDouble(gprice));
                // goods.setGstock(Integer.parseInt(gstock));

                if (highlighting.containsKey(id)){
                    Map<String, List<String>> filedMap = highlighting.get(id);
                    List<String> stringList = filedMap.get("gname");
                    if (stringList!=null){
                        String s = stringList.get(0);
                        goods.setGname(s);
                    }

                }
                // c.添加到集合中
                goodsList.add(goods);
            }
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return goodsList;
    }

    @Override
    public void addSolrCore(Goods goods) {
        SolrInputDocument document = new SolrInputDocument();
        document.addField("gname",goods.getGname());
        document.addField("gdesc",goods.getGdesc());
        document.addField("gprice",goods.getGprice());
        document.addField("gpic",goods.getGpic());

        // 有ID就执行更新，没有Id执行插入操作
        document.addField("id",goods.getId());

        // 2.把document添加索引库
        try {
            client.add(document); // update/add
            client.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
