package com.dream.exerciseSystem.domain.exercise;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mdkt.compiler.InMemoryJavaCompiler;

import javax.management.RuntimeErrorException;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaProgramChecker {
    public static void main(String[] args) throws IOException {
        String fPath = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "exercises", "program", "java", "programExercises.json").toString();
        System.out.println(fPath);
        File file = new File(fPath);
        ObjectMapper objectMapper = new ObjectMapper();
        Map data = objectMapper.readValue(file, Map.class);
        List basicData = (List) ((Map) data.get("w3resource")).get("basic");
        Map exercise1 = (Map) basicData.get(0);
        JavaProgramExercise e = JavaProgramExercise.parseFromJson(exercise1);
    }

    public static HashMap<String, Object> checkConsole(String submissionCode, String checkCode, String targetMethodName) throws Exception {
        HashMap<String, Object> checkResult = new HashMap<>();
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        PrintStream consoleStream = System.out;
        System.setOut(new PrintStream(myOut));
        Class<?> solutionClass;

        try {
            solutionClass = InMemoryJavaCompiler.newInstance().compile("org.dream.solution.Solution", submissionCode);
        } catch (Exception e) {
            checkResult.put("correct", false);
            checkResult.put("hint", "compileException");
            checkResult.put("compileException", e);
            System.setOut(consoleStream);
            return checkResult;
        }

        Class<?> checkerClass = InMemoryJavaCompiler.newInstance().compile("org.dream.check.Checker", checkCode);
        Method[] solutionMethods = solutionClass.getDeclaredMethods();
        Method[] checkerMethods = checkerClass.getDeclaredMethods();
        List<Method> filteredSolutionMethods = Stream.of(solutionMethods).filter(method -> method.getName().equals(targetMethodName)).collect(Collectors.toList());
        List<Method> filteredCheckMethods = Stream.of(checkerMethods).filter(method -> method.getName().equals("check")).collect(Collectors.toList());
        Method solutionMethod = filteredSolutionMethods.get(0);
        Method checkMethod = filteredCheckMethods.get(0);
        solutionMethod.setAccessible(true);
        checkMethod.setAccessible(true);

        try {
            solutionMethod.invoke(null);
        } catch (Exception e) {
            InvocationTargetException targetEx = (InvocationTargetException) e;
//            Throwable trowEx = targetEx .getTargetException();
//            throw new Excetption ("异常："+trowEx .getMessage());
            checkResult.put("correct", false);
            checkResult.put("hint", "runtimeException");
            checkResult.put("runtimeException", targetEx);
            System.setOut(consoleStream);
            return checkResult;
        }

        String userOutput = myOut.toString();
        if (userOutput.length() > 0) {
            userOutput = userOutput.substring(0, userOutput.length()-1);
        }
        checkResult = (HashMap<String, Object>) checkMethod.invoke(null, userOutput);
        checkResult.put("userOutput", userOutput);
        System.setOut(consoleStream);
        return checkResult;
    }

    public static HashMap<String, Object> check(String submissionCode, String checkCode, String checkPlace, String targetMethodName) throws Exception {
        HashMap<String, Object> result;
        if (checkPlace.equals("console")) {
            result = checkConsole(submissionCode, checkCode, targetMethodName);
        } else {
            result = new HashMap<>();
        }
        return result;
    }
}
