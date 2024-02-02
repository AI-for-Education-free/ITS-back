package com.dream.exerciseSystem.domain.exercise;

import java.util.HashMap;

public class ChoiceExerciseChecker {
    public static void singleChoiceExerciseCheck(SingleChoiceExercise exercise, int studentAnswer) {
        HashMap<String, Object> result = new HashMap<>();
        int correctAnswer = exercise.getCorrectAnswer();

        result.put("correct", correctAnswer == studentAnswer);

    }


    public static void main(String[] args) {

    }
}
