package com.xun.elastic.configuration;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.apache.lucene.analysis.Analyzer;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeRequest;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse;
import org.elasticsearch.client.*;
import org.elasticsearch.index.analysis.Analysis;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.List;

/**
 * @author xun.guo
 */
@Slf4j
@Configuration
public class ElasticClient {

    private static final String HTTP_SCHEMA = "http";

    private static final Integer ELASTIC_PORT = 9200;

    private static final String IP_74 = "172.16.0.74";

    private static final String IP_87 = "172.16.0.87";

    private static final String IP_88 = "172.16.0.88";

    public RestClientBuilder clientBuilder = null;

    public RestHighLevelClient restHighLevelClient = null;

    private static final HttpHost[] hosts = {
            new HttpHost(IP_74, ELASTIC_PORT, HTTP_SCHEMA),
            new HttpHost(IP_87, ELASTIC_PORT, HTTP_SCHEMA),
            new HttpHost(IP_88, ELASTIC_PORT, HTTP_SCHEMA)
    };

    public ElasticClient() {
        log.info("Elasticsearch init in service");
        Header[] headers = new Header[]{new BasicHeader("header", "value")};
        clientBuilder = RestClient.builder(hosts)
                .setDefaultHeaders(headers)
                .setFailureListener(new RestClient.FailureListener() {
                    @Override
                    public void onFailure(Node node) {
                        log.error("节点地址：{} 不可用", node.getHost().getHostName());
                    }
                })
                .setNodeSelector(NodeSelector.SKIP_DEDICATED_MASTERS);
        restHighLevelClient = new RestHighLevelClient(clientBuilder);

    }

    @Bean
    public RestHighLevelClient getRestHighLevelClient() {
        return restHighLevelClient;
    }

    public static void main(String[] args) {


    }

}



























