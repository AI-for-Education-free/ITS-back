package com.dream.exerciseSystem.service;


import com.dream.exerciseSystem.service.impl.JavaProgramExerciseService;
import com.dream.exerciseSystem.utils.DataWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
//@ActiveProfiles("dev")
public class JavaProgramExerciseServiceTest {
    @Autowired
    JavaProgramExerciseService javaProgramExerciseService;

    @Test
    void TestGetExerciseById() {
        DataWrapper exercise = javaProgramExerciseService.getExerciseById("1984017648");
        System.out.println(exercise);
    }

    @Test
    void TestGetExerciseAll() {
        DataWrapper exerciseList = javaProgramExerciseService.getExerciseAll();
        System.out.println(exerciseList);
    }

    @Test
    void TestGetExerciseAllBasicInfo() {
        DataWrapper exerciseBasicInfoList = javaProgramExerciseService.getExerciseAllBasicInfo();
        System.out.println(exerciseBasicInfoList);
    }

    @Test
    void TestCheckExercise() throws Exception {
        String id = "783120422";
        String submissionCodePrefix = "package org.dream.solution;\n\npublic class Solution {";
        String submissionCodeSuffix = "}";
        String submissionCode = submissionCodePrefix +
                "\n" +
                "    public static String middle(String str) {\n" +
                "        int position;\n" +
                "        int length;\n" +
                "        if (str.length() % 2 == 0)\n" +
                "        {\n" +
                "            position = str.length() / 2 - 1;\n" +
                "            length = 2;\n" +
                "        }\n" +
                "        else\n" +
                "        {\n" +
                "            position = str.length() / 2;\n" +
                "            length = 1;\n" +
                "        }\n" +
                "        return str.substring(position, position + length);\n" +
                "    }\n"
                + submissionCodeSuffix;
        DataWrapper checkResult = javaProgramExerciseService.checkExercise(id, submissionCode);
        System.out.println(checkResult);
    }
}
