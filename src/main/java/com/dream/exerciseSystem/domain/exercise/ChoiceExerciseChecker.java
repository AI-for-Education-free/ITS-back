package com.dream.exerciseSystem.domain.exercise;

import java.util.HashMap;

public class ChoiceExerciseChecker {
    public static HashMap<String, Object> checkSingleChoiceExercise(SingleChoiceExercise exercise, int studentAnswer) {
        HashMap<String, Object> result = new HashMap<>();
        int correctAnswer = exercise.getCorrectAnswer();

        result.put("correct", correctAnswer == studentAnswer);

        return result;
    }


    public static void main(String[] args) {

    }
}
