package com.dream.exerciseSystem.service;

import com.dream.exerciseSystem.utils.DataWrapper;

public interface ISingleChoiceExerciseService {
    DataWrapper getExerciseById(String id);
    DataWrapper getExerciseAll();
    DataWrapper getExerciseAllBasicInfo();
}
