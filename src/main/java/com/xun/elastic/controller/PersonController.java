package com.xun.elastic.controller;

import com.xun.elastic.service.PersonService;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author xun.guo
 */
@RestController
@RequestMapping(value = "/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping(value = "/search")
    public GetResponse search() throws IOException {
        return personService.search();
    }

    @GetMapping(value = "/createIndex")
    public CreateIndexResponse createIndex() {
        return personService.createIndex();
    }

    @GetMapping(value = "/insertIndex")
    public IndexResponse insertIndex() throws IOException {
        return personService.insertIndex();
    }

    @GetMapping(value = "/analysisTemplate")
    public void analysisTemplate() {
        personService.analysisTemplate();
    }

}
