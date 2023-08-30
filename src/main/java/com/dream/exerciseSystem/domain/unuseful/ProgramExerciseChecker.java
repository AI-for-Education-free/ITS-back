package com.dream.exerciseSystem.domain.exercise;

import com.github.javaparser.ast.body.MethodDeclaration;
import org.mdkt.compiler.InMemoryJavaCompiler;
import com.github.javaparser.ast.body.MethodDeclaration;

import java.io.ByteArrayOutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ProgramExerciseChecker {
    public static HashMap<String, Object> checkConsole(String submissionCode, String checkCode, String targetMethodName) throws Exception {
        HashMap<String, Object> checkResult = new HashMap<>();
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        PrintStream consoleStream = System.out;
        System.setOut(new PrintStream(myOut));
        Class<?> solutionClass;

        try {
            solutionClass = InMemoryJavaCompiler.newInstance().compile("org.dream.solution.Solution", submissionCode);
        } catch (RuntimeException e) {
            checkResult.put("compileException", e);
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
        } catch (RuntimeException e) {
            checkResult.put("runtimeException", e);
            return checkResult;
        }

        String userOutput = myOut.toString();
        userOutput = userOutput.substring(0, userOutput.length()-1);
        checkResult = (HashMap<String, Object>) checkMethod.invoke(null, userOutput);
        checkResult.put("userOutput", userOutput);
        System.setOut(consoleStream);
        System.out.println(userOutput);
        return checkResult;
    }

    public HashMap<String, Object> check(String userOutput) {
        HashMap<String, Object> result = new HashMap<>();
        String[] tmp = userOutput.split("\n");
        boolean check1 = tmp.length != 2;
        boolean check2 = !tmp[0].equals("hello");
        ArrayList<HashMap<String, String>> hints = new ArrayList<>();
        if (check1) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", "输出必须是两行");
            hints.add(hint);
        }
        if (check2) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", "第一行必须是hello");
            hints.add(hint);
        }
        result.put("correct", check1 && check2);
        result.put("hints", hints);
        return result;
    }

    public static void main(String[] args) throws Exception {
        String s = "package org.dream.solution;\n" +
                "\n" +
                "public class Solution {\n" +
                "    public static void printHello() {\n" +
                "        System.out.println(\"helo\\ndream\");\n" +
                "    }\n" +
                "}";
        String c = "package org.dream.check;\n" +
                "import java.util.HashMap;\n" +
                "import java.util.Objects;\n" +
                "import java.util.ArrayList;\n" +
                "public class Checker {\n" +
                "   public static HashMap<String, Object> check(String userOutput) {\n" +
                "        HashMap<String, Object> result = new HashMap<>();\n" +
                "        String[] tmp = userOutput.split(\"\\n\");\n" +
                "        boolean check1 = tmp.length != 2;\n" +
                "        boolean check2 = !tmp[0].equals(\"hello\");\n" +
                "        ArrayList<HashMap<String, String>> hints = new ArrayList<>();\n" +
                "        if (check1) {\n" +
                "            HashMap<String, String> hint = new HashMap<>();\n" +
                "            hint.put(\"chinese\", \"输出必须是两行\");\n" +
                "            hints.add(hint);\n" +
                "        }\n" +
                "        if (check2) {\n" +
                "            HashMap<String, String> hint = new HashMap<>();\n" +
                "            hint.put(\"chinese\", \"第一行必须是hello\");\n" +
                "            hints.add(hint);\n" +
                "        }\n" +
                "        result.put(\"correct\", check1 && check2);\n" +
                "        result.put(\"hints\", hints);\n" +
                "        return result;" +
                "    }\n" +
                "}";
        System.out.println(checkConsole(s, c, "printHello"));

        System.out.println(c);

        String sourceCode = "package org.any;\n" +
                "public class HelloClass {\n" +
                "   public static String name = \"abc\";\n" +
                "   public int age;\n" +
                "   public float x;\n" +
                "   public HelloClass(int age, float x) { this.age = age; this.x = x;}" +
                "   public static String hello() { return \"hello, \" + name; }" +
                "   public static void sayHello() { System.out.println(\"hello\");; }" +
                "}";

        Class<?> targetClass = InMemoryJavaCompiler.newInstance().compile("org.any.HelloClass", sourceCode);
        Method[] methods = targetClass.getDeclaredMethods();
//        List<Method> targetMethods = Stream.of(methods).filter(method -> method.getName().equals("hello")).collect(Collectors.toList());
//        Method targetMethod = targetMethods.get(0);
//        targetMethod.setAccessible(true);
//        System.out.println(targetMethod.invoke(null));
        List<Method> targetMethods = Stream.of(methods).filter(method -> method.getName().equals("sayHello")).collect(Collectors.toList());
        Method targetMethod = targetMethods.get(0);
        targetMethod.setAccessible(true);
        targetMethod.invoke(null);
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        PrintStream consoleStream = System.out;
        System.setOut(new PrintStream(myOut));
        System.out.println(myOut.toString());
        System.setOut(consoleStream);
        System.out.println("hi");
        //        try (PipedOutputStream pipedOS = new PipedOutputStream();) {
//            pipedOS.connect();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }


//        Constructor<?> ctor = targetClass.getDeclaredConstructor(int.class, float.class);
//        Object targetObject = ctor.newInstance((int) 10, (float) 1.2);
//        Field ageField = targetClass.getDeclaredField("age");
//        ageField.setAccessible(true);
//        System.out.println(ageField.get(targetObject));
//        Field xField = targetClass.getDeclaredField("x");
//        xField.setAccessible(true);
//        System.out.println(xField.get(targetObject));
    }
}
