package com.dream.exerciseSystem.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.annotation.Resource;

@SpringBootTest
public class FillInExerciseTest {

    @Resource
    private MongoTemplate mongoTemplate;
    @Test
    void fillInExerciseTest(){

    }
}
