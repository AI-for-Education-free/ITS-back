package com.dream.exerciseSystem.domain.exercise;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
@Getter
@Setter
@ToString
@Document(collection = "javaProgramExercise")
public class ExerciseBasicInfo {
    public String subjectType;
    public String exerciseType;
    @MongoId
    public String exerciseId;
    public String exerciseContent;
    public List<String> concepts;
    public List<String> tags;

    public ExerciseBasicInfo(String subjectType, String exerciseType, String exerciseId, String exerciseContent,
                             List<String> concepts, List<String> tags) {
        this.subjectType = subjectType;
        this.exerciseType = exerciseType;
        this.exerciseId = exerciseId;
        this.exerciseContent = exerciseContent;
        this.concepts = concepts;
        this.tags = tags;
    }

    public static List<ExerciseBasicInfo> javaProgramExercise2exerciseBasicInfo(List<JavaProgramExercise> javaProgramExerciseList) {
        List<ExerciseBasicInfo> basicInfoList = new ArrayList<>();
        for (JavaProgramExercise javaProgramExercise: javaProgramExerciseList) {
            Content firstContent = javaProgramExercise.exercise.exerciseContents.get(0);
            String exerciseContent = firstContent.getChinese();
            if (exerciseContent.length() == 0) {
                exerciseContent = firstContent.getEnglish();
            }
            ExerciseBasicInfo exerciseBasicInfo = new ExerciseBasicInfo("JAVA", javaProgramExercise.getExerciseType(),
                    javaProgramExercise.getId(), exerciseContent, javaProgramExercise.concepts, javaProgramExercise.tags);
            basicInfoList.add(exerciseBasicInfo);
        }
        return basicInfoList;
    }

    public static List<ExerciseBasicInfo> singleChoiceExercise2exerciseBasicInfo(List<SingleChoiceExercise> singleChoiceExerciseList) {
        List<ExerciseBasicInfo> basicInfoList = new ArrayList<>();
        for (SingleChoiceExercise singleChoiceExercise: singleChoiceExerciseList) {
            Content firstContent = singleChoiceExercise.exercise.exerciseContents.get(0);
            String exerciseContent = firstContent.getChinese();
            if (exerciseContent.length() == 0) {
                exerciseContent = firstContent.getEnglish();
            }
            ExerciseBasicInfo exerciseBasicInfo = new ExerciseBasicInfo(
                    singleChoiceExercise.getSubjectType(), singleChoiceExercise.getExerciseType(),
                    singleChoiceExercise.getId(), exerciseContent, singleChoiceExercise.concepts, singleChoiceExercise.tags);
            basicInfoList.add(exerciseBasicInfo);
        }
        return basicInfoList;
    }

    public static List<ExerciseBasicInfo> fillInExercise2exerciseBasicInfo(List<FillInExercise> fillInExercisesList) {
        List<ExerciseBasicInfo> basicInfoList = new ArrayList<>();
        for (FillInExercise fillInExercise: fillInExercisesList) {
            Content firstContent = fillInExercise.exercise.exerciseContents.get(0);
            String exerciseContent = firstContent.getChinese();
            if (exerciseContent.length() == 0) {
                exerciseContent = firstContent.getEnglish();
            }
            ExerciseBasicInfo exerciseBasicInfo = new ExerciseBasicInfo(
                    fillInExercise.getSubjectType(), fillInExercise.getExerciseType(),
                    fillInExercise.getId(), exerciseContent, fillInExercise.concepts, fillInExercise.tags);
            basicInfoList.add(exerciseBasicInfo);
        }
        return basicInfoList;
    }
}
