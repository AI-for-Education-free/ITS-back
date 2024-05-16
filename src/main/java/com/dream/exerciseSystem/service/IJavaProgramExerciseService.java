package com.dream.exerciseSystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dream.exerciseSystem.domain.StudentAnswerRecord;
import com.dream.exerciseSystem.utils.DataWrapper;

public interface IJavaProgramExerciseService extends IService<StudentAnswerRecord> {
    DataWrapper getExerciseById(String id);
    DataWrapper getExerciseAll();
    DataWrapper getExerciseAllBasicInfo();
    DataWrapper checkExercise(String id, String submissionCode) throws Exception;

    DataWrapper checkExercise(String id, String submissionCode, String studentId) throws Exception;

}
