package com.example.demo;


import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

@SpringBootTest
public class EsDemo {

    //从ES中查询数据
    @Test
    public void test1() throws UnknownHostException {
        //指定ES集群
        Settings setting = Settings.builder().put("cluster.name",
                "my-application").build();

        //创建访问ES服务器的客户端
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(
                        new TransportAddress(
                                InetAddress.getByName("127.0.0.1"),9300));
        //get方式数据查询 ,参数为Index,type和id
        GetResponse response = client.prepareGet("lib5","testadd","XxavC24BhQWNCPuYx7pA").get();

        System.out.println(response.getSourceAsString());
        client.close();
    }

    //插入数据
    @Test
    public void test2() throws IOException {
        //指定ES集群
        Settings setting = Settings.builder().put("cluster.name",
                "my-application").build();

        //创建访问ES服务器的客户端
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(
                        new TransportAddress(
                                InetAddress.getByName("127.0.0.1"),9300));

        XContentBuilder doc = XContentFactory.jsonBuilder()
                .startObject()
                .field("id","1")
                .field("title","我在学习es插入操作")
                .field("content","好好学习，天天向上")
                .endObject();

        //添加一个doc
        IndexResponse response = client.prepareIndex("lib5","testadd",null)//id为null，由ES自己生成
                .setSource(doc).get();
        System.out.println(response);
        System.out.println(response.status());//打印添加是否成功
        client.close();
    }

    //删除文档
    @Test
    public void test3() throws IOException {
        //指定ES集群
        Settings setting = Settings.builder().put("cluster.name",
                "my-application").build();

        //创建访问ES服务器的客户端
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(
                        new TransportAddress(
                                InetAddress.getByName("127.0.0.1"),9300));

        DeleteResponse response = client.prepareDelete("lib5","testadd","dympT2cBfvwZhJjhaFQ3")
                .get();
        System.out.println(response.status());//打印添加是否成功
        client.close();
    }


    //更新文档
    @Test
    public void test4() throws IOException, ExecutionException, InterruptedException {
        //指定ES集群
        Settings setting = Settings.builder().put("cluster.name",
                "my-application").build();

        //创建访问ES服务器的客户端
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(
                        new TransportAddress(
                                InetAddress.getByName("127.0.0.1"),9300));
        UpdateRequest request = new UpdateRequest();
        request.index("lib5")
                .type("testadd")
                .id("eCm1T2cBfvwZhJjhF1SM")
                .doc(
                        XContentFactory.jsonBuilder().startObject()
                                .field("title","我在学习ES的修改操作")
                                .field("newadd","新增字段")
                                .endObject()
                );
        UpdateResponse response = client.update(request).get();

        System.out.println(response.status());//打印是否成功
        client.close();
    }

    @SuppressWarnings({ "unchecked", "resource" })
    public static void main(String[] args) throws Exception {
        Settings settings = Settings.builder()
                .put("cluster.name", "elasticsearch")
                .build();

        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));

//		prepareData(client);
        executeSearch(client);

        client.close();

    }

    /**
     * 执行搜索操作
     * @param client
     */
    private static void executeSearch(TransportClient client) {
        SearchResponse response = client.prepareSearch("company")
                .setTypes("employee")
                .setQuery(QueryBuilders.matchQuery("position", "technique"))
                .setPostFilter(QueryBuilders.rangeQuery("age").from(20).to(40))
                .setFrom(0).setSize(5)
                .get();

        SearchHit[] searchHits = response.getHits().getHits();
        for(int i = 0; i < searchHits.length; i++) {
            System.err.println(searchHits[i].getSourceAsString());
        }
    }

    /**
     * 准备数据
     * @param client
     */
    private static void prepareData(TransportClient client) throws Exception {
        client.prepareIndex("company", "employee", "11")
                .setSource(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("name", "jack")
                        .field("age", 27)
                        .field("position", "软件工程师我们")
                        .field("country", "china")
                        .field("join_date", "2017-01-01")
                        .field("salary", 10000)
                        .endObject())
                .get();

        client.prepareIndex("company", "employee", "12")
                .setSource(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("name", "marry")
                        .field("age", 35)
                        .field("position", "软件工程师管理师他")
                        .field("country", "china")
                        .field("join_date", "2017-01-01")
                        .field("salary", 12000)
                        .endObject())
                .get();

        client.prepareIndex("company", "employee", "13")
                .setSource(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("name", "tom")
                        .field("age", 32)
                        .field("position", "php开发工程师你")
                        .field("country", "china")
                        .field("join_date", "2016-01-01")
                        .field("salary", 11000)
                        .endObject())
                .get();

        client.prepareIndex("company", "employee", "14")
                .setSource(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("name", "jen")
                        .field("age", 25)
                        .field("position", "产品经理打的")
                        .field("country", "usa")
                        .field("join_date", "2016-01-01")
                        .field("salary", 7000)
                        .endObject())
                .get();

        client.prepareIndex("company", "employee", "15")
                .setSource(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("name", "mike")
                        .field("age", 37)
                        .field("position", "产品总监大大")
                        .field("country", "usa")
                        .field("join_date", "2015-01-01")
                        .field("salary", 15000)
                        .endObject())
                .get();
    }
}
