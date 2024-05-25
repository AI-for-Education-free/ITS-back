package com.dream.exerciseSystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dream.exerciseSystem.domain.StudentAnswerRecord;
import com.dream.exerciseSystem.utils.DataWrapper;

public interface SingleChoiceExerciseService extends IService<StudentAnswerRecord> {
    DataWrapper getExerciseById(String id);
    DataWrapper getExerciseAll();
    DataWrapper getExerciseAllBasicInfo();

    DataWrapper checkSingleChoiceExercise(String exerciseId, String answer, String studentId);
}
