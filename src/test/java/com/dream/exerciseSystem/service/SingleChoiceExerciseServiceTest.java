package com.dream.exerciseSystem.service;

<<<<<<< HEAD:src/test/java/com/dream/exerciseSystem/service/SingleExerciseServiceTest.java
import com.dream.exerciseSystem.service.SingleChoiceExerciseService;
=======
import com.dream.exerciseSystem.service.impl.SingleChoiceExerciseServiceImpl;
>>>>>>> a3a687131548adfa1a41f0fa75894d3720ca09ac:src/test/java/com/dream/exerciseSystem/service/SingleChoiceExerciseServiceTest.java
import com.dream.exerciseSystem.utils.DataWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
//@ActiveProfiles("dev")
public class SingleChoiceExerciseServiceTest {
    @Autowired
<<<<<<< HEAD:src/test/java/com/dream/exerciseSystem/service/SingleExerciseServiceTest.java
    SingleChoiceExerciseService singleExerciseService;
=======
    SingleChoiceExerciseServiceImpl singleExerciseService;
>>>>>>> a3a687131548adfa1a41f0fa75894d3720ca09ac:src/test/java/com/dream/exerciseSystem/service/SingleChoiceExerciseServiceTest.java

    @Test
    void TestGetExerciseById() {
        DataWrapper targetExercise = singleExerciseService.getExerciseById("1370052298");
        System.out.println(targetExercise);
    }

    @Test
    void TestGetExerciseAllBasicInfo() {
        DataWrapper exerciseBasicInfoList = singleExerciseService.getExerciseAllBasicInfo();
        System.out.println(exerciseBasicInfoList);
    }

    @Test
    void TestGetExerciseAll() {
        DataWrapper targetExercises = singleExerciseService.getExerciseAll();
        System.out.println(targetExercises);
    }
}
