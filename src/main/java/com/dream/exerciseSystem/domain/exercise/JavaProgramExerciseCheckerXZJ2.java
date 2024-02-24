package com.dream.exerciseSystem.domain.exercise;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class JavaProgramExerciseCheckerXZJ2 {
    private final Random random = new Random();

    public static double fahrenheit2celsius(double fahrenheit) {
        return (( 5 *(fahrenheit - 32.0)) / 9.0);
    }

    public HashMap<String, Object> checkFahrenheit2celsius(Class<?> solutionClass) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        String targetMethodName = "fahrenheit2celsius";
        HashMap<String, Object> result = new HashMap<>();
        ArrayList<HashMap<String, String>> hints = new ArrayList<>();

        Method targetMethod = null;
        Class<?>[] trueParameterTypes = {double.class};
        Class<?> trueReturnType = double.class;
        HashMap<String, Object> checkInputAndOutputResult = checkInputAndOutput(solutionClass, targetMethodName, trueParameterTypes, trueReturnType);
        if (!(boolean)checkInputAndOutputResult.get("correct")) {
            return checkInputAndOutputResult;
        }
        targetMethod = solutionClass.getDeclaredMethod(targetMethodName, trueParameterTypes);

        double testInput1 = random.nextDouble() + random.nextInt(10) + 10;
        double testInput2 = random.nextDouble() + random.nextInt(100) + 100;
        double testInput3 = random.nextDouble() + random.nextInt(1000) + 1000;

        targetMethod.setAccessible(true);
        double userMethodOutput1 = (double) targetMethod.invoke(null, testInput1);
        double userMethodOutput2 = (double) targetMethod.invoke(null, testInput2);
        double userMethodOutput3 = (double) targetMethod.invoke(null, testInput3);

        double trueOutput1 = fahrenheit2celsius(testInput1);
        double trueOutput2 = fahrenheit2celsius(testInput2);
        double trueOutput3 = fahrenheit2celsius(testInput3);

        boolean check1 = userMethodOutput1 == trueOutput1;
        boolean check2 = userMethodOutput2 == trueOutput2;
        boolean check3 = userMethodOutput3 == trueOutput3;
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

    public static double inch2meter(double inch) {
        return inch * 0.0254;
    }

    public HashMap<String, Object> checkInch2meter(Class<?> solutionClass) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        String targetMethodName = "inch2meter";
        HashMap<String, Object> result = new HashMap<>();
        ArrayList<HashMap<String, String>> hints = new ArrayList<>();

        Method targetMethod = null;
        Class<?>[] trueParameterTypes = {double.class};
        Class<?> trueReturnType = double.class;
        HashMap<String, Object> checkInputAndOutputResult = checkInputAndOutput(solutionClass, targetMethodName, trueParameterTypes, trueReturnType);
        if (!(boolean)checkInputAndOutputResult.get("correct")) {
            return checkInputAndOutputResult;
        }
        targetMethod = solutionClass.getDeclaredMethod(targetMethodName, trueParameterTypes);

        double testInput1 = random.nextDouble() + random.nextInt(10) + 10;
        double testInput2 = random.nextDouble() + random.nextInt(100) + 100;
        double testInput3 = random.nextDouble() + random.nextInt(1000) + 1000;

        targetMethod.setAccessible(true);
        double userMethodOutput1 = (double) targetMethod.invoke(null, testInput1);
        double userMethodOutput2 = (double) targetMethod.invoke(null, testInput2);
        double userMethodOutput3 = (double) targetMethod.invoke(null, testInput3);

        double trueOutput1 = inch2meter(testInput1);
        double trueOutput2 = inch2meter(testInput2);
        double trueOutput3 = inch2meter(testInput3);

        boolean check1 = userMethodOutput1 == trueOutput1;
        boolean check2 = userMethodOutput2 == trueOutput2;
        boolean check3 = userMethodOutput3 == trueOutput3;
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

    public static int dataTypesTest3(int num) {
        int firstDigit = num % 10;
        int remainingNumber = num / 10;
        int SecondDigit = remainingNumber % 10;
        remainingNumber = remainingNumber / 10;
        int thirdDigit = remainingNumber % 10;
        remainingNumber = remainingNumber / 10;
        int fourthDigit = remainingNumber % 10;

        return thirdDigit + SecondDigit + firstDigit + fourthDigit;
    }

    public HashMap<String, Object> checkDataTypesTest3(Class<?> solutionClass) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        String targetMethodName = "dataTypesTest3";
        HashMap<String, Object> result = new HashMap<>();
        ArrayList<HashMap<String, String>> hints = new ArrayList<>();

        Method targetMethod = null;
        Class<?>[] trueParameterTypes = {int.class};
        Class<?> trueReturnType = int.class;
        HashMap<String, Object> checkInputAndOutputResult = checkInputAndOutput(solutionClass, targetMethodName, trueParameterTypes, trueReturnType);
        if (!(boolean)checkInputAndOutputResult.get("correct")) {
            return checkInputAndOutputResult;
        }
        targetMethod = solutionClass.getDeclaredMethod(targetMethodName, trueParameterTypes);

        int testInput1 = random.nextInt(1000);
        int testInput2 = random.nextInt(1000);
        int testInput3 = random.nextInt(1000);

        targetMethod.setAccessible(true);
        int userMethodOutput1 = (int) targetMethod.invoke(null, testInput1);
        int userMethodOutput2 = (int) targetMethod.invoke(null, testInput2);
        int userMethodOutput3 = (int) targetMethod.invoke(null, testInput3);

        int trueOutput1 = dataTypesTest3(testInput1);
        int trueOutput2 = dataTypesTest3(testInput2);
        int trueOutput3 = dataTypesTest3(testInput3);

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

    public static int[] minute2yearAndDay(double min) {
        double minutesInYear = 60 * 24 * 365;
        int years = (int) (min / minutesInYear);
        int days = (int) (min / 60 / 24) % 365;
        return new int[]{years, days};
    }

    public HashMap<String, Object> checkMinute2yearAndDay(Class<?> solutionClass) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        String targetMethodName = "minute2yearAndDay";
        HashMap<String, Object> result = new HashMap<>();
        ArrayList<HashMap<String, String>> hints = new ArrayList<>();

        Method targetMethod = null;
        Class<?>[] trueParameterTypes = {double.class};
        Class<?> trueReturnType = int[].class;
        HashMap<String, Object> checkInputAndOutputResult = checkInputAndOutput(solutionClass, targetMethodName, trueParameterTypes, trueReturnType);
        if (!(boolean)checkInputAndOutputResult.get("correct")) {
            return checkInputAndOutputResult;
        }
        targetMethod = solutionClass.getDeclaredMethod(targetMethodName, trueParameterTypes);

        double testInput1 = random.nextDouble() + random.nextInt(10000) + 10000;
        double testInput2 = random.nextDouble() + random.nextInt(100000) + 100000;
        double testInput3 = random.nextDouble() + random.nextInt(1000000) + 1000000;

        targetMethod.setAccessible(true);
        int[] userMethodOutput1 = (int[]) targetMethod.invoke(null, testInput1);
        int[] userMethodOutput2 = (int[]) targetMethod.invoke(null, testInput2);
        int[] userMethodOutput3 = (int[]) targetMethod.invoke(null, testInput3);

        int[] trueOutput1 = minute2yearAndDay(testInput1);
        int[] trueOutput2 = minute2yearAndDay(testInput2);
        int[] trueOutput3 = minute2yearAndDay(testInput3);

        boolean check1 = Arrays.equals(userMethodOutput1, trueOutput1);
        boolean check2 = Arrays.equals(userMethodOutput2, trueOutput2);
        boolean check3 = Arrays.equals(userMethodOutput3, trueOutput3);
        if (!check1) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "(" + testInput1 + ")的返回值应该是" + Arrays.toString(trueOutput1) + "，而不应该是" + Arrays.toString(userMethodOutput1));
            hints.add(hint);
        }
        if (!check2) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "(" + testInput2 + ")的返回值应该是" + Arrays.toString(trueOutput2) + "，而不应该是" + Arrays.toString(userMethodOutput2));
            hints.add(hint);
        }
        if (!check3) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "(" + testInput3 + ")的返回值应该是" + Arrays.toString(trueOutput3) + "，而不应该是" + Arrays.toString(userMethodOutput3));
            hints.add(hint);
        }
        result.put("correct", check1 && check2 && check3);
        result.put("hints", hints);
        return result;
    }

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
                "    public static int[] minute2yearAndDay(double min) {\n" +
                "        double minutesInYear = 60 * 24 * 365;\n" +
                "        int years = (int) (min / minutesInYear) + 1;\n" +
                "        int days = (int) (min / 60 / 24) % 365;\n" +
                "        return new int[]{years, days};\n" +
                "    }\n"
                + submissionCodeSuffix;
        String targetMethodName = exercise.targetMethodName;

        HashMap<String, Object> checkResult = JavaProgramExercise.check(submissionCode, targetMethodName, "XZJ2");
        System.out.println(checkResult);
    }

    public static void main(String[] args) throws Exception {
        String fPath = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "exercises", "program", "java", "testProgramExerciseXZJ2.json").toString();
        File file = new File(fPath);
        String jsonString = Files.readString(Path.of(file.getAbsolutePath()));
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        JSONArray exerciseArray = jsonObject.getJSONArray("exercises");
        List<JavaProgramExercise> javaProgramExercises = new ArrayList<>();
        for (int i = 0; i < exerciseArray.size(); i++) {
            JSONObject exerciseObject = exerciseArray.getJSONObject(i);
            javaProgramExercises.add(JavaProgramExercise.generateExerciseFromJsonObject(exerciseObject, "JAVA"));
        }
        JavaProgramExercise exercise = javaProgramExercises.get(3);
        javaProgramExerciseCheck(exercise);
    }
}
