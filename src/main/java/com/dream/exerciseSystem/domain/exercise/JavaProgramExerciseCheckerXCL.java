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

    public static boolean methodTest22(int n) {
        final int f = 10;
        if (n == 0){
            return false;
        }
        while(n != 0){
            if((n % f) % 2 != 0){
                return false;
            }
            n /= 10;
        }
        return true;
    }

    public static boolean isAllVowel(String input) {
        String str_vowels = "aeiou";
        String phrase = input.toLowerCase();
        for (int i = 0; i < phrase.length(); i++) {
            if (str_vowels.indexOf(phrase.charAt(i)) == -1)
                return false;
        }
        return true;
    }

    public HashMap<String, Object> checkMethodTest22(Class<?> solutionClass) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        // 改成对应方法名
        String targetMethodName = "methodTest22";
        HashMap<String, Object> result = new HashMap<>();
        ArrayList<HashMap<String, String>> hints = new ArrayList<>();

        // 输入参数个数和类型
        Method targetMethod = solutionClass.getDeclaredMethod(targetMethodName, int.class);
        // 返回值类型
        Class<?> trueReturnType = boolean.class;
        Class<?> returnType = targetMethod.getReturnType();
        if (returnType != trueReturnType) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", "方法 " + targetMethodName + " 的返回值类型不对，返回值类型必须为：" + trueReturnType);
            hints.add(hint);
            result.put("correct", false);
            result.put("hints", hints);
            return result;
        }

        // 测试的输入值
        int testInput1 = random.nextInt(10000) + 10000;
        int testInput2 = random.nextInt(100000) + 100000;
        int testInput3 = random.nextInt(1000000) + 1000000;

        targetMethod.setAccessible(true);
        // 用户代码的输出值
        boolean userMethodOutput1 = (boolean) targetMethod.invoke(null, testInput1);
        boolean userMethodOutput2 = (boolean) targetMethod.invoke(null, testInput2);
        boolean userMethodOutput3 = (boolean) targetMethod.invoke(null, testInput3);

        // 正确的输出值
        boolean trueOutput1 = methodTest22(testInput1);
        boolean trueOutput2 = methodTest22(testInput2);
        boolean trueOutput3 = methodTest22(testInput3);

        // 检查用户输出和正确输出是否一致
        boolean check1 = Objects.equals(userMethodOutput1, trueOutput1);
        boolean check2 = Objects.equals(userMethodOutput2, trueOutput2);
        boolean check3 = Objects.equals(userMethodOutput3, trueOutput3);

        if (!check1) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "(" + testInput1 + ")的返回值应该是" + trueOutput1 + "，而不应该是" + userMethodOutput1);
            hints.add(hint);
        }
        if (!check2) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "(" + testInput2 + ")的返回值应该是" + trueOutput2 + "，而不应该是" + userMethodOutput2);
            hints.add(hint);
        }
        if (!check3) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "(" + testInput3 + ")的返回值应该是" + trueOutput3 + "，而不应该是" + userMethodOutput3);
            hints.add(hint);
        }
        result.put("correct", check1 && check2 && check3);
        result.put("hints", hints);
        return result;
    }

    public HashMap<String, Object> checkIsAllVowel(Class<?> solutionClass) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        String targetMethodName = "isAllVowel";
        HashMap<String, Object> result = new HashMap<>();
        ArrayList<HashMap<String, String>> hints = new ArrayList<>();

        Method targetMethod = solutionClass.getDeclaredMethod(targetMethodName, String.class);
        Class<?> trueReturnType = boolean.class;
        Class<?> returnType = targetMethod.getReturnType();
        if (returnType != trueReturnType) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", "方法 " + targetMethodName + " 的返回值类型不对，返回值类型必须为：" + trueReturnType);
            hints.add(hint);
            result.put("correct", false);
            result.put("hints", hints);
            return result;
        }

        String testInput1 = "AIEEE";
        String testInput2 = "IAO";
        String testInput3 = "Python";

        targetMethod.setAccessible(true);
        boolean userMethodOutput1 = (boolean) targetMethod.invoke(null, testInput1);
        boolean userMethodOutput2 = (boolean) targetMethod.invoke(null, testInput2);
        boolean userMethodOutput3 = (boolean) targetMethod.invoke(null, testInput3);

        boolean trueOutput1 = isAllVowel(testInput1);
        boolean trueOutput2 = isAllVowel(testInput2);
        boolean trueOutput3 = isAllVowel(testInput3);

        boolean check1 = Objects.equals(userMethodOutput1, trueOutput1);
        boolean check2 = Objects.equals(userMethodOutput2, trueOutput2);
        boolean check3 = Objects.equals(userMethodOutput3, trueOutput3);

        if (!check1) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "(" + testInput1 + ")的返回值应该是" + trueOutput1 + "，而不应该是" + userMethodOutput1);
            hints.add(hint);
        }
        if (!check2) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "(" + testInput2 + ")的返回值应该是" + trueOutput2 + "，而不应该是" + userMethodOutput2);
            hints.add(hint);
        }
        if (!check3) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "(" + testInput3 + ")的返回值应该是" + trueOutput3 + "，而不应该是" + userMethodOutput3);
            hints.add(hint);
        }
        result.put("correct", check1 && check2 && check3);
        result.put("hints", hints);
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
                "                return true;\n" +
                "        }\n" +
                "        return true;\n" +
                "    }\n"
                + submissionCodeSuffix;
        String targetMethodName = exercise.targetMethodName;

        HashMap<String, Object> checkResult = JavaProgramExercise.check(submissionCode, targetMethodName, "XCL");
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
        JavaProgramExercise exercise = javaProgramExercises.get(2);
        javaProgramExerciseCheck(exercise);
    }
}
