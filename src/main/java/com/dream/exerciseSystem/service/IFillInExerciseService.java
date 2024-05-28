package com.dream.exerciseSystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dream.exerciseSystem.domain.StudentAnswerRecord;
import com.dream.exerciseSystem.utils.DataWrapper;

import java.util.HashMap;

public interface IFillInExerciseService extends IService<StudentAnswerRecord> {
    DataWrapper getExerciseById(String id);
    DataWrapper getExerciseAll();
    DataWrapper getExerciseAllBasicInfo();
    DataWrapper checkFillInExercise(String exerciseId, HashMap<String, String> answer, String studentId);
}
