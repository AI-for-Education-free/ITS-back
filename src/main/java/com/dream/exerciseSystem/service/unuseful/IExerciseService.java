package com.dream.exerciseSystem.service.unuseful;

import com.dream.exerciseSystem.domain.exercise.ExerciseType;
import com.dream.exerciseSystem.utils.DataWrapper;

public interface IExerciseService {
    DataWrapper getAllExercisesByType(ExerciseType exerciseType);
    DataWrapper getExerciseById(String id);
    DataWrapper getExerciseResult(String exerciseId, String submissionCode) throws Exception;
}
