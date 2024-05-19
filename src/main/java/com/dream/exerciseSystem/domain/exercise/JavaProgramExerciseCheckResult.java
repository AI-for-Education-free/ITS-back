package com.dream.exerciseSystem.domain.exercise;

import lombok.Data;

import java.util.HashMap;
import java.util.List;


@Data
public class JavaProgramExerciseCheckResult {
    private boolean answerState;
    private List<HashMap<String, String>> caseHints;
    private String codeErrorType;
    private String codeErrorInfo;
}
