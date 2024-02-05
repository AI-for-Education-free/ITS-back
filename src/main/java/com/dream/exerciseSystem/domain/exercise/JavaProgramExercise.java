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
        String s = "Write a Java method that checks whether all the characters in a given string are vowels (a, e,i,o,u) or not. Return true if each character in the string is a vowel, otherwise return false.";
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

    public static HashMap<String, Object> check(String submissionCode, String targetMethodName, String developer)
            throws Exception {
        HashMap<String, Object> checkResult = new HashMap<>();
        HashMap<String, Object> findSolutionClassResult = getSolutionClass(submissionCode);
        Class<?> solutionClass = (Class<?>) findSolutionClassResult.get("solutionClass");

        if (solutionClass == null) {
            checkResult.put("correct", false);
            checkResult.put("hint", "compileException");
            checkResult.put("compileException", findSolutionClassResult.get("error"));
            return checkResult;
        }

        Method checkMethod = getCheckMethod(targetMethodName, developer);
        checkMethod.setAccessible(true);

        try {
            if (Objects.equals(developer, "XCL")) {
                checkResult = (HashMap<String, Object>) checkMethod.invoke(new JavaProgramExerciseCheckerXCL(), solutionClass);
            } else {
                checkResult = (HashMap<String, Object>) checkMethod.invoke(new JavaProgramExerciseChecker(), solutionClass);
            }
        } catch (Exception e) {
            assert e instanceof InvocationTargetException;
            InvocationTargetException targetEx = (InvocationTargetException) e;
            checkResult.put("correct", false);
            checkResult.put("hint", "runtimeException");
            checkResult.put("runtimeException", targetEx);
            return checkResult;
        }

        return checkResult;
    }
}
