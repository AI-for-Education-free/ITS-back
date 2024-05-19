package com.dream.exerciseSystem.domain.exercise;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.mdkt.compiler.InMemoryJavaCompiler;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data @Getter @Setter
@ToString
@Document(collection = "javaProgramExercise")
public class JavaProgramExercise {
    public String exerciseType = "JAVA_PROGRAM_EXERCISE";
    @MongoId
    public String id;
    // subjectType目前可取值: JAVA
    public String subjectType;
    public Exercise exercise;
    public List<String> concepts;
    public List<String> tags;
    // 在同一个tag下的习题顺序
    public int orderInTag;
    // java编程习题特有的属性
    public String initCode;
    public String targetMethodName;

    public static void main(String[] args) {
        String s = "Write a Java program to take the user for a distance (in meters) and the time taken (as three numbers: hours, minutes, seconds), and display the speed, in meters per second, kilometers per hour and miles per hour (hint: 1 mile = 1609 meters).";
        System.out.println(s.hashCode());
    }

    public JavaProgramExercise() {
    }

    public JavaProgramExercise(String id, String subjectType, Exercise exercise, List<String> concepts,
                               List<String> tags, int orderInTag, String initCode, String targetMethodName) {
        this.id = id;
        this.subjectType = subjectType;
        this.exercise = exercise;
        this.concepts = concepts;
        this.tags = tags;
        this.orderInTag = orderInTag;
        this.initCode = initCode;
        this.targetMethodName = targetMethodName;
    }

    public static JavaProgramExercise generateExerciseFromJsonObject(JSONObject jsonObject, String subjectType) {
        int orderInTag = (int) jsonObject.get("orderInTag");
        String id = (String) jsonObject.get("id");
        String initCode = (String) jsonObject.get("initCode");
        String targetMethodName = (String) jsonObject.get("targetMethodName");

        JSONArray conceptArray = jsonObject.getJSONArray("concepts");
        JSONArray tagArray = jsonObject.getJSONArray("tags");
        List<String> concepts = Util.generateStringListFromJsonObject(conceptArray);
        List<String> tags = Util.generateStringListFromJsonObject(tagArray);

        JSONArray exerciseContentArray = jsonObject.getJSONArray("exerciseContents");
        JSONArray answerContentArray = jsonObject.getJSONArray("answerContents");
        JSONArray explanationContentArray = jsonObject.getJSONArray("explanationContents");
        List<Content> exerciseContents = Content.generateContentsFromJsonArray(exerciseContentArray);
        List<Content> answerContents = Content.generateContentsFromJsonArray(answerContentArray);
        List<Content> explanationContents = Content.generateContentsFromJsonArray(explanationContentArray);

        Exercise exercise = new Exercise(exerciseContents, answerContents, explanationContents);

        return new JavaProgramExercise(id, subjectType, exercise, concepts, tags, orderInTag, initCode, targetMethodName);
    }

    public static Method getCheckMethod(String targetMethodName, String developer) {
        String checkMethodName = "check" + targetMethodName.substring(0,1).toUpperCase() + targetMethodName.substring(1);
        Method[] checkMethods = null;
        if (Objects.equals(developer, "XCL")) {
            Class<JavaProgramExerciseCheckerXCL> javaProgramExerciseCheckerClass = JavaProgramExerciseCheckerXCL.class;
            checkMethods = javaProgramExerciseCheckerClass.getDeclaredMethods();
        } else if (Objects.equals(developer, "XZJ2")) {
            Class<JavaProgramExerciseCheckerXZJ2> javaProgramExerciseCheckerClass = JavaProgramExerciseCheckerXZJ2.class;
            checkMethods = javaProgramExerciseCheckerClass.getDeclaredMethods();
        } else {
            Class<JavaProgramExerciseChecker> javaProgramExerciseCheckerClass = JavaProgramExerciseChecker.class;
            checkMethods = javaProgramExerciseCheckerClass.getDeclaredMethods();
        }
        List<Method> filteredCheckMethods = Stream.of(checkMethods).filter(method -> method.getName().equals(checkMethodName)).collect(Collectors.toList());
        return filteredCheckMethods.get(0);
    }

    public static HashMap<String, Object> getSolutionClass(String submissionCode) {
        HashMap<String, Object> result = new HashMap<>();
        Class<?> solutionClass;

        try {
            solutionClass = InMemoryJavaCompiler.newInstance().compile("org.dream.solution.Solution", submissionCode);
            result.put("solutionClass", solutionClass);
        } catch (Exception e) {
            result.put("solutionClass", null);
            result.put("error", e);
        }

        return result;
    }

    public static JavaProgramExerciseCheckResult check(String submissionCode, String targetMethodName, String developer)
            throws Exception {
        JavaProgramExerciseCheckResult javaProgramExerciseCheckResult = new JavaProgramExerciseCheckResult();
        HashMap<String, Object> checkResult = new HashMap<>();
        HashMap<String, Object> findSolutionClassResult = getSolutionClass(submissionCode);
        Class<?> solutionClass = (Class<?>) findSolutionClassResult.get("solutionClass");

        if (solutionClass == null) {
            checkResult.put("correct", false);
            checkResult.put("hint", "compileException");
            checkResult.put("compileException", findSolutionClassResult.get("error"));

            javaProgramExerciseCheckResult.setAnswerState(false);
            javaProgramExerciseCheckResult.setCaseHints(null);
            javaProgramExerciseCheckResult.setCodeErrorType("compileException");
            javaProgramExerciseCheckResult.setCodeErrorInfo("not find solutionClass");

            return javaProgramExerciseCheckResult;
        }

        Method checkMethod = getCheckMethod(targetMethodName, developer);
        checkMethod.setAccessible(true);

        try {
            if (Objects.equals(developer, "XCL")) {
                checkResult = (HashMap<String, Object>) checkMethod.invoke(new JavaProgramExerciseCheckerXCL(), solutionClass);
            } else if (Objects.equals(developer, "XZJ2")) {
                checkResult = (HashMap<String, Object>) checkMethod.invoke(new JavaProgramExerciseCheckerXZJ2(), solutionClass);
            } else {
                checkResult = (HashMap<String, Object>) checkMethod.invoke(new JavaProgramExerciseChecker(), solutionClass);
            }
        } catch (Exception e) {
            assert e instanceof InvocationTargetException;
            InvocationTargetException targetEx = (InvocationTargetException) e;
            javaProgramExerciseCheckResult.setAnswerState(false);
            javaProgramExerciseCheckResult.setCaseHints(null);
            javaProgramExerciseCheckResult.setCodeErrorType("runtimeException");
            javaProgramExerciseCheckResult.setCodeErrorInfo(targetEx.toString());

            return javaProgramExerciseCheckResult;
        }

        boolean answerState = (boolean) checkResult.get("correct");
        javaProgramExerciseCheckResult.setAnswerState(answerState);
        List<HashMap<String, String>> caseHints = (List<HashMap<String, String>>) checkResult.get("hints");
        if (answerState) {
            javaProgramExerciseCheckResult.setCaseHints(null);
            javaProgramExerciseCheckResult.setCodeErrorType("");
            javaProgramExerciseCheckResult.setCodeErrorInfo("");
        } else {
            javaProgramExerciseCheckResult.setCodeErrorType("case not pass");
            javaProgramExerciseCheckResult.setCodeErrorInfo("");
            javaProgramExerciseCheckResult.setCaseHints(caseHints);
        }

        return javaProgramExerciseCheckResult;
    }
}
