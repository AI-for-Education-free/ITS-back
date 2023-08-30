package com.dream.exerciseSystem.service.impl;

import com.dream.exerciseSystem.domain.exercise.ExerciseBasicInfo;
import com.dream.exerciseSystem.domain.exercise.SingleChoiceExercise;
import com.dream.exerciseSystem.service.ISingleChoiceExerciseService;
import com.dream.exerciseSystem.utils.DataWrapper;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SingleExerciseService implements ISingleChoiceExerciseService {
    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public DataWrapper getExerciseById(String id) {
        Map<String, SingleChoiceExercise> data = new HashMap<>();
        SingleChoiceExercise result = mongoTemplate.findById(id, SingleChoiceExercise.class);
        if (result == null) {
            return new DataWrapper(false).msgBuilder("请求单选习题失败").codeBuilder(100);
        } else {
            data.put("exercise", result);
            return new DataWrapper(true).msgBuilder("请求单选习题成功").dataBuilder(data);
        }
    }


    @Override
    public DataWrapper getExerciseAll() {
        Map<String, List<SingleChoiceExercise>> data = new HashMap<>();
        List<SingleChoiceExercise> results = mongoTemplate.findAll(SingleChoiceExercise.class);
        if (results.size() == 0) {
            return new DataWrapper(false).msgBuilder("请求单选习题失败").codeBuilder(100);
        } else {
            data.put("exercises", results);
            return new DataWrapper(true).msgBuilder("请求单选习题成功").dataBuilder(data);
        }
    }

    @Override
    public DataWrapper getExerciseAllBasicInfo() {
        Map<String, List<ExerciseBasicInfo>> data = new HashMap<>();
        List<SingleChoiceExercise> singleChoiceExerciseList = mongoTemplate.findAll(SingleChoiceExercise.class);
        List<ExerciseBasicInfo> exerciseBasicInfoList = ExerciseBasicInfo.singleChoiceExercise2exerciseBasicInfo(singleChoiceExerciseList);
        if (exerciseBasicInfoList.size() == 0) {
            return new DataWrapper(false).msgBuilder("请求Java单选习题基本信息失败").codeBuilder(100);
        } else {
            data.put("exerciseBasicInfoList", exerciseBasicInfoList);
            return new DataWrapper(true).msgBuilder("请求Java单选习题基本信息成功").dataBuilder(data);
        }
    }
}
