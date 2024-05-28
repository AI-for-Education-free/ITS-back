package com.dream.exerciseSystem;

import com.dream.exerciseSystem.domain.exercise.Exercise;
import com.dream.exerciseSystem.utils.MongoUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class RecommendTest {

    @Resource
    private MongoUtils mongoUtils;

//    @Test
//    void getFiveRandomExerciseContentsAndId(){
//        List<Exercise> exerciseContentList = mongoUtils.getRandomExerciseContent();
//        List<String> exerciseIdList = mongoUtils.getIdFromExerciseContent(exerciseContentList);
//        System.out.println(exerciseContentList);
//        System.out.println(exerciseIdList);
//    }

}
