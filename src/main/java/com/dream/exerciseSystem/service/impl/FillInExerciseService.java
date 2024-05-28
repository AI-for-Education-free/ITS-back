package com.dream.exerciseSystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dream.exerciseSystem.domain.Student;
import com.dream.exerciseSystem.domain.StudentAnswerRecord;
import com.dream.exerciseSystem.domain.exercise.ExerciseBasicInfo;
import com.dream.exerciseSystem.domain.exercise.FillInExercise;
import com.dream.exerciseSystem.domain.exercise.SingleChoiceExercise;
import com.dream.exerciseSystem.mapper.StudentAnswerRecordMapper;
import com.dream.exerciseSystem.service.IFillInExerciseService;
import com.dream.exerciseSystem.service.IJavaProgramExerciseService;
import com.dream.exerciseSystem.utils.DataWrapper;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FillInExerciseService extends ServiceImpl<StudentAnswerRecordMapper, StudentAnswerRecord> implements IFillInExerciseService {

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public DataWrapper getExerciseById(String id) {
        Map<String, FillInExercise> data = new HashMap<>();
        FillInExercise result = mongoTemplate.findById(id, FillInExercise.class);
        if (result == null) {
            return new DataWrapper(false).msgBuilder("请求单选习题失败").codeBuilder(100);
        } else {
            data.put("exercise", result);
            return new DataWrapper(true).msgBuilder("请求单选习题成功").dataBuilder(data);
        }
    }

    @Override
    public DataWrapper getExerciseAll() {
        Map<String, List<FillInExercise>> data = new HashMap<>();
        List<FillInExercise> results = mongoTemplate.findAll(FillInExercise.class);
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
        List<FillInExercise> fillInExerciseList = mongoTemplate.findAll(FillInExercise.class);
        List<ExerciseBasicInfo> exerciseBasicInfoList = ExerciseBasicInfo.fillInExercise2exerciseBasicInfo(fillInExerciseList);
        if (exerciseBasicInfoList.size() == 0) {
            return new DataWrapper(false).msgBuilder("请求Java单选习题基本信息失败").codeBuilder(100);
        } else {
            data.put("exerciseBasicInfoList", exerciseBasicInfoList);
            return new DataWrapper(true).msgBuilder("请求Java单选习题基本信息成功").dataBuilder(data);
        }
    }

    @Override
    public DataWrapper checkFillInExercise(String exerciseId, HashMap<String, String> answer, String studentId) {
        FillInExercise fillInExercise = mongoTemplate.findById(exerciseId, FillInExercise.class);
        if(fillInExercise == null)
            return new DataWrapper(false).msgBuilder("检查填空习题失败").codeBuilder(100);
        HashMap<String, String> hashMap = fillInExercise.getCorrectAnswer();

        StudentAnswerRecord studentAnswerRecord = new StudentAnswerRecord();
        long studentAnswerTimestamp = Instant.now().getEpochSecond();
        // id in StudentAnswerRecord is encrypted by MD5 algorithm
        String studentAnswerId = studentId+exerciseId+studentAnswerTimestamp;
        studentAnswerRecord.setId(studentAnswerId);
        studentAnswerRecord.setUserId(studentId);
        studentAnswerRecord.setQuestionId(exerciseId);
        studentAnswerRecord.setQuestionType(2); //single choice type:1
        // Convert the answer into the lower case
        if(answer.equals(hashMap))
            studentAnswerRecord.setAnswerCorrectness(1);
        else
            studentAnswerRecord.setAnswerCorrectness(0);

        studentAnswerRecord.setAnswerTimestamp(studentAnswerTimestamp);
        studentAnswerRecord.setAnswer(answer.toString());   //hashmap convert to string
        boolean writeState = this.save(studentAnswerRecord);
        return new DataWrapper(true).msgBuilder("检查填空习题成功").dataBuilder(null);
    }
}
