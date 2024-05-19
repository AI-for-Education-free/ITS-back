package com.dream.exerciseSystem.domain.exercise;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaProgramExerciseCheckerXCL {
    private final Random random = new Random();

    public HashMap<String, Object> checkInputAndOutput(Class<?> solutionClass, String targetMethodName, Class<?>[] trueParameterTypes, Class<?> trueReturnType) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("correct", false);
        ArrayList<HashMap<String, String>> hints = new ArrayList<>();

        Method targetMethod = null;
        // 检查方法是否存在以及输入参数
        try {
            targetMethod = solutionClass.getDeclaredMethod(targetMethodName, trueParameterTypes);
        } catch (NoSuchMethodException noSuchMethodException) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", "方法 " + targetMethodName + " 不存在；或者方法 " + targetMethodName +
                    " 的输入参数类型/数目不对，输入参数必须为：" + Arrays.toString(trueParameterTypes));
            hints.add(hint);
            result.put("hints", hints);
            return result;
        }

        // 检查返回值
        Class<?> returnType = targetMethod.getReturnType();
        if (returnType != trueReturnType) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", "方法 " + targetMethodName + " 的返回值类型不对，返回值类型必须为：" + trueReturnType);
            hints.add(hint);
            result.put("hints", hints);
            return result;
        }

        result.put("correct", true);
        return result;
    }

    public static void javaProgramExerciseCheck(JavaProgramExercise exercise) throws Exception {
        String submissionCodePrefix = "package org.dream.solution;\n\npublic class Solution {";
        String submissionCodeSuffix = "}";
        String submissionCode = submissionCodePrefix +
                "\n" +
                "    public static boolean isAllVowel(String input) {\n" +
                "        String str_vowels = \"aeiou\";\n" +
                "        String phrase = input.toLowerCase();\n" +
                "        for (int i = 0; i < phrase.length(); i++) {\n" +
                "            if (str_vowels.indexOf(phrase.charAt(i)) == -1)\n" +
                "                return false;\n" +
                "        }\n" +
                "        return true;\n" +
                "    }\n"
                + submissionCodeSuffix;
        String targetMethodName = exercise.targetMethodName;

        JavaProgramExerciseCheckResult checkResult = JavaProgramExercise.check(submissionCode, targetMethodName, "XCL");
        System.out.println(checkResult);
    }

    public static void main(String[] args) throws Exception {
        String fPath = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "exercises", "program", "java", "testProgramExerciseXCL.json").toString();
        File file = new File(fPath);
        String jsonString = Files.readString(Path.of(file.getAbsolutePath()));
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        JSONArray exerciseArray = jsonObject.getJSONArray("exercises");
        List<JavaProgramExercise> javaProgramExercises = new ArrayList<>();
        for (int i = 0; i < exerciseArray.size(); i++) {
            JSONObject exerciseObject = exerciseArray.getJSONObject(i);
            javaProgramExercises.add(JavaProgramExercise.generateExerciseFromJsonObject(exerciseObject, "JAVA"));
        }
        JavaProgramExercise exercise = javaProgramExercises.get(1);
        javaProgramExerciseCheck(exercise);
    }
}
