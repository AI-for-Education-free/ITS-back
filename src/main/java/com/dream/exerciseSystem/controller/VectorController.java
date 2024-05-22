package com.dream.exerciseSystem.controller;

import com.dream.exerciseSystem.utils.DataWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class VectorController {

//    @PostMapping
//    public DataWrapper
    @PostMapping("/vector/search")
    public void elasticSearchVector(){

    }
}
