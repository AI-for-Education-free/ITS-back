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


public class JavaProgramExerciseCheckerTemplate {
    public static final int PASSWORD_LENGTH = 10;
    private final Random random = new Random();
    private final Util exerciseUtil = new Util();

    public static void printHello() {
        System.out.println("hello\ndream");
    }

    public HashMap<String, Object> checkPrintHello(Class<?> solutionClass) throws NoSuchMethodException {
        String targetMethodName = "printHello";
        HashMap<String, Object> result = new HashMap<>();
        ArrayList<HashMap<String, String>> hints = new ArrayList<>();

        Method targetMethod = null;
        Class<?>[] trueParameterTypes = {};
        Class<?> trueReturnType = void.class;
        HashMap<String, Object> checkInputAndOutputResult = checkInputAndOutput(solutionClass, targetMethodName, trueParameterTypes, trueReturnType);
        if (!(boolean)checkInputAndOutputResult.get("correct")) {
            return checkInputAndOutputResult;
        }
        targetMethod = solutionClass.getDeclaredMethod(targetMethodName, trueParameterTypes);

        targetMethod.setAccessible(true);
        final ByteArrayOutputStream userOutputStream = new ByteArrayOutputStream();
        PrintStream consoleStream = System.out;
        System.setOut(new PrintStream(userOutputStream));
        try {
            targetMethod.invoke(null);
        } catch (Exception e) {
            assert e instanceof InvocationTargetException;
            // 这里如果用户写的方法发生invoke错误，这里(InvocationTargetException) e会发生投影错误
            InvocationTargetException targetEx = (InvocationTargetException) e;
//            Throwable trowEx = targetEx .getTargetException();
//            throw new Exception ("异常："+trowEx .getMessage());
            result.put("correct", false);
            result.put("hint", "runtimeException");
            result.put("runtimeException", targetEx);
            System.setOut(consoleStream);
            return result;
        }

        String userOutput = userOutputStream.toString();
        if (userOutput.length() > 0) {
            userOutput = userOutput.substring(0, userOutput.length()-1);
        }
        result.put("userOutput", userOutput);
        System.setOut(consoleStream);

        String[] tmp = userOutput.split("\n");
        boolean check1 = tmp.length == 2;
        boolean check2 = tmp[0].equals("hello");

        if (!check1) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", "printHello() 的输出必须是两行");
            hints.add(hint);
        }
        if (!check2) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", "printHello() 输出的第一行必须是hello");
            hints.add(hint);
        }
        result.put("correct", check1 && check2);
        result.put("hints", hints);
        return result;
    }

    public static double smallest(double x, double y, double z) {
        return Math.min(Math.min(x, y), z);
    }

    public HashMap<String, Object> checkSmallest(Class<?> solutionClass) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        String targetMethodName = "smallest";
        HashMap<String, Object> result = new HashMap<>();
        ArrayList<HashMap<String, String>> hints = new ArrayList<>();

        Method targetMethod = null;
        Class<?>[] trueParameterTypes = {double.class, double.class, double.class};
        Class<?> trueReturnType = double.class;
        HashMap<String, Object> checkInputAndOutputResult = checkInputAndOutput(solutionClass, targetMethodName, trueParameterTypes, trueReturnType);
        if (!(boolean)checkInputAndOutputResult.get("correct")) {
            return checkInputAndOutputResult;
        }
        targetMethod = solutionClass.getDeclaredMethod(targetMethodName, trueParameterTypes);

        targetMethod.setAccessible(true);
        double userMethodOutput1 = (double) targetMethod.invoke(null, 25, 37, 39);
        double userMethodOutput2 = (double) targetMethod.invoke(null, 2, -5, 10);
        double userMethodOutput3 = (double) targetMethod.invoke(null, 144, 295, 100);

        double trueOutput1 = smallest(25, 37, 39);
        double trueOutput2 = smallest(2, -5, 10);
        double trueOutput3 = smallest(144, 295, 100);

        boolean check1 = userMethodOutput1 == trueOutput1;
        boolean check2 = userMethodOutput2 == trueOutput2;
        boolean check3 = userMethodOutput3 == trueOutput3;
        if (!check1) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "(25, 37, 39)的返回值应该是" + trueOutput1 + "，而不应该是" + userMethodOutput1);
            hints.add(hint);
        }
        if (!check2) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "(2, -5, 10)的返回值应该是" + trueOutput2 + "，而不应该是" + userMethodOutput2);
            hints.add(hint);
        }
        if (!check3) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "(144, 295, 100)的返回值应该是" + trueOutput3 + "，而不应该是" + userMethodOutput3);
            hints.add(hint);
        }
        result.put("correct", check1 && check2 && check3);
        result.put("hints", hints);
        return result;
    }

    public static String middle(String str) {
        int position;
        int length;
        if (str.length() % 2 == 0)
        {
            position = str.length() / 2 - 1;
            length = 2;
        }
        else
        {
            position = str.length() / 2;
            length = 1;
        }
        return str.substring(position, position + length);
    }

    public HashMap<String, Object> checkMiddle(Class<?> solutionClass) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        String targetMethodName = "middle";
        HashMap<String, Object> result = new HashMap<>();
        ArrayList<HashMap<String, String>> hints = new ArrayList<>();

        Method targetMethod = solutionClass.getDeclaredMethod(targetMethodName, String.class);
        Class<?> trueReturnType = String.class;
        Class<?> returnType = targetMethod.getReturnType();
        if (returnType != trueReturnType) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", "方法 " + targetMethodName + " 的返回值类型不对，返回值类型必须为：" + trueReturnType);
            hints.add(hint);
            result.put("correct", false);
            result.put("hints", hints);
            return result;
        }

        String testInput1 = "350";
        String testInput2 = "1234";
        String testInput3 = "hello, world";

        targetMethod.setAccessible(true);
        String userMethodOutput1 = (String) targetMethod.invoke(null, testInput1);
        String userMethodOutput2 = (String) targetMethod.invoke(null, testInput2);
        String userMethodOutput3 = (String) targetMethod.invoke(null, testInput3);

        String trueOutput1 = middle(testInput1);
        String trueOutput2 = middle(testInput2);
        String trueOutput3 = middle(testInput3);

        boolean check1 = Objects.equals(userMethodOutput1, trueOutput1);
        boolean check2 = Objects.equals(userMethodOutput2, trueOutput2);
        boolean check3 = Objects.equals(userMethodOutput3, trueOutput3);

        if (!check1) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "(\"" + testInput1 + "\")的返回值应该是" + trueOutput1 + "，而不应该是" + userMethodOutput1);
            hints.add(hint);
        }
        if (!check2) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "(\"" + testInput2 + "\")的返回值应该是" + trueOutput2 + "，而不应该是" + userMethodOutput2);
            hints.add(hint);
        }
        if (!check3) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "(\"" + testInput3 + "\")的返回值应该是" + trueOutput3 + "，而不应该是" + userMethodOutput3);
            hints.add(hint);
        }
        result.put("correct", check1 && check2 && check3);
        result.put("hints", hints);
        return result;
    }

    public static String printChars(char char1, char char2, int n) {
        StringBuilder result = new StringBuilder();
        for (int ctr = 1; char1 <= char2; ctr++, char1++) {
            result.append(char1).append(" ");
            if (ctr % n == 0) result.append('\n');
        }
        return result.toString();
    }

    public HashMap<String, Object> checkPrintChars(Class<?> solutionClass) throws NoSuchMethodException {
        String targetMethodName = "printChars";
        HashMap<String, Object> result = new HashMap<>();
        ArrayList<HashMap<String, String>> hints = new ArrayList<>();

        Method targetMethod = null;
        Class<?>[] trueParameterTypes = {char.class, char.class, int.class};
        Class<?> trueReturnType = void.class;
        HashMap<String, Object> checkInputAndOutputResult = checkInputAndOutput(solutionClass, targetMethodName, trueParameterTypes, trueReturnType);
        if (!(boolean)checkInputAndOutputResult.get("correct")) {
            return checkInputAndOutputResult;
        }
        targetMethod = solutionClass.getDeclaredMethod(targetMethodName, trueParameterTypes);

        targetMethod.setAccessible(true);
        // 获取用户输出1
        final ByteArrayOutputStream userOutputStream = new ByteArrayOutputStream();
        PrintStream consoleStream = System.out;
        System.setOut(new PrintStream(userOutputStream));
        try {
            targetMethod.invoke(null, '(','z',20);
        } catch (Exception e) {
            assert e instanceof InvocationTargetException;
            InvocationTargetException targetEx = (InvocationTargetException) e;
            result.put("correct", false);
            result.put("hint", "runtimeException");
            result.put("runtimeException", targetEx);
            System.setOut(consoleStream);
            return result;
        }
        String userOutput1 = userOutputStream.toString();
        // 获取用户输出2
        userOutputStream.reset();
        try {
            targetMethod.invoke(null, '1','x',10);
        } catch (Exception e) {
            assert e instanceof InvocationTargetException;
            InvocationTargetException targetEx = (InvocationTargetException) e;
            result.put("correct", false);
            result.put("hint", "runtimeException");
            result.put("runtimeException", targetEx);
            System.setOut(consoleStream);
            return result;
        }
        String userOutput2 = userOutputStream.toString();
        System.setOut(consoleStream);

        String trueOutput1 = printChars('(', 'z', 20);
        String trueOutput2 = printChars('1', 'x', 10);
        boolean check1 = trueOutput1.equals(userOutput1);
        boolean check2 = trueOutput2.equals(userOutput2);

        if (!check1) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "('(', 'z', 20)的打印结果不是\n" + trueOutput1 + "\n而是\n" + userOutput1);
            hints.add(hint);
        }
        if (!check2) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "('1', 'x', 10)的打印结果不是\n" + trueOutput2 + "\n而是\n" + userOutput2);
            hints.add(hint);
        }

        result.put("correct", check1 && check2);
        result.put("hints", hints);
        return result;
    }

    public static boolean isLeapYear(int y) {
        boolean a = (y % 4) == 0;
        boolean b = (y % 100) != 0;
        boolean c = ((y % 100 == 0) && (y % 400 == 0));

        return a && (b || c);
    }

    public HashMap<String, Object> checkIsLeapYear(Class<?> solutionClass) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        String targetMethodName = "isLeapYear";
        HashMap<String, Object> result = new HashMap<>();
        ArrayList<HashMap<String, String>> hints = new ArrayList<>();

        Method targetMethod = null;
        Class<?>[] trueParameterTypes = {int.class};
        Class<?> trueReturnType = boolean.class;
        HashMap<String, Object> checkInputAndOutputResult = checkInputAndOutput(solutionClass, targetMethodName, trueParameterTypes, trueReturnType);
        if (!(boolean)checkInputAndOutputResult.get("correct")) {
            return checkInputAndOutputResult;
        }
        targetMethod = solutionClass.getDeclaredMethod(targetMethodName, trueParameterTypes);

        int testInput1 = random.nextInt(1000) + 1000;
        int testInput2 = random.nextInt(1000) + 1000;
        int testInput3 = random.nextInt(1000) + 1000;

        targetMethod.setAccessible(true);
        boolean userMethodOutput1 = (boolean) targetMethod.invoke(null, testInput1);
        boolean userMethodOutput2 = (boolean) targetMethod.invoke(null, testInput2);
        boolean userMethodOutput3 = (boolean) targetMethod.invoke(null, testInput3);

        boolean trueOutput1 = isLeapYear(testInput1);
        boolean trueOutput2 = isLeapYear(testInput2);
        boolean trueOutput3 = isLeapYear(testInput3);

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

    public static void printMatrix(int n) {
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                System.out.print((int)(Math.random() * 2) + " ");
            }
            System.out.println();
        }
    }

    public HashMap<String, Object> checkPrintMatrixHelper(String userOutput, int n) {
        HashMap<String, Object> checkResult = new HashMap<>();
        String[] tmp = userOutput.split("\n");
        int numCol = 0;
        for (int i = 0; i < tmp.length; i++) {
            String[] row = tmp[i].strip().split(" ");
            if (row.length != n) {
                checkResult.put("result", false);
                checkResult.put("hint", "矩阵第" + i + "行的元素个数不为" + n);
                return checkResult;
            }
            if (row.length != 0) {
                numCol += 1;
            }
        }
        if (numCol != n) {
            checkResult.put("result", false);
            checkResult.put("hint", "矩阵的行数不为" + n);
            return checkResult;
        }
        checkResult.put("result", true);
        return checkResult;
    }

    public HashMap<String, Object> checkPrintMatrix(Class<?> solutionClass) throws NoSuchMethodException {
        String targetMethodName = "printMatrix";
        HashMap<String, Object> result = new HashMap<>();
        ArrayList<HashMap<String, String>> hints = new ArrayList<>();

        Method targetMethod = null;
        Class<?>[] trueParameterTypes = {int.class};
        Class<?> trueReturnType = void.class;
        HashMap<String, Object> checkInputAndOutputResult = checkInputAndOutput(solutionClass, targetMethodName, trueParameterTypes, trueReturnType);
        if (!(boolean)checkInputAndOutputResult.get("correct")) {
            return checkInputAndOutputResult;
        }
        targetMethod = solutionClass.getDeclaredMethod(targetMethodName, trueParameterTypes);

        int testInput1 = random.nextInt(20) + 1;
        int testInput2 = random.nextInt(20) + 1;
        int testInput3 = random.nextInt(20) + 1;

        targetMethod.setAccessible(true);
        // 获取用户输出1
        final ByteArrayOutputStream userOutputStream = new ByteArrayOutputStream();
        PrintStream consoleStream = System.out;
        System.setOut(new PrintStream(userOutputStream));
        try {

            targetMethod.invoke(null, testInput1);
        } catch (Exception e) {
            assert e instanceof InvocationTargetException;
            InvocationTargetException targetEx = (InvocationTargetException) e;
            result.put("correct", false);
            result.put("hint", "runtimeException");
            result.put("runtimeException", targetEx);
            System.setOut(consoleStream);
            return result;
        }
        String userOutput1 = userOutputStream.toString();
        // 获取用户输出2
        userOutputStream.reset();
        try {
            targetMethod.invoke(null, testInput2);
        } catch (Exception e) {
            assert e instanceof InvocationTargetException;
            InvocationTargetException targetEx = (InvocationTargetException) e;
            result.put("correct", false);
            result.put("hint", "runtimeException");
            result.put("runtimeException", targetEx);
            System.setOut(consoleStream);
            return result;
        }
        String userOutput2 = userOutputStream.toString();
        // 获取用户输出3
        userOutputStream.reset();
        try {
            targetMethod.invoke(null, testInput3);
        } catch (Exception e) {
            assert e instanceof InvocationTargetException;
            InvocationTargetException targetEx = (InvocationTargetException) e;
            result.put("correct", false);
            result.put("hint", "runtimeException");
            result.put("runtimeException", targetEx);
            System.setOut(consoleStream);
            return result;
        }
        String userOutput3 = userOutputStream.toString();
        System.setOut(consoleStream);


        HashMap<String, Object> check1 = checkPrintMatrixHelper(userOutput1, testInput1);
        HashMap<String, Object> check2 = checkPrintMatrixHelper(userOutput2, testInput2);
        HashMap<String, Object> check3 = checkPrintMatrixHelper(userOutput3, testInput3);

        if (!(boolean) check1.get("result")) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "(" + testInput1 + ")的打印结果错误：" + check1.get("hint"));
            hints.add(hint);
        }
        if (!(boolean) check2.get("result")) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "(" + testInput2 + ")的打印结果错误：" + check2.get("hint"));
            hints.add(hint);
        }
        if (!(boolean) check3.get("result")) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "(" + testInput3 + ")的打印结果错误：" + check3.get("hint"));
            hints.add(hint);
        }

        result.put("correct", (boolean) check1.get("result") && (boolean) check2.get("result") && (boolean) check3.get("result"));
        result.put("hints", hints);
        return result;
    }

    public static boolean isPrime(long n) {

        if (n < 2) return false;

        for (int i = 2; i <= n / 2; i++) {

            if (n % i == 0) return false;
        }
        return true;
    }

    public static ArrayList<String> twinPrime(long n) {
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 2; i < n; i++) {
            if (isPrime(i) && isPrime(i + 2)) {
                strings.add("(" + i + "," + (i+2) + ")");
            }
        }
        return strings;
    }

    public HashMap<String, Object> checkTwinPrime(Class<?> solutionClass) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        String targetMethodName = "twinPrime";
        HashMap<String, Object> result = new HashMap<>();
        ArrayList<HashMap<String, String>> hints = new ArrayList<>();

        Method targetMethod = null;
        Class<?>[] trueParameterTypes = {long.class};
        Class<?> trueReturnType = ArrayList.class;
        HashMap<String, Object> checkInputAndOutputResult = checkInputAndOutput(solutionClass, targetMethodName, trueParameterTypes, trueReturnType);
        if (!(boolean)checkInputAndOutputResult.get("correct")) {
            return checkInputAndOutputResult;
        }
        targetMethod = solutionClass.getDeclaredMethod(targetMethodName, trueParameterTypes);

        long testInput1 = 100;
        long testInput2 = 200;
        long testInput3 = 1000;

        targetMethod.setAccessible(true);
        ArrayList<String> userMethodOutput1 = (ArrayList<String>) targetMethod.invoke(null, testInput1);
        ArrayList<String> userMethodOutput2 = (ArrayList<String>) targetMethod.invoke(null, testInput2);
        ArrayList<String> userMethodOutput3 = (ArrayList<String>) targetMethod.invoke(null, testInput3);

        ArrayList<String> trueOutput1 = twinPrime(testInput1);
        ArrayList<String> trueOutput2 = twinPrime(testInput2);
        ArrayList<String> trueOutput3 = twinPrime(testInput3);

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

    public static int calculateFactorial(int n) {
        // Base case: factorial of 0 is 1
        if (n == 0) {
            return 1;
        }

        // Recursive case: multiply n with factorial of (n-1)
        return n * calculateFactorial(n - 1);
    }

    public HashMap<String, Object> checkCalculateFactorial(Class<?> solutionClass) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        String targetMethodName = "calculateFactorial";
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

        int testInput1 = random.nextInt(3);
        int testInput2 = random.nextInt(3) + 3;
        int testInput3 = random.nextInt(3) + 6;

        targetMethod.setAccessible(true);
        int userMethodOutput1 = (int) targetMethod.invoke(null, testInput1);
        int userMethodOutput2 = (int) targetMethod.invoke(null, testInput2);
        int userMethodOutput3 = (int) targetMethod.invoke(null, testInput3);

        int trueOutput1 = calculateFactorial(testInput1);
        int trueOutput2 = calculateFactorial(testInput2);
        int trueOutput3 = calculateFactorial(testInput3);

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

    public static boolean isNumeric(char ch) {

        return (ch >= '0' && ch <= '9');
    }

    public static boolean isLetter(char ch) {
        ch = Character.toUpperCase(ch);
        return (ch >= 'A' && ch <= 'Z');
    }

    public static boolean isValidPassword(String password) {

        if (password.length() < PASSWORD_LENGTH) return false;

        int charCount = 0;
        int numCount = 0;
        for (int i = 0; i < password.length(); i++) {

            char ch = password.charAt(i);

            if (isNumeric(ch)) numCount++;
            else if (isLetter(ch)) charCount++;
            else return false;
        }


        return (charCount >= 2 && numCount >= 2);
    }

    public HashMap<String, Object> checkIsValidPassword(Class<?> solutionClass) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        String targetMethodName = "isValidPassword";
        HashMap<String, Object> result = new HashMap<>();
        ArrayList<HashMap<String, String>> hints = new ArrayList<>();

        Method targetMethod = null;
        Class<?>[] trueParameterTypes = {String.class};
        Class<?> trueReturnType = boolean.class;
        HashMap<String, Object> checkInputAndOutputResult = checkInputAndOutput(solutionClass, targetMethodName, trueParameterTypes, trueReturnType);
        if (!(boolean)checkInputAndOutputResult.get("correct")) {
            return checkInputAndOutputResult;
        }
        targetMethod = solutionClass.getDeclaredMethod(targetMethodName, trueParameterTypes);

        String testInput1 = "dream12345";
        String testInput2 = "dream123";
        String testInput3 = "dream123!!";

        targetMethod.setAccessible(true);
        boolean userMethodOutput1 = (boolean) targetMethod.invoke(null, testInput1);
        boolean userMethodOutput2 = (boolean) targetMethod.invoke(null, testInput2);
        boolean userMethodOutput3 = (boolean) targetMethod.invoke(null, testInput3);

        boolean trueOutput1 = isValidPassword(testInput1);
        boolean trueOutput2 = isValidPassword(testInput2);
        boolean trueOutput3 = isValidPassword(testInput3);

        boolean check1 = Objects.equals(userMethodOutput1, trueOutput1);
        boolean check2 = Objects.equals(userMethodOutput2, trueOutput2);
        boolean check3 = Objects.equals(userMethodOutput3, trueOutput3);

        if (!check1) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "(\"" + testInput1 + "\")的返回值应该是" + trueOutput1 + "，而不应该是" + userMethodOutput1);
            hints.add(hint);
        }
        if (!check2) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "(\"" + testInput2 + "\")的返回值应该是" + trueOutput2 + "，而不应该是" + userMethodOutput2);
            hints.add(hint);
        }
        if (!check3) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "(\"" + testInput3 + "\")的返回值应该是" + trueOutput3 + "，而不应该是" + userMethodOutput3);
            hints.add(hint);
        }
        result.put("correct", check1 && check2 && check3);
        result.put("hints", hints);
        return result;
    }

    public static int calculateOddNumberSum(int[] arr) {
        return calculateOddNumberSum(arr, 0);
    }

    private static int calculateOddNumberSum(int[] arr, int index) {
        // Base case: if the index reaches the end of the array, return 0
        if (index == arr.length) {
            return 0;
        }

        // Recursive case: check if the element at the current index is odd,
        // and recursively call the method with the next index and add the current element if it is odd
        int sum = calculateOddNumberSum(arr, index + 1);
        if (arr[index] % 2 != 0) {
            sum += arr[index];
        }

        return sum;
    }

    public HashMap<String, Object> checkCalculateOddNumberSum(Class<?> solutionClass) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        String targetMethodName = "calculateOddNumberSum";
        HashMap<String, Object> result = new HashMap<>();
        ArrayList<HashMap<String, String>> hints = new ArrayList<>();

        Method targetMethod = null;
        Class<?>[] trueParameterTypes = {int[].class};
        Class<?> trueReturnType = int.class;
        HashMap<String, Object> checkInputAndOutputResult = checkInputAndOutput(solutionClass, targetMethodName, trueParameterTypes, trueReturnType);
        if (!(boolean)checkInputAndOutputResult.get("correct")) {
            return checkInputAndOutputResult;
        }
        targetMethod = solutionClass.getDeclaredMethod(targetMethodName, trueParameterTypes);

        int arrayNum1 = random.nextInt(10) + 20;
        int arrayNum2 = random.nextInt(10) + 20;
        int arrayNum3 = random.nextInt(10) + 20;
        int[] testInput1 = exerciseUtil.generateIntArray(1, 100, arrayNum1);
        int[] testInput2 = exerciseUtil.generateIntArray(1, 100, arrayNum2);
        int[] testInput3 = exerciseUtil.generateIntArray(1, 100, arrayNum3);

        targetMethod.setAccessible(true);
        int userMethodOutput1 = (int) targetMethod.invoke(null, testInput1);
        int userMethodOutput2 = (int) targetMethod.invoke(null, testInput2);
        int userMethodOutput3 = (int) targetMethod.invoke(null, testInput3);

        int trueOutput1 = calculateOddNumberSum(testInput1);
        int trueOutput2 = calculateOddNumberSum(testInput2);
        int trueOutput3 = calculateOddNumberSum(testInput3);

        boolean check1 = Objects.equals(userMethodOutput1, trueOutput1);
        boolean check2 = Objects.equals(userMethodOutput2, trueOutput2);
        boolean check3 = Objects.equals(userMethodOutput3, trueOutput3);

        if (!check1) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "(" + Arrays.toString(testInput1) + ")的返回值应该是" + trueOutput1 + "，而不应该是" + userMethodOutput1);
            hints.add(hint);
        }
        if (!check2) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "(" + Arrays.toString(testInput2) + ")的返回值应该是" + trueOutput2 + "，而不应该是" + userMethodOutput2);
            hints.add(hint);
        }
        if (!check3) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "(" + Arrays.toString(testInput3) + ")的返回值应该是" + trueOutput3 + "，而不应该是" + userMethodOutput3);
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
        String targetMethodName = exercise.targetMethodName;

        HashMap<String, Object> checkResult = JavaProgramExercise.check(submissionCode, targetMethodName, "XZJ");
        System.out.println(checkResult);
    }

    public static void main(String[] args) throws Exception {
        String fPath = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "exercises", "program", "java", "testProgramExercise.json").toString();
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
