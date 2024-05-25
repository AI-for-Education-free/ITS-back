package com.dream.exerciseSystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dream.exerciseSystem.domain.StudentAnswerRecord;
import com.dream.exerciseSystem.domain.exercise.ExerciseBasicInfo;
import com.dream.exerciseSystem.domain.exercise.SingleChoiceExercise;
import com.dream.exerciseSystem.mapper.StudentAnswerRecordMapper;
import com.dream.exerciseSystem.service.ISingleChoiceExerciseService;
import com.dream.exerciseSystem.utils.DataWrapper;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SingleExerciseService extends ServiceImpl<StudentAnswerRecordMapper, StudentAnswerRecord> implements ISingleChoiceExerciseService {
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

    @Override
    public DataWrapper checkSingleChoiceExercise(String exerciseId, String answer, String studentId) {
        // check if the answer is in the correct form
        if(answer.length() >= 2){
            return new DataWrapper(false).msgBuilder("the length of answer exceeds").dataBuilder(null);
        }
        else if(answer.isEmpty()){
            return new DataWrapper(false).msgBuilder("the content of answer is empty").dataBuilder(null);
        }

        // If answer check passwd. Query the question by the exerciseId
        SingleChoiceExercise singleChoiceExercise = mongoTemplate.findById(exerciseId, SingleChoiceExercise.class);
        if (singleChoiceExercise == null)
            return new DataWrapper(false).msgBuilder("检查single choice习题失败").codeBuilder(100);
        int correctAnswer = singleChoiceExercise.getCorrectAnswer();

        // Generate the studentAnswerRecord model
        StudentAnswerRecord studentAnswerRecord = new StudentAnswerRecord();
        long studentAnswerTimestamp = Instant.now().getEpochSecond();
        // id in StudentAnswerRecord is encrypted by MD5 algorithm
        String studentAnswerId = studentId+exerciseId+studentAnswerTimestamp;
        studentAnswerRecord.setId(studentAnswerId);
        studentAnswerRecord.setUserId(studentId);
        studentAnswerRecord.setQuestionId(exerciseId);
        studentAnswerRecord.setQuestionType(1); //single choice type:1
        // Convert the answer into the lower case
        char convertedAnswer = answer.toLowerCase().charAt(0);
        if(convertedAnswer-'a' != correctAnswer){
            studentAnswerRecord.setAnswerCorrectness(0);
        }
        else
            studentAnswerRecord.setAnswerCorrectness(1);
        studentAnswerRecord.setAnswerTimestamp(studentAnswerTimestamp);
        studentAnswerRecord.setAnswer(answer);
        boolean writeState = this.save(studentAnswerRecord);
        return new DataWrapper(true).msgBuilder("检查Java编程习题成功").dataBuilder(null);
    }
}
