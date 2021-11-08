package com.xun.elastic.controller;

import com.xun.elastic.service.IndexService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author Administrator
 */
@Slf4j
@RestController
@RequestMapping(value = "/index")
public class IndexController {

    @Autowired
    private IndexService indexService;

    @GetMapping(value = "/checkIndexExists")
    public Boolean checkIndexExists(@RequestParam(value = "indexName") String indexName) throws IOException {
        return indexService.checkIndexExists(indexName);
    }

    @GetMapping(value = "/deleteIndex")
    public AcknowledgedResponse deleteIndex(@RequestParam(value = "indexName") String indexName) throws IOException {
        return indexService.deleteIndex(indexName);
    }
}
