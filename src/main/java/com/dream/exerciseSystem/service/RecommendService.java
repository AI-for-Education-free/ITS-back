package com.dream.exerciseSystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dream.exerciseSystem.domain.StudentAnswerRecord;
import com.dream.exerciseSystem.utils.DataWrapper;

public interface RecommendService extends IService<StudentAnswerRecord> {

    DataWrapper defaultRecommend(String studentId);
}
