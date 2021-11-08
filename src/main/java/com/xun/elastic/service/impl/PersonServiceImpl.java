package com.xun.elastic.service.impl;

import com.xun.elastic.constants.IndexConstants;
import com.xun.elastic.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.TermQuery;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeRequest;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xun.guo
 */
@Slf4j
@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;


    @Override
    public SearchResponse querySearch(String fieldName, String value) {
        TermQueryBuilder termQueryBuilder = new TermQueryBuilder(fieldName, value);
//        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder(fieldName, value);
//        RangeQueryBuilder rangeQueryBuilder = new RangeQueryBuilder(fieldName);
//        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(termQueryBuilder);
        SearchRequest searchRequest = new SearchRequest();
        SearchResponse searchResponse = null;
        searchRequest.indices(IndexConstants.PERSON_INDEX.getIndexName()).source(searchSourceBuilder);
        try {
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return searchResponse;

    }

    @Override
    public GetResponse search() throws IOException {
        GetRequest getIndexRequest = new GetRequest(IndexConstants.PERSON_INDEX.getIndexName());
        getIndexRequest.id("1");
        GetResponse getResponse = restHighLevelClient.get(getIndexRequest, RequestOptions.DEFAULT);
        return getResponse;

    }

    @Override
    public void analysisTemplate() {
        AnalyzeRequest analyzeRequest = new AnalyzeRequest();
        analyzeRequest.text("当以异步方式执行AnalyzeRequest时，高级客户端不必同步等待请求结果的返回，可以直接向接口调用方返回异步执行成功的结果");
        analyzeRequest.analyzer("standard");
        ActionListener<AnalyzeResponse> listener = new ActionListener<AnalyzeResponse>() {
            @Override
            public void onResponse(AnalyzeResponse analyzeTokens) {
                List<AnalyzeResponse.AnalyzeToken> tokens = analyzeTokens.getTokens();
                for (AnalyzeResponse.AnalyzeToken token : tokens) {
                    System.out.println(token.getTerm());
                }
            }

            @Override
            public void onFailure(Exception e) {
                log.error(e.getMessage());
            }
        };
        restHighLevelClient.indices().analyzeAsync(analyzeRequest, RequestOptions.DEFAULT, listener);
    }

    @Override
    public CreateIndexResponse createIndex() {
        CreateIndexRequest request = new CreateIndexRequest(IndexConstants.TEST_INDEX.getIndexName());
        Settings.Builder settingBuilder = Settings.builder()
                .put("index.number_of_shards", 5)
                .put("index.number_of_replicas", 1);
        XContentBuilder builder = null;
        try {
            builder = XContentFactory.jsonBuilder();
            builder.startObject();
            {
                builder.startObject("properties");
                {
                    builder.startObject("id");
                    {
                        builder.field("type", "long");
                    }
                    builder.endObject();

                    builder.startObject("name");
                    {
                        builder.field("type", "text");
                    }
                    builder.endObject();

                    builder.startObject("age");
                    {
                        builder.field("type", "integer");
                    }
                    builder.endObject();

                    builder.startObject("birthday");
                    {
                        builder.field("type", "date");
                    }
                    builder.endObject();

                    builder.startObject("sex");
                    {
                        builder.field("type", "integer");
                    }
                    builder.endObject();
                    builder.startObject("description");
                    {
                        builder.field("type", "text");
                    }
                    builder.endObject();
                }
                builder.endObject();
            }
            builder.endObject();
            request.settings(settingBuilder);
            request.mapping(builder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        request.settings();
        CreateIndexResponse indexResponse = null;
        try {
            indexResponse = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return indexResponse;
    }

    @Override
    public IndexResponse insertIndex() throws IOException {
        Map<String, Object> sourceMap = new HashMap<>(5);
        sourceMap.put("id", 4);
        sourceMap.put("age", 55);
        sourceMap.put("name", "hua.guo");
        sourceMap.put("address", "南郭村4组");
        sourceMap.put("birthday", "1966-02-25");
        IndexRequest request = new IndexRequest(IndexConstants.TEST_INDEX.getIndexName()).id("4");
        request.source(sourceMap);
        IndexResponse response = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        return response;
    }

}
