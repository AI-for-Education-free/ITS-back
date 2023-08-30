package com.dream.exerciseSystem.domain.exercise;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@Component
@ConfigurationProperties("exercise")
public class ExerciseInfo {
    private String subjectType;
    private String exerciseType;

    public List<String> getSubjectTypeAll() {
        String[] subjectTypeAllArray = this.subjectType.split("");
        List<String> subjectTypeAllList = new ArrayList<>(subjectTypeAllArray.length);
        Collections.addAll(subjectTypeAllList, subjectTypeAllArray);
        return subjectTypeAllList;
    }

    public List<String> getExerciseTypeAll() {
        String[] exerciseTypeAllArray = this.exerciseType.split("");
        List<String> exerciseTypeAllList = new ArrayList<>(exerciseTypeAllArray.length);
        Collections.addAll(exerciseTypeAllList, exerciseTypeAllArray);
        return exerciseTypeAllList;
    }

    public static void main(String[] args) {
        ExerciseInfo exerciseInfo = new ExerciseInfo();
        System.out.println(exerciseInfo.getExerciseTypeAll());
    }
}
