package com.dream.exerciseSystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dream.exerciseSystem.constant.StudentInfoConstant;
import com.dream.exerciseSystem.domain.Student;
import com.dream.exerciseSystem.domain.StudentAnswerRecord;
import com.dream.exerciseSystem.domain.exercise.ExerciseBasicInfo;
import com.dream.exerciseSystem.domain.exercise.JavaProgramExercise;
import com.dream.exerciseSystem.mapper.StudentAnswerRecordMapper;
import com.dream.exerciseSystem.service.IJavaProgramExerciseService;
import com.dream.exerciseSystem.utils.DataWrapper;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JavaProgramExerciseService extends ServiceImpl<StudentAnswerRecordMapper, StudentAnswerRecord> implements IJavaProgramExerciseService {
    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public DataWrapper getExerciseById(String id) {
        Map<String, JavaProgramExercise> data = new HashMap<>();
        JavaProgramExercise result = mongoTemplate.findById(id, JavaProgramExercise.class);
        if (result == null) {
            return new DataWrapper(false).msgBuilder("请求Java编程习题失败").codeBuilder(100);
        } else {
            data.put("exercise", result);
            return new DataWrapper(true).msgBuilder("请求Java编程习题成功").dataBuilder(data);
        }
    }

    @Override
    public DataWrapper getExerciseAll() {
        Map<String, List<JavaProgramExercise>> data = new HashMap<>();
        List<JavaProgramExercise> results = mongoTemplate.findAll(JavaProgramExercise.class);
        if (results.size() == 0) {
            return new DataWrapper(false).msgBuilder("请求Java编程习题失败").codeBuilder(100);
        } else {
            data.put("exercises", results);
            return new DataWrapper(true).msgBuilder("请求Java编程习题成功").dataBuilder(data);
        }
    }

    @Override
    public DataWrapper checkExercise(String id, String submissionCode) throws Exception {
        Map<String, Map> data = new HashMap<>();
        JavaProgramExercise result = mongoTemplate.findById(id, JavaProgramExercise.class);
        if (result == null) {
            return new DataWrapper(false).msgBuilder("检查Java编程习题失败").codeBuilder(100);
        } else {
            String targetMethodName = result.targetMethodName;
            HashMap<String, Object> checkResult = JavaProgramExercise.check(submissionCode, targetMethodName, "XZJ");
            data.put("result", checkResult);
            return new DataWrapper(true).msgBuilder("检查Java编程习题成功").dataBuilder(data);
        }
    }

    @Override
    public DataWrapper checkExercise(String id, String submissionCode, String studentId) throws Exception {
        Map<String, Map> data = new HashMap<>();
        JavaProgramExercise result = mongoTemplate.findById(id, JavaProgramExercise.class);
        if (result == null) {
            return new DataWrapper(false).msgBuilder("检查Java编程习题失败").codeBuilder(100);
        } else {
            String targetMethodName = result.targetMethodName;
            HashMap<String, Object> checkResult = JavaProgramExercise.check(submissionCode, targetMethodName, "XZJ");
            data.put("result", checkResult);

            StudentAnswerRecord studentAnswerRecord = new StudentAnswerRecord();
            long studentAnswerTimestamp = Instant.now().getEpochSecond();
            // id in StudentAnswerRecord is encrypted by MD5 algorithm
            String studentAnswerId = studentId+id+studentAnswerTimestamp;
            studentAnswerRecord.setId(studentAnswerId);
            studentAnswerRecord.setUserId(studentId);
            studentAnswerRecord.setQuestionId(id);
            if(!(boolean)checkResult.get("correct")){
                studentAnswerRecord.setAnswerCorrectness(0);
            }
            else
                studentAnswerRecord.setAnswerCorrectness(1);
            studentAnswerRecord.setAnswerTimestamp(studentAnswerTimestamp);
            studentAnswerRecord.setAnswer(submissionCode);
            boolean writeState = this.save(studentAnswerRecord);
            return new DataWrapper(true).msgBuilder("检查Java编程习题成功").dataBuilder(data);
        }


    }


    @Override
    public DataWrapper getExerciseAllBasicInfo() {
        Map<String, List<ExerciseBasicInfo>> data = new HashMap<>();
        List<JavaProgramExercise> javaProgramExerciseList = mongoTemplate.findAll(JavaProgramExercise.class);
        List<ExerciseBasicInfo> exerciseBasicInfoList = ExerciseBasicInfo.javaProgramExercise2exerciseBasicInfo(javaProgramExerciseList);
        if (exerciseBasicInfoList.size() == 0) {
            return new DataWrapper(false).msgBuilder("请求Java编程习题基本信息失败").codeBuilder(100);
        } else {
            data.put("exerciseBasicInfoList", exerciseBasicInfoList);
            return new DataWrapper(true).msgBuilder("请求Java编程习题基本信息成功").dataBuilder(data);
        }
    }
}
