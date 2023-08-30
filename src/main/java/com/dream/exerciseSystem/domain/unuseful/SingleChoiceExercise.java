package com.dream.exerciseSystem.domain.exercise;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
@Data
@EqualsAndHashCode(callSuper=true)
public class SingleChoiceExercise extends Exercise{
    public Integer correctAnswer;
    public List<List<Description>> options;
    public SingleChoiceExercise(ExerciseType exerciseType, int order, String id, SubjectType subject, List<String> concepts, List<String> tags,
                                List<Description> exerciseDescriptionContents, List<Description> answerDescriptionContents, List<Description> explanationDescriptionContents,
                                Integer correctAnswer, List<List<Description>> options) {
        super(exerciseType, order, id, subject, concepts, exerciseDescriptionContents, answerDescriptionContents, explanationDescriptionContents, tags);
        this.correctAnswer = correctAnswer;
        this.options = options;
    }

    public static List<List<Description>> getOptions(List optionList) {
        List<List<Description>> result = new ArrayList<>();
        for (Object option: optionList) {
            result.add(JavaProgramExercise.getDescriptionContentsFromList((List) option));
        }
        return result;
    }

    public static SingleChoiceExercise parseFromJson(Map jsonData) {
        String exerciseTypeStr = (String) jsonData.get("exerciseType");
        int order = (int) jsonData.get("order");
        int answer = (int) jsonData.get("answer");
        String id = (String) jsonData.get("id");
        List<String> concepts = (List) jsonData.get("concepts");
        List exercise = (List) jsonData.get("exercise");
        List options = (List) jsonData.get("options");
        List explanation = (List) jsonData.get("explanation");
        List<String> tags = (List) jsonData.get("tags");

        List<Description> exerciseDescriptionContents = JavaProgramExercise.getDescriptionContentsFromList(exercise);
        List<List<Description>> optionList = SingleChoiceExercise.getOptions(options);
        List<Description> explanationDescriptionContents = JavaProgramExercise.getDescriptionContentsFromList(explanation);

        ExerciseType exerciseType;
        switch (exerciseTypeStr) {
            case "program":
                exerciseType = ExerciseType.PROGRAM;
                break;
            default:
                exerciseType = ExerciseType.SINGLE_CHOICE;
        }

        return new SingleChoiceExercise(exerciseType, order, id, SubjectType.JAVA, concepts, tags, exerciseDescriptionContents, null,
                explanationDescriptionContents, answer, optionList);
    }
}
