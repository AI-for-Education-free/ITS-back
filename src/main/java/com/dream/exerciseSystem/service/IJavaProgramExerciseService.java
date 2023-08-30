package com.dream.exerciseSystem.service;

import com.dream.exerciseSystem.utils.DataWrapper;

public interface IJavaProgramExerciseService {
    DataWrapper getExerciseById(String id);
    DataWrapper getExerciseAll();
    DataWrapper getExerciseAllBasicInfo();
    DataWrapper checkExercise(String id, String submissionCode) throws Exception;
}
