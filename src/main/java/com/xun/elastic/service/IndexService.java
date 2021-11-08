package com.xun.elastic.service;

import org.elasticsearch.action.support.master.AcknowledgedResponse;

import java.io.IOException;

/**
 * @author xun.guo
 */
public interface IndexService {

    /**
     * 检测是否存在该索引
     *
     * @param indexName
     * @return
     * @throws IOException
     */
    boolean checkIndexExists(String indexName) throws IOException;

    /**
     * 删除索引
     *
     * @param indexName
     * @return
     * @throws IOException
     */
    AcknowledgedResponse deleteIndex(String indexName) throws IOException;


}
