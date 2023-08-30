package com.dream.exerciseSystem.domain.exercise;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class SingleChoiceChecker {
    public static void main(String[] args) throws IOException {
        String fPath = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "exercises", "program", "java", "singleChoiceExercises.json").toString();
        System.out.println(fPath);
        File file = new File(fPath);
        ObjectMapper objectMapper = new ObjectMapper();
        Map data = objectMapper.readValue(file, Map.class);
        List basicData = (List) ((Map) data.get("sanfoundry")).get("Integer and Floating Data Types");
        Map exercise1 = (Map) basicData.get(0);
        SingleChoiceExercise e = SingleChoiceExercise.parseFromJson(exercise1);
        System.out.println("");
    }
}
