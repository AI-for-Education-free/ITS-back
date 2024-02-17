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


public class JavaProgramExerciseChecker {
    public static final int PASSWORD_LENGTH = 10;
    private final Random random = new Random();
    private final Util exerciseUtil = new Util();

    public static void printHello() {
        System.out.println("hello\ndream");
    }

    public static double smallest(double x, double y, double z) {
        return Math.min(Math.min(x, y), z);
    }

    public static double average(double x, double y, double z) {
        return (x + y + z) / 3;
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

    public static int countVowels(String str) {
        int count = 0;
        for (int i = 0; i < str.length(); i++)
        {
            if (str.charAt(i) == 'a' || str.charAt(i) == 'e' || str.charAt(i) == 'i'
                    || str.charAt(i) == 'o' || str.charAt(i) == 'u')
            {
                count++;
            }
        }
        return count;
    }

    public static int countWords(String str) {
        int count = 0;
        if (!(" ".equals(str.substring(0, 1))) || !(" ".equals(str.substring(str.length() - 1))))
        {
            for (int i = 0; i < str.length(); i++)
            {
                if (str.charAt(i) == ' ')
                {
                    count++;
                }
            }
            count = count + 1;
        }
        // returns 0 if string starts or ends with space " ".
        return count;
    }

    public static int sumDigits(long n) {
        int result = 0;

        while(n > 0) {
            result += n % 10;
            n /= 10;
        }

        return result;
    }

    public static int getPentagonalNumber(int i) {
        return (i * (3 * i - 1))/2;
    }

    public static double futureInvestmentValue(double investmentAmount, double monthlyInterestRate, int years) {
        return investmentAmount * Math.pow(1 + monthlyInterestRate, years * 12);
    }

    public static String printChars(char char1, char char2, int n) {
        StringBuilder result = new StringBuilder();
        for (int ctr = 1; char1 <= char2; ctr++, char1++) {
            result.append(char1).append(" ");
            if (ctr % n == 0) result.append('\n');
        }
        return result.toString();
    }

    public static boolean isLeapYear(int y) {
        boolean a = (y % 4) == 0;
        boolean b = (y % 100) != 0;
        boolean c = ((y % 100 == 0) && (y % 400 == 0));

        return a && (b || c);
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

    public static boolean isLetter(char ch) {
        ch = Character.toUpperCase(ch);
        return (ch >= 'A' && ch <= 'Z');
    }

    public static boolean isNumeric(char ch) {

        return (ch >= '0' && ch <= '9');
    }

    public static void printMatrix(int n) {
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                System.out.print((int)(Math.random() * 2) + " ");
            }
            System.out.println();
        }
    }

    public static double areaTriangle(double side1, double side2, double side3) {
        double area = 0;
        double s = (side1 + side2 + side3)/2;
        area = Math.sqrt(s*(s - side1)*(s - side2)*(s - side3));
        return area;
    }

    public static double pentagonArea(int n, double s) {
        return  (n * s * s) / (4 * Math.tan(Math.PI/n));
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

    public static int countValue(int num, int k) {
        int ctr = 0;
        int n = num;
        do{
            if (n % 10 == k){
                ctr ++;
            }
            n /= 10;
        }while(n > 0);
        return ctr;
    }

    public static boolean isConsecutive(int x, int y, int z) {
        int max_num = Math.max(x, Math.max(y, z));
        int min_num = Math.min(x, Math.min(y, z));
        int middle_num = x+y+z - max_num - min_num;
        return (max_num - middle_num) == 1 && (middle_num - min_num == 1);
    }

    public static boolean methodTest19(int x, int y, int z) {
        int max = Math.max(x, Math.max(y,z));
        int min = Math.min(x, Math.min(y,z));
        double mid_point1 = (max + min) / 2.0;
        int mid_point2 = x + y + z - max - min;
        return (mid_point1 == mid_point2);
    }

    public static int methodTest20 (int n) {
        int fact_num = 10;
        while(n / fact_num != 0){
            fact_num *= 10;
        }
        return Math.abs(n / (fact_num/10));
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

    public HashMap<String, Object> checkAverage(Class<?> solutionClass) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        String targetMethodName = "average";
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
        double userMethodOutput1 = (double) targetMethod.invoke(null, 25, 45, 65);
        double userMethodOutput2 = (double) targetMethod.invoke(null, 2, -5, 10);
        double userMethodOutput3 = (double) targetMethod.invoke(null, 144, 295, 100);

        double trueOutput1 = average(25, 45, 65);
        double trueOutput2 = average(2, -5, 10);
        double trueOutput3 = average(144, 295, 100);

        boolean check1 = userMethodOutput1 == trueOutput1;
        boolean check2 = userMethodOutput2 == trueOutput2;
        boolean check3 = userMethodOutput3 == trueOutput3;
        if (!check1) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "(25, 45, 65)的返回值应该是" + trueOutput1 + "，而不应该是" + userMethodOutput1);
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

    public HashMap<String, Object> checkMiddle(Class<?> solutionClass) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        String targetMethodName = "middle";
        HashMap<String, Object> result = new HashMap<>();
        ArrayList<HashMap<String, String>> hints = new ArrayList<>();

        Method targetMethod = null;
        Class<?>[] trueParameterTypes = {String.class};
        Class<?> trueReturnType = String.class;
        HashMap<String, Object> checkInputAndOutputResult = checkInputAndOutput(solutionClass, targetMethodName, trueParameterTypes, trueReturnType);
        if (!(boolean)checkInputAndOutputResult.get("correct")) {
            return checkInputAndOutputResult;
        }
        targetMethod = solutionClass.getDeclaredMethod(targetMethodName, trueParameterTypes);

        targetMethod.setAccessible(true);
        String userMethodOutput1 = (String) targetMethod.invoke(null, "350");
        String userMethodOutput2 = (String) targetMethod.invoke(null, "1234");
        String userMethodOutput3 = (String) targetMethod.invoke(null, "hello, world");

        String trueOutput1 = middle("350");
        String trueOutput2 = middle("1234");
        String trueOutput3 = middle("hello, world");

        boolean check1 = Objects.equals(userMethodOutput1, trueOutput1);
        boolean check2 = Objects.equals(userMethodOutput2, trueOutput2);
        boolean check3 = Objects.equals(userMethodOutput3, trueOutput3);
        if (!check1) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "(\"350\")的返回值应该是" + trueOutput1 + "，而不应该是" + userMethodOutput1);
            hints.add(hint);
        }
        if (!check2) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "(\"1234\")的返回值应该是" + trueOutput2 + "，而不应该是" + userMethodOutput2);
            hints.add(hint);
        }
        if (!check3) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "(\"hello, world\")的返回值应该是" + trueOutput3 + "，而不应该是" + userMethodOutput3);
            hints.add(hint);
        }
        result.put("correct", check1 && check2 && check3);
        result.put("hints", hints);
        return result;
    }

    public HashMap<String, Object> checkCountVowels(Class<?> solutionClass) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        String targetMethodName = "countVowels";
        HashMap<String, Object> result = new HashMap<>();
        ArrayList<HashMap<String, String>> hints = new ArrayList<>();

        Method targetMethod = null;
        Class<?>[] trueParameterTypes = {String.class};
        Class<?> trueReturnType = int.class;
        HashMap<String, Object> checkInputAndOutputResult = checkInputAndOutput(solutionClass, targetMethodName, trueParameterTypes, trueReturnType);
        if (!(boolean)checkInputAndOutputResult.get("correct")) {
            return checkInputAndOutputResult;
        }
        targetMethod = solutionClass.getDeclaredMethod(targetMethodName, trueParameterTypes);

        targetMethod.setAccessible(true);
        int userMethodOutput1 = (int) targetMethod.invoke(null, "hello, world");
        int userMethodOutput2 = (int) targetMethod.invoke(null, "Do you like programing?");
        int userMethodOutput3 = (int) targetMethod.invoke(null, "I think programing is interesting");

        int trueOutput1 = countVowels("hello, world");
        int trueOutput2 = countVowels("Do you like programing?");
        int trueOutput3 = countVowels("I think programing is interesting");

        boolean check1 = Objects.equals(userMethodOutput1, trueOutput1);
        boolean check2 = Objects.equals(userMethodOutput2, trueOutput2);
        boolean check3 = Objects.equals(userMethodOutput3, trueOutput3);
        if (!check1) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "(\"hello, world\")的返回值应该是" + trueOutput1 + "，而不应该是" + userMethodOutput1);
            hints.add(hint);
        }
        if (!check2) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "(\"Do you like programing?\")的返回值应该是" + trueOutput2 + "，而不应该是" + userMethodOutput2);
            hints.add(hint);
        }
        if (!check3) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "(\"I think programing is interesting\")的返回值应该是" + trueOutput3 + "，而不应该是" + userMethodOutput3);
            hints.add(hint);
        }
        result.put("correct", check1 && check2 && check3);
        result.put("hints", hints);
        return result;
    }

    public HashMap<String, Object> checkCountWords(Class<?> solutionClass) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        String targetMethodName = "countWords";
        HashMap<String, Object> result = new HashMap<>();
        ArrayList<HashMap<String, String>> hints = new ArrayList<>();

        Method targetMethod = null;
        Class<?>[] trueParameterTypes = {String.class};
        Class<?> trueReturnType = int.class;
        HashMap<String, Object> checkInputAndOutputResult = checkInputAndOutput(solutionClass, targetMethodName, trueParameterTypes, trueReturnType);
        if (!(boolean)checkInputAndOutputResult.get("correct")) {
            return checkInputAndOutputResult;
        }
        targetMethod = solutionClass.getDeclaredMethod(targetMethodName, trueParameterTypes);

        targetMethod.setAccessible(true);
        int userMethodOutput1 = (int) targetMethod.invoke(null, "hello, world");
        int userMethodOutput2 = (int) targetMethod.invoke(null, "Do you like programing?");
        int userMethodOutput3 = (int) targetMethod.invoke(null, "I think programing is interesting");

        int trueOutput1 = countWords("hello, world");
        int trueOutput2 = countWords("Do you like programing?");
        int trueOutput3 = countWords("I think programing is interesting");

        boolean check1 = Objects.equals(userMethodOutput1, trueOutput1);
        boolean check2 = Objects.equals(userMethodOutput2, trueOutput2);
        boolean check3 = Objects.equals(userMethodOutput3, trueOutput3);
        if (!check1) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "(\"hello, world\")的返回值应该是" + trueOutput1 + "，而不应该是" + userMethodOutput1);
            hints.add(hint);
        }
        if (!check2) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "(\"Do you like programing?\")的返回值应该是" + trueOutput2 + "，而不应该是" + userMethodOutput2);
            hints.add(hint);
        }
        if (!check3) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "(\"I think programing is interesting\")的返回值应该是" + trueOutput3 + "，而不应该是" + userMethodOutput3);
            hints.add(hint);
        }
        result.put("correct", check1 && check2 && check3);
        result.put("hints", hints);
        return result;
    }

    public HashMap<String, Object> checkSumDigits(Class<?> solutionClass) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        String targetMethodName = "sumDigits";
        HashMap<String, Object> result = new HashMap<>();
        ArrayList<HashMap<String, String>> hints = new ArrayList<>();

        Method targetMethod = null;
        Class<?>[] trueParameterTypes = {long.class};
        Class<?> trueReturnType = int.class;
        HashMap<String, Object> checkInputAndOutputResult = checkInputAndOutput(solutionClass, targetMethodName, trueParameterTypes, trueReturnType);
        if (!(boolean)checkInputAndOutputResult.get("correct")) {
            return checkInputAndOutputResult;
        }
        targetMethod = solutionClass.getDeclaredMethod(targetMethodName, trueParameterTypes);

        long testInput1 = random.nextLong() % 1000;
        long testInput2 = random.nextLong() % 1000;
        long testInput3 = random.nextLong() % 1000;

        targetMethod.setAccessible(true);
        int userMethodOutput1 = (int) targetMethod.invoke(null, testInput1);
        int userMethodOutput2 = (int) targetMethod.invoke(null, testInput2);
        int userMethodOutput3 = (int) targetMethod.invoke(null, testInput3);

        int trueOutput1 = sumDigits(testInput1);
        int trueOutput2 = sumDigits(testInput2);
        int trueOutput3 = sumDigits(testInput3);

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

    public HashMap<String, Object> checkGetPentagonalNumber(Class<?> solutionClass) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        String targetMethodName = "getPentagonalNumber";
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

        int testInput1 = random.nextInt(100) + 1;
        int testInput2 = random.nextInt(100) + 1;
        int testInput3 = random.nextInt(100) + 1;
        int testInput4 = random.nextInt(100) + 1;
        int testInput5 = random.nextInt(100) + 1;

        targetMethod.setAccessible(true);

        int userMethodOutput1;
        int userMethodOutput2;
        int userMethodOutput3;
        int userMethodOutput4;
        int userMethodOutput5;

        userMethodOutput1 = (int) targetMethod.invoke(null, testInput1);
        userMethodOutput2 = (int) targetMethod.invoke(null, testInput2);
        userMethodOutput3 = (int) targetMethod.invoke(null, testInput3);
        userMethodOutput4 = (int) targetMethod.invoke(null, testInput4);
        userMethodOutput5 = (int) targetMethod.invoke(null, testInput5);


        int trueOutput1 = getPentagonalNumber(testInput1);
        int trueOutput2 = getPentagonalNumber(testInput2);
        int trueOutput3 = getPentagonalNumber(testInput3);
        int trueOutput4 = getPentagonalNumber(testInput4);
        int trueOutput5 = getPentagonalNumber(testInput5);

        boolean check1 = Objects.equals(userMethodOutput1, trueOutput1);
        boolean check2 = Objects.equals(userMethodOutput2, trueOutput2);
        boolean check3 = Objects.equals(userMethodOutput3, trueOutput3);
        boolean check4 = Objects.equals(userMethodOutput4, trueOutput4);
        boolean check5 = Objects.equals(userMethodOutput5, trueOutput5);

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
        if (!check4) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "(" + testInput4 + ")的返回值应该是" + trueOutput4 + "，而不应该是" + userMethodOutput4);
            hints.add(hint);
        }
        if (!check5) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "(" + testInput5 + ")的返回值应该是" + trueOutput5 + "，而不应该是" + userMethodOutput5);
            hints.add(hint);
        }
        result.put("correct", check1 && check2 && check3 && check4 && check5);
        result.put("hints", hints);
        return result;
    }

    public HashMap<String, Object> checkFutureInvestmentValue (Class<?> solutionClass) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        String targetMethodName = "futureInvestmentValue";
        HashMap<String, Object> result = new HashMap<>();
        ArrayList<HashMap<String, String>> hints = new ArrayList<>();

        Method targetMethod = null;
        Class<?>[] trueParameterTypes = {double.class, double.class, int.class};
        Class<?> trueReturnType = double.class;
        HashMap<String, Object> checkInputAndOutputResult = checkInputAndOutput(solutionClass, targetMethodName, trueParameterTypes, trueReturnType);
        if (!(boolean)checkInputAndOutputResult.get("correct")) {
            return checkInputAndOutputResult;
        }
        targetMethod = solutionClass.getDeclaredMethod(targetMethodName, trueParameterTypes);

        targetMethod.setAccessible(true);
        double userMethodOutput1 = (double) targetMethod.invoke(null, 1000, 0.01, 4);
        double userMethodOutput2 = (double) targetMethod.invoke(null, 2000, 0.03, 5);
        double userMethodOutput3 = (double) targetMethod.invoke(null, 10000, 0.05, 20);

        double trueOutput1 = futureInvestmentValue(1000, 0.01, 4);
        double trueOutput2 = futureInvestmentValue(2000, 0.03, 5);
        double trueOutput3 = futureInvestmentValue(10000, 0.05, 20);

        boolean check1 = userMethodOutput1 == trueOutput1;
        boolean check2 = userMethodOutput2 == trueOutput2;
        boolean check3 = userMethodOutput3 == trueOutput3;
        if (!check1) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "(1000, 0.01, 4)的返回值应该是" + trueOutput1 + "，而不应该是" + userMethodOutput1);
            hints.add(hint);
        }
        if (!check2) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "(2000, 0.03, 5)的返回值应该是" + trueOutput2 + "，而不应该是" + userMethodOutput2);
            hints.add(hint);
        }
        if (!check3) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "(10000, 0.05, 20)的返回值应该是" + trueOutput3 + "，而不应该是" + userMethodOutput3);
            hints.add(hint);
        }
        result.put("correct", check1 && check2 && check3);
        result.put("hints", hints);
        return result;
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

    public HashMap<String, Object> checkAreaTriangle(Class<?> solutionClass) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        String targetMethodName = "areaTriangle";
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
        double userMethodOutput1 = (double) targetMethod.invoke(null, 10, 15, 20);
        double userMethodOutput2 = (double) targetMethod.invoke(null, 3, 4, 5);
        double userMethodOutput3 = (double) targetMethod.invoke(null, 5.4, 7.8, 4.3);

        double trueOutput1 = areaTriangle(10, 15, 20);
        double trueOutput2 = areaTriangle(3, 4, 5);
        double trueOutput3 = areaTriangle(5.4, 7.8, 4.3);

        boolean check1 = userMethodOutput1 == trueOutput1;
        boolean check2 = userMethodOutput2 == trueOutput2;
        boolean check3 = userMethodOutput3 == trueOutput3;
        if (!check1) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "(10, 15, 20)的返回值应该是" + trueOutput1 + "，而不应该是" + userMethodOutput1);
            hints.add(hint);
        }
        if (!check2) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "(3, 4, 5)的返回值应该是" + trueOutput2 + "，而不应该是" + userMethodOutput2);
            hints.add(hint);
        }
        if (!check3) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "(5.4, 7.8, 4.3)的返回值应该是" + trueOutput3 + "，而不应该是" + userMethodOutput3);
            hints.add(hint);
        }
        result.put("correct", check1 && check2 && check3);
        result.put("hints", hints);
        return result;
    }

    public HashMap<String, Object> checkPentagonArea(Class<?> solutionClass) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        String targetMethodName = "pentagonArea";
        HashMap<String, Object> result = new HashMap<>();
        ArrayList<HashMap<String, String>> hints = new ArrayList<>();

        Method targetMethod = null;
        Class<?>[] trueParameterTypes = {int.class, double.class};
        Class<?> trueReturnType = double.class;
        HashMap<String, Object> checkInputAndOutputResult = checkInputAndOutput(solutionClass, targetMethodName, trueParameterTypes, trueReturnType);
        if (!(boolean)checkInputAndOutputResult.get("correct")) {
            return checkInputAndOutputResult;
        }
        targetMethod = solutionClass.getDeclaredMethod(targetMethodName, trueParameterTypes);

        targetMethod.setAccessible(true);
        double userMethodOutput1 = (double) targetMethod.invoke(null, 5, 6);
        double userMethodOutput2 = (double) targetMethod.invoke(null, 3, 8.1);
        double userMethodOutput3 = (double) targetMethod.invoke(null, 7, 10);

        double trueOutput1 = pentagonArea(5, 6);
        double trueOutput2 = pentagonArea(3, 8.1);
        double trueOutput3 = pentagonArea(7, 10);

        boolean check1 = userMethodOutput1 == trueOutput1;
        boolean check2 = userMethodOutput2 == trueOutput2;
        boolean check3 = userMethodOutput3 == trueOutput3;
        if (!check1) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "(5, 6)的返回值应该是" + trueOutput1 + "，而不应该是" + userMethodOutput1);
            hints.add(hint);
        }
        if (!check2) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "(3, 8.1)的返回值应该是" + trueOutput2 + "，而不应该是" + userMethodOutput2);
            hints.add(hint);
        }
        if (!check3) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "(7, 10)的返回值应该是" + trueOutput3 + "，而不应该是" + userMethodOutput3);
            hints.add(hint);
        }
        result.put("correct", check1 && check2 && check3);
        result.put("hints", hints);
        return result;
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

    public HashMap<String, Object> checkCountValue(Class<?> solutionClass) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        String targetMethodName = "countValue";
        HashMap<String, Object> result = new HashMap<>();
        ArrayList<HashMap<String, String>> hints = new ArrayList<>();

        Method targetMethod = null;
        Class<?>[] trueParameterTypes = {int.class, int.class};
        Class<?> trueReturnType = int.class;
        HashMap<String, Object> checkInputAndOutputResult = checkInputAndOutput(solutionClass, targetMethodName, trueParameterTypes, trueReturnType);
        if (!(boolean)checkInputAndOutputResult.get("correct")) {
            return checkInputAndOutputResult;
        }
        targetMethod = solutionClass.getDeclaredMethod(targetMethodName, trueParameterTypes);

        targetMethod.setAccessible(true);
        int userMethodOutput1 = (int) targetMethod.invoke(null, 12541, 2);
        int userMethodOutput2 = (int) targetMethod.invoke(null, 41326243, 3);
        int userMethodOutput3 = (int) targetMethod.invoke(null, 12246009, 0);

        int trueOutput1 = countValue(12541, 2);
        int trueOutput2 = countValue(41326243, 3);
        int trueOutput3 = countValue(12246009, 0);

        boolean check1 = userMethodOutput1 == trueOutput1;
        boolean check2 = userMethodOutput2 == trueOutput2;
        boolean check3 = userMethodOutput3 == trueOutput3;
        if (!check1) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "(12541, 2)的返回值应该是" + trueOutput1 + "，而不应该是" + userMethodOutput1);
            hints.add(hint);
        }
        if (!check2) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "(41326243, 3)的返回值应该是" + trueOutput2 + "，而不应该是" + userMethodOutput2);
            hints.add(hint);
        }
        if (!check3) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "(12246009, 0)的返回值应该是" + trueOutput3 + "，而不应该是" + userMethodOutput3);
            hints.add(hint);
        }
        result.put("correct", check1 && check2 && check3);
        result.put("hints", hints);
        return result;
    }

    public HashMap<String, Object> checkIsConsecutive(Class<?> solutionClass) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        String targetMethodName = "isConsecutive";
        HashMap<String, Object> result = new HashMap<>();
        ArrayList<HashMap<String, String>> hints = new ArrayList<>();

        Method targetMethod = null;
        Class<?>[] trueParameterTypes = {int.class, int.class, int.class};
        Class<?> trueReturnType = boolean.class;
        HashMap<String, Object> checkInputAndOutputResult = checkInputAndOutput(solutionClass, targetMethodName, trueParameterTypes, trueReturnType);
        if (!(boolean)checkInputAndOutputResult.get("correct")) {
            return checkInputAndOutputResult;
        }
        targetMethod = solutionClass.getDeclaredMethod(targetMethodName, trueParameterTypes);

        targetMethod.setAccessible(true);
        boolean userMethodOutput1 = (boolean) targetMethod.invoke(null, 15, 16, 17);
        boolean userMethodOutput2 = (boolean) targetMethod.invoke(null, 20, 22, 25);
        boolean userMethodOutput3 = (boolean) targetMethod.invoke(null, -1, -3, -2);
        boolean userMethodOutput4 = (boolean) targetMethod.invoke(null, 10, 12, 25);

        boolean trueOutput1 = isConsecutive(15, 16, 17);
        boolean trueOutput2 = isConsecutive(20, 22, 25);
        boolean trueOutput3 = isConsecutive(-1, -3, -2);
        boolean trueOutput4 = isConsecutive(10, 12, 25);

        boolean check1 = userMethodOutput1 == trueOutput1;
        boolean check2 = userMethodOutput2 == trueOutput2;
        boolean check3 = userMethodOutput3 == trueOutput3;
        boolean check4 = userMethodOutput4 == trueOutput4;
        if (!check1) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "(15, 16, 17)的返回值应该是" + trueOutput1 + "，而不应该是" + userMethodOutput1);
            hints.add(hint);
        }
        if (!check2) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "(20, 22, 25)的返回值应该是" + trueOutput2 + "，而不应该是" + userMethodOutput2);
            hints.add(hint);
        }
        if (!check3) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "(-1, -3, -2)的返回值应该是" + trueOutput3 + "，而不应该是" + userMethodOutput3);
            hints.add(hint);
        }
        if (!check4) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "(-1, -3, -2)的返回值应该是" + trueOutput4 + "，而不应该是" + userMethodOutput4);
            hints.add(hint);
        }
        result.put("correct", check1 && check2 && check3 && check4);
        result.put("hints", hints);
        return result;
    }

    public HashMap<String, Object> checkMethodTest19(Class<?> solutionClass) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        String targetMethodName = "methodTest19";
        HashMap<String, Object> result = new HashMap<>();
        ArrayList<HashMap<String, String>> hints = new ArrayList<>();

        Method targetMethod = null;
        Class<?>[] trueParameterTypes = {int.class, int.class, int.class};
        Class<?> trueReturnType = boolean.class;
        HashMap<String, Object> checkInputAndOutputResult = checkInputAndOutput(solutionClass, targetMethodName, trueParameterTypes, trueReturnType);
        if (!(boolean)checkInputAndOutputResult.get("correct")) {
            return checkInputAndOutputResult;
        }
        targetMethod = solutionClass.getDeclaredMethod(targetMethodName, trueParameterTypes);

        targetMethod.setAccessible(true);
        boolean userMethodOutput1 = (boolean) targetMethod.invoke(null, 2,4,6);
        boolean userMethodOutput2 = (boolean) targetMethod.invoke(null, 20,7,-6);
        boolean userMethodOutput3 = (boolean) targetMethod.invoke(null, 1,2,5);
        boolean userMethodOutput4 = (boolean) targetMethod.invoke(null, 3,9,14);

        boolean trueOutput1 = methodTest19(2,4,6);
        boolean trueOutput2 = methodTest19(20,7,-6);
        boolean trueOutput3 = methodTest19(1,2,5);
        boolean trueOutput4 = methodTest19(3,9,14);

        boolean check1 = userMethodOutput1 == trueOutput1;
        boolean check2 = userMethodOutput2 == trueOutput2;
        boolean check3 = userMethodOutput3 == trueOutput3;
        boolean check4 = userMethodOutput4 == trueOutput4;
        if (!check1) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "(2,4,6)的返回值应该是" + trueOutput1 + "，而不应该是" + userMethodOutput1);
            hints.add(hint);
        }
        if (!check2) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "(20,7,-6)的返回值应该是" + trueOutput2 + "，而不应该是" + userMethodOutput2);
            hints.add(hint);
        }
        if (!check3) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "(1,2,5)的返回值应该是" + trueOutput3 + "，而不应该是" + userMethodOutput3);
            hints.add(hint);
        }
        if (!check4) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "(3,9,14)的返回值应该是" + trueOutput4 + "，而不应该是" + userMethodOutput4);
            hints.add(hint);
        }
        result.put("correct", check1 && check2 && check3 && check4);
        result.put("hints", hints);
        return result;
    }

    public HashMap<String, Object> checkMethodTest20(Class<?> solutionClass) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        String targetMethodName = "methodTest20";
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

        int testInput1 = 123;
        int testInput2 = 354;
        int testInput3 = -634;

        targetMethod.setAccessible(true);
        int userMethodOutput1 = (int) targetMethod.invoke(null, testInput1);
        int userMethodOutput2 = (int) targetMethod.invoke(null, testInput2);
        int userMethodOutput3 = (int) targetMethod.invoke(null, testInput3);


        int trueOutput1 = methodTest20(testInput1);
        int trueOutput2 = methodTest20(testInput2);
        int trueOutput3 = methodTest20(testInput3);


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

    public static int calculateSum(int n) {
        // Base case: sum of 0 is 0
        if (n == 0) {
            return 0;
        }

        // Recursive case: add n with the sum of (n-1)
        return n + calculateSum(n - 1);
    }

    public HashMap<String, Object> checkCalculateSum(Class<?> solutionClass) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        String targetMethodName = "calculateSum";
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

        int testInput1 = random.nextInt(10);
        int testInput2 = random.nextInt(100) + 10;
        int testInput3 = random.nextInt(1000) + 100;

        targetMethod.setAccessible(true);
        int userMethodOutput1 = (int) targetMethod.invoke(null, testInput1);
        int userMethodOutput2 = (int) targetMethod.invoke(null, testInput2);
        int userMethodOutput3 = (int) targetMethod.invoke(null, testInput3);

        int trueOutput1 = calculateSum(testInput1);
        int trueOutput2 = calculateSum(testInput2);
        int trueOutput3 = calculateSum(testInput3);

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

    public static int calculateFibonacci(int n) {
        // Base case: Fibonacci numbers at positions 0 and 1 are 0 and 1, respectively
        if (n == 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        }

        // Recursive case: sum of the previous two Fibonacci numbers
        return calculateFibonacci(n - 1) + calculateFibonacci(n - 2);
    }

    public HashMap<String, Object> checkCalculateFibonacci(Class<?> solutionClass) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        String targetMethodName = "calculateFibonacci";
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

        int testInput1 = random.nextInt(10);
        int testInput2 = random.nextInt(10) + 10;
        int testInput3 = random.nextInt(10) + 20;

        targetMethod.setAccessible(true);
        int userMethodOutput1 = (int) targetMethod.invoke(null, testInput1);
        int userMethodOutput2 = (int) targetMethod.invoke(null, testInput2);
        int userMethodOutput3 = (int) targetMethod.invoke(null, testInput3);

        int trueOutput1 = calculateFibonacci(testInput1);
        int trueOutput2 = calculateFibonacci(testInput2);
        int trueOutput3 = calculateFibonacci(testInput3);

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

    public static boolean isPalindrome(String str) {
        // Base case: an empty string or a string with one character is a palindrome
        if (str.length() <= 1) {
            return true;
        }

        // Recursive case: check if the first and last characters are equal,
        // and recursively check if the substring between them is a palindrome
        char firstChar = str.charAt(0);
        char lastChar = str.charAt(str.length() - 1);

        if (firstChar != lastChar) {
            return false;
        }

        String remainingSubstring = str.substring(1, str.length() - 1);
        return isPalindrome(remainingSubstring);
    }

    public HashMap<String, Object> checkIsPalindrome(Class<?> solutionClass) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        String targetMethodName = "isPalindrome";
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

        String testInput1 = "madam";
        String testInput2 = "level";
        String testInput3 = "java";

        targetMethod.setAccessible(true);
        boolean userMethodOutput1 = (boolean) targetMethod.invoke(null, testInput1);
        boolean userMethodOutput2 = (boolean) targetMethod.invoke(null, testInput2);
        boolean userMethodOutput3 = (boolean) targetMethod.invoke(null, testInput3);

        boolean trueOutput1 = isPalindrome(testInput1);
        boolean trueOutput2 = isPalindrome(testInput2);
        boolean trueOutput3 = isPalindrome(testInput3);

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

    public static double calculateExponentiation(double base, int exponent) {
        // Base case: any number raised to the power of 0 is 1
        if (exponent == 0) {
            return 1;
        }

        // Recursive case: multiply the base with the exponentiation of (base, exponent-1)
        return base * calculateExponentiation(base, exponent - 1);
    }

    public HashMap<String, Object> checkCalculateExponentiation(Class<?> solutionClass) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        String targetMethodName = "calculateExponentiation";
        HashMap<String, Object> result = new HashMap<>();
        ArrayList<HashMap<String, String>> hints = new ArrayList<>();

        Method targetMethod = null;
        Class<?>[] trueParameterTypes = {double.class, int.class};
        Class<?> trueReturnType = double.class;
        HashMap<String, Object> checkInputAndOutputResult = checkInputAndOutput(solutionClass, targetMethodName, trueParameterTypes, trueReturnType);
        if (!(boolean)checkInputAndOutputResult.get("correct")) {
            return checkInputAndOutputResult;
        }
        targetMethod = solutionClass.getDeclaredMethod(targetMethodName, trueParameterTypes);

        targetMethod.setAccessible(true);
        double userMethodOutput1 = (double) targetMethod.invoke(null, 3.5, 4);
        double userMethodOutput2 = (double) targetMethod.invoke(null, 2, 8);
        double userMethodOutput3 = (double) targetMethod.invoke(null, 4.1, 5);

        double trueOutput1 = calculateExponentiation(3.5, 4);
        double trueOutput2 = calculateExponentiation(2, 8);
        double trueOutput3 = calculateExponentiation(4.1, 5);

        boolean check1 = userMethodOutput1 == trueOutput1;
        boolean check2 = userMethodOutput2 == trueOutput2;
        boolean check3 = userMethodOutput3 == trueOutput3;
        if (!check1) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "(3.5, 4)的返回值应该是" + trueOutput1 + "，而不应该是" + userMethodOutput1);
            hints.add(hint);
        }
        if (!check2) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "(2, 8)的返回值应该是" + trueOutput2 + "，而不应该是" + userMethodOutput2);
            hints.add(hint);
        }
        if (!check3) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "(4.1, 5)的返回值应该是" + trueOutput3 + "，而不应该是" + userMethodOutput3);
            hints.add(hint);
        }
        result.put("correct", check1 && check2 && check3);
        result.put("hints", hints);
        return result;
    }

    public static String reverseString(String str) {
        // Base case: if the string is empty or has only one character, it is already reversed
        if (str.isEmpty() || str.length() == 1) {
            return str;
        }

        // Recursive case: reverse the substring starting from the second character and concatenate the first character
        return reverseString(str.substring(1)) + str.charAt(0);
    }

    public HashMap<String, Object> checkReverseString(Class<?> solutionClass) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        String targetMethodName = "reverseString";
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

        String testInput1 = "Java, World!";
        String testInput2 = "Learning Java is so fun!";
        String testInput3 = "Python is the best in the world (funny)!";

        targetMethod.setAccessible(true);
        String userMethodOutput1 = (String) targetMethod.invoke(null, testInput1);
        String userMethodOutput2 = (String) targetMethod.invoke(null, testInput2);
        String userMethodOutput3 = (String) targetMethod.invoke(null, testInput3);

        String trueOutput1 = reverseString(testInput1);
        String trueOutput2 = reverseString(testInput2);
        String trueOutput3 = reverseString(testInput3);

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

    public static int calculateGCD(int num1, int num2) {
        // Base case: if the second number is 0, the GCD is the first number
        if (num2 == 0) {
            return num1;
        }

        // Recursive case: calculate the GCD by recursively calling the method with num2 as the new num1 and the remainder as num2
        int remainder = num1 % num2;
        return calculateGCD(num2, remainder);
    }

    public HashMap<String, Object> checkCalculateGCD(Class<?> solutionClass) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        String targetMethodName = "calculateGCD";
        HashMap<String, Object> result = new HashMap<>();
        ArrayList<HashMap<String, String>> hints = new ArrayList<>();

        Method targetMethod = null;
        Class<?>[] trueParameterTypes = {int.class, int.class};
        Class<?> trueReturnType = int.class;
        HashMap<String, Object> checkInputAndOutputResult = checkInputAndOutput(solutionClass, targetMethodName, trueParameterTypes, trueReturnType);
        if (!(boolean)checkInputAndOutputResult.get("correct")) {
            return checkInputAndOutputResult;
        }
        targetMethod = solutionClass.getDeclaredMethod(targetMethodName, trueParameterTypes);

        int testInput11 = random.nextInt(1000);
        int testInput12 = random.nextInt(1000);
        int testInput21 = random.nextInt(1000);
        int testInput22 = random.nextInt(1000);
        int testInput31 = random.nextInt(1000);
        int testInput32 = random.nextInt(1000);

        targetMethod.setAccessible(true);
        int userMethodOutput1 = (int) targetMethod.invoke(null, testInput11, testInput12);
        int userMethodOutput2 = (int) targetMethod.invoke(null, testInput21, testInput22);
        int userMethodOutput3 = (int) targetMethod.invoke(null, testInput31, testInput32);

        int trueOutput1 = calculateGCD(testInput11, testInput12);
        int trueOutput2 = calculateGCD(testInput21, testInput22);
        int trueOutput3 = calculateGCD(testInput31, testInput32);

        boolean check1 = userMethodOutput1 == trueOutput1;
        boolean check2 = userMethodOutput2 == trueOutput2;
        boolean check3 = userMethodOutput3 == trueOutput3;
        if (!check1) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "("+ testInput11 + ", " + testInput12 + ")的返回值应该是" + trueOutput1 + "，而不应该是" + userMethodOutput1);
            hints.add(hint);
        }
        if (!check2) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "("+ testInput21 + ", " + testInput22 + ")的返回值应该是" + trueOutput2 + "，而不应该是" + userMethodOutput2);
            hints.add(hint);
        }
        if (!check3) {
            HashMap<String, String> hint = new HashMap<>();
            hint.put("chinese", targetMethodName + "("+ testInput31 + ", " + testInput32 + ")的返回值应该是" + trueOutput3 + "，而不应该是" + userMethodOutput3);
            hints.add(hint);
        }
        result.put("correct", check1 && check2 && check3);
        result.put("hints", hints);
        return result;
    }

    public static < T > int countOccurrences(T[] arr, T target) {
        return countOccurrences(arr, target, 0);
    }

    private static < T > int countOccurrences(T[] arr, T target, int index) {
        // Base case: if the index reaches the end of the array, return 0
        if (index == arr.length) {
            return 0;
        }

        // Recursive case: check if the element at the current index is equal to the target,
        // and recursively call the method with the next index and add 1 if it is equal
        int count = countOccurrences(arr, target, index + 1);
        if (arr[index].equals(target)) {
            count++;
        }

        return count;
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

    public static void javaProgramExerciseCheck(JavaProgramExercise exercise) throws Exception {
        String submissionCodePrefix = "package org.dream.solution;\n\npublic class Solution {";
        String submissionCodeSuffix = "}";
        String submissionCode = submissionCodePrefix +
                "\n" +
                "    public static int calculateOddNumberSum(int[] arr) {\n" +
                "        return calculateOddNumberSum(arr, 0);\n" +
                "    }\n" +
                "\n" +
                "    private static int calculateOddNumberSum(int[] arr, int index) {\n" +
                "        // Base case: if the index reaches the end of the array, return 0\n" +
                "        if (index == arr.length) {\n" +
                "            return 1;\n" +
                "        }\n" +
                "\n" +
                "        // Recursive case: check if the element at the current index is odd,\n" +
                "        // and recursively call the method with the next index and add the current element if it is odd\n" +
                "        int sum = calculateOddNumberSum(arr, index + 1);\n" +
                "        if (arr[index] % 2 != 0) {\n" +
                "            sum += arr[index];\n" +
                "        }\n" +
                "\n" +
                "        return sum;\n" +
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
        JavaProgramExercise exercise = javaProgramExercises.get(7);
        javaProgramExerciseCheck(exercise);
    }
}
