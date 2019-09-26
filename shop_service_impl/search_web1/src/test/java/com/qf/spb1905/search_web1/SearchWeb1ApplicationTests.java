package com.qf.spb1905.search_web1;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchWeb1ApplicationTests {
    @Autowired
    private SolrClient solrClient;
    @Test
    public void contextLoads() {
    }
    @Test
    public  void delete() throws IOException, SolrServerException {
		solrClient.deleteById("8e149fde-78e5-4a66-bc9a-b13b378e804e");

        solrClient.commit();
    }
}
