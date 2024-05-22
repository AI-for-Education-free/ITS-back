package com.dream.exerciseSystem.service;

import com.dream.exerciseSystem.domain.exercise.SingleChoiceExercise;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class SingleChoiceTest {

    @Resource
    private MongoTemplate mongoTemplate;

    @Test
    void getDataFromDB(){
//        String exerciseId = "1370052298";
//        SingleChoiceExercise result = mongoTemplate.findById(exerciseId, SingleChoiceExercise.class);
//        System.out.println(result.getCorrectAnswer());
        List<SingleChoiceExercise> results = mongoTemplate.findAll(SingleChoiceExercise.class);
//        for(SingleChoiceExercise i : results){
//            System.out.println(i.getId());
//        }
        System.out.println(results.get(0).getId());
    }

}
