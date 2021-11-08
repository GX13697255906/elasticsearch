package com.xun.elastic.service.impl;

import com.xun.elastic.service.IndexService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author Administrator
 */
@Slf4j
@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Override
    public boolean checkIndexExists(String indexName) throws IOException {
        GetIndexRequest getIndexRequest = new GetIndexRequest(indexName);
        boolean result = restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        if (result) {
            log.info("索引： {} 存在", indexName);
        } else {
            log.info("索引： {} 不存在", indexName);
        }
        return result;
    }

    @Override
    public AcknowledgedResponse deleteIndex(String indexName) throws IOException {
        AcknowledgedResponse response = null;
        if (checkIndexExists(indexName)) {
            log.info("删除索引： {} ", indexName);
            DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(indexName);
            response = restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
        } else {
            log.info("索引： {} 不存在", indexName);
        }
        return response;
    }
}
