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
    public String exerciseType;
    @MongoId
    public String exerciseId;
    public HashMap<String, String> exerciseString;
    public List<String> concepts;
    public List<String> tags;

    public ExerciseBasicInfo(String exerciseType, String exerciseId, HashMap<String, String> exerciseString,
                             List<String> concepts, List<String> tags) {
        this.exerciseType = exerciseType;
        this.exerciseId = exerciseId;
        this.exerciseString = exerciseString;
        this.concepts = concepts;
        this.tags = tags;
    }

    public static List<ExerciseBasicInfo> javaProgramExercise2exerciseBasicInfo(List<JavaProgramExercise> javaProgramExerciseList) {
        List<ExerciseBasicInfo> basicInfoList = new ArrayList<>();
        for (JavaProgramExercise javaProgramExercise: javaProgramExerciseList) {
            Content firstContent = javaProgramExercise.exercise.exerciseContents.get(0);
            HashMap<String, String> exerciseString = new HashMap<>();
            exerciseString.put("chinese", firstContent.getChinese());
            exerciseString.put("english", firstContent.getEnglish());
            ExerciseBasicInfo exerciseBasicInfo = new ExerciseBasicInfo(javaProgramExercise.getExerciseType(),
                    javaProgramExercise.getId(), exerciseString, javaProgramExercise.concepts, javaProgramExercise.tags);
            basicInfoList.add(exerciseBasicInfo);
        }
        return basicInfoList;
    }

    public static List<ExerciseBasicInfo> singleChoiceExercise2exerciseBasicInfo(List<SingleChoiceExercise> singleChoiceExerciseList) {
        List<ExerciseBasicInfo> basicInfoList = new ArrayList<>();
        for (SingleChoiceExercise singleChoiceExercise: singleChoiceExerciseList) {
            Content firstContent = singleChoiceExercise.exercise.exerciseContents.get(0);
            HashMap<String, String> exerciseString = new HashMap<>();
            exerciseString.put("chinese", firstContent.getChinese());
            exerciseString.put("english", firstContent.getEnglish());
            ExerciseBasicInfo exerciseBasicInfo = new ExerciseBasicInfo(singleChoiceExercise.getExerciseType(),
                    singleChoiceExercise.getId(), exerciseString, singleChoiceExercise.concepts, singleChoiceExercise.tags);
            basicInfoList.add(exerciseBasicInfo);
        }
        return basicInfoList;
    }
}
