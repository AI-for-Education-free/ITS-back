package com.dream.exerciseSystem.service.unuseful;

import com.dream.exerciseSystem.domain.exercise.*;
import com.dream.exerciseSystem.utils.DataWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExerciseService implements IExerciseService {
    @Override
    public DataWrapper getAllExercisesByType(ExerciseType exerciseType) {
        return null;
    }

    @Override
    public DataWrapper getExerciseById(String id) {
        List<Exercise> allExercises = new ArrayList<>();
        String programmingExercisesPath = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "exercises", "program", "java", "programExercises.json").toString();
        File programmingExercisesFile = new File(programmingExercisesPath);
        String singleChoiceExercisesPath = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "exercises", "program", "java", "singleChoiceExercises.json").toString();
        File singleChoiceExercisesFile = new File(singleChoiceExercisesPath);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,Exercise> data = new HashMap<>();
        try {
            Map programmingExerciseJson = objectMapper.readValue(programmingExercisesFile, Map.class);
            List basicExercises = (List) ((Map) programmingExerciseJson.get("w3resource")).get("basic");
            Map singleChoiceExerciseJson = objectMapper.readValue(singleChoiceExercisesFile, Map.class);
            List singleChoiceExercises1 = (List) ((Map) singleChoiceExerciseJson.get("sanfoundry")).get("Integer and Floating Data Types");

            for (int i = 0; i < basicExercises.size(); i++) {
                Map exercise = (Map) basicExercises.get(i);
                JavaProgramExercise e = JavaProgramExercise.parseFromJson(exercise);
                allExercises.add(e);
            }
            for (int i = 0; i < singleChoiceExercises1.size(); i++) {
                Map exercise = (Map) singleChoiceExercises1.get(i);
                SingleChoiceExercise e = SingleChoiceExercise.parseFromJson(exercise);
                allExercises.add(e);
            }

            for (Exercise exercise: allExercises) {
                if (exercise.id.equals(id)) {
                    data.put("exercise", exercise);
                    break;
                }
            }
            if (data.get("exercise") != null) {
                // 这里注意，如果要序列化数据，对象的属性要为public，或者其它处理方式
                return new DataWrapper(true).msgBuilder("请求习题成功").dataBuilder(data);
            } else {
                return new DataWrapper(false).msgBuilder("请求习题失败，习题不存在").codeBuilder(100);
            }
        } catch (IOException e) {
            return new DataWrapper(false).msgBuilder("请求习题失败，IO错误").dataBuilder(data);
        }
    }

    @Override
    public DataWrapper getExerciseResult(String exerciseId, String submissionCode) throws Exception {
        String checkCode = "package org.dream.check;\n" +
                "import java.util.HashMap;\n" +
                "import java.util.Objects;\n" +
                "import java.util.ArrayList;\n" +
                "public class Checker {\n" +
                "   public static HashMap<String, Object> check(String userOutput) {\n" +
                "        HashMap<String, Object> result = new HashMap<>();\n" +
                "        String[] tmp = userOutput.split(\"\\n\");\n" +
                "        boolean check1 = tmp.length == 2;\n" +
                "        boolean check2 = tmp[0].equals(\"hello\");\n" +
                "        ArrayList<HashMap<String, String>> hints = new ArrayList<>();\n" +
                "        if (!check1) {\n" +
                "            HashMap<String, String> hint = new HashMap<>();\n" +
                "            hint.put(\"chinese\", \"输出必须是两行\");\n" +
                "            hints.add(hint);\n" +
                "        }\n" +
                "        if (!check2) {\n" +
                "            HashMap<String, String> hint = new HashMap<>();\n" +
                "            hint.put(\"chinese\", \"第一行必须是hello\");\n" +
                "            hints.add(hint);\n" +
                "        }\n" +
                "        result.put(\"correct\", check1 && check2);\n" +
                "        result.put(\"hints\", hints);\n" +
                "        return result;" +
                "    }\n" +
                "}";
        HashMap<String, Object> result = JavaProgramChecker.check(submissionCode, checkCode, "console", "printHello");
        Map<String, Map> data = new HashMap<>();
        data.put("result", result);
        return new DataWrapper(true).msgBuilder("习题判断成功").dataBuilder(data);
    }
}
