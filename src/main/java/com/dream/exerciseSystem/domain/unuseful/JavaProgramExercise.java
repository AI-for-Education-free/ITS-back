package com.dream.exerciseSystem.domain.exercise;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class JavaProgramExercise extends Exercise {
    public String initCode;
    public String targetMethodName;
    public String checkPlace;

    public static void main(String[] args) {
        String s = "3. Which of the following are legal lines of Java code?";
        System.out.println(s.hashCode());
    }

    public JavaProgramExercise(ExerciseType exerciseType, int order, String id, SubjectType subject, List<String> concepts, List<String> tags,
                               List<Description> exerciseDescriptionContents, List<Description> answerDescriptionContents, List<Description> explanationDescriptionContents,
                               String initCode, String targetMethodName, String checkPlace) {
        super(exerciseType, order, id, subject, concepts, exerciseDescriptionContents, answerDescriptionContents, explanationDescriptionContents, tags);
        this.initCode = initCode;
        this.targetMethodName = targetMethodName;
        this.checkPlace = checkPlace;
    }

    public static List<Description> getDescriptionContentsFromList(List contentList) {
        List<Description> result = new ArrayList<>();

        for (Object content: contentList) {
            Description exerciseDescriptionContent;
            DescriptionType type;
            switch (((Map<String, String>) content).get("type")) {
                case "text":
                    type = DescriptionType.TEXT;
                    String english = ((Map<String, String>) content).get("english");
                    String chinese = ((Map<String, String>) content).get("chinese");
                    DescriptionStyle style;
                    String typeString = ((Map<String, String>) content).get("style");
                    if (typeString == null) {
                        style = DescriptionStyle.NORMAL;
                    } else {
                        switch (typeString) {
                            case "bold":
                                style = DescriptionStyle.BOLD;
                                break;
                            case "italic":
                                style = DescriptionStyle.ITALIC;
                                break;
                            case "boldItalic":
                                style = DescriptionStyle.BOLD_ITALIC;
                                break;
                            default:
                                style = DescriptionStyle.NORMAL;
                        }
                    }
                    exerciseDescriptionContent = new Description(type, english, chinese, style);
                    break;
                case "code":
                    type = DescriptionType.CODE;
                    String code = ((Map<String, String>) content).get("code");
                    exerciseDescriptionContent = new Description(type, code);
                    break;
                default:
                    type = DescriptionType.IMAGE;
                    String imgName = ((Map<String, String>) content).get("imgName");
                    exerciseDescriptionContent = new Description(type, imgName);
                    break;
            }
            result.add(exerciseDescriptionContent);
        }

        return result;
    }

    public static JavaProgramExercise parseFromJson(Map jsonData) {
        String exerciseTypeStr = (String) jsonData.get("exerciseType");
        int order = (int) jsonData.get("order");
        String id = (String) jsonData.get("id");
        List<String> concepts = (List) jsonData.get("concepts");
        List exercise = (List) jsonData.get("exercise");
        String initCode = (String) jsonData.get("initCode");
        String targetMethodName = (String) jsonData.get("targetMethodName");
        String checkPlace = (String) jsonData.get("checkPlace");
        String checkCode = (String) jsonData.get("checkCode");
        List answer = (List) jsonData.get("answer");
        List explanation = (List) jsonData.get("explanation");
        List<String> tags = (List) jsonData.get("tags");

        List<Description> exerciseDescriptionContents = getDescriptionContentsFromList(exercise);
        List<Description> answerDescriptionContents = getDescriptionContentsFromList(answer);
        List<Description> explanationDescriptionContents = getDescriptionContentsFromList(explanation);

        ExerciseType exerciseType;
        switch (exerciseTypeStr) {
            case "program":
                exerciseType = ExerciseType.PROGRAM;
                break;
            default:
                exerciseType = ExerciseType.SINGLE_CHOICE;
        }

        return new JavaProgramExercise(exerciseType, order, id, SubjectType.JAVA, concepts, tags, exerciseDescriptionContents, answerDescriptionContents,
                explanationDescriptionContents, initCode, targetMethodName, checkPlace);
    }
}
