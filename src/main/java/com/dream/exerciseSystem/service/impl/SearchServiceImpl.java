package com.dream.exerciseSystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dream.exerciseSystem.domain.Student;
import com.dream.exerciseSystem.domain.StudentAnswerRecord;
import com.dream.exerciseSystem.domain.exercise.Exercise;
import com.dream.exerciseSystem.domain.exercise.FillInExercise;
import com.dream.exerciseSystem.domain.exercise.JavaProgramExercise;
import com.dream.exerciseSystem.domain.exercise.SingleChoiceExercise;
import com.dream.exerciseSystem.mapper.StudentAnswerRecordMapper;
import com.dream.exerciseSystem.mapper.StudentMapper;
import com.dream.exerciseSystem.service.SearchService;
import com.dream.exerciseSystem.utils.DataWrapper;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    @Resource
    private MongoTemplate mongoTemplate;

    @Resource
    private StudentMapper studentMapper;

    @Resource
    private StudentAnswerRecordMapper studentAnswerRecordMapper;

    @Override
    public DataWrapper searchByQuestionIdAndQuestionType(String questionId, String questionType) {
        Query query = new Query();
        query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("_id").is(questionId));

        if(questionType.equalsIgnoreCase("fillin")){
            if (mongoTemplate.exists(query, "fillInExercise")) {
                FillInExercise result = mongoTemplate.findById(questionId, FillInExercise.class);
                Exercise exercise = result.getExercise();
                return new DataWrapper(true).msgBuilder("搜索到相应填空题").dataBuilder(exercise);
            }
            else
                return new DataWrapper(false).msgBuilder("搜索不到相应填空题，检查id是否有问题").dataBuilder(null);
        }

        if(questionType.equalsIgnoreCase("singlechoice")){
            if (mongoTemplate.exists(query, "javaSingleChoiceExercise")) {
                SingleChoiceExercise result = mongoTemplate.findById(questionId, SingleChoiceExercise.class);
                Exercise exercise = result.getExercise();
                return new DataWrapper(true).msgBuilder("搜索到相应单选题").dataBuilder(exercise);
            }
            else
                return new DataWrapper(false).msgBuilder("搜索不到相应单选题，检查id是否有问题").dataBuilder(null);
        }

        if(questionType.equalsIgnoreCase("javaprogram")){
            if (mongoTemplate.exists(query, "javaProgramExercise")) {
                JavaProgramExercise result = mongoTemplate.findById(questionId, JavaProgramExercise.class);
                Exercise exercise = result.getExercise();
                return new DataWrapper(true).msgBuilder("搜索到相应编程题").dataBuilder(exercise);
            }
            else
                return new DataWrapper(false).msgBuilder("搜索不到相应编程题，检查id是否有问题").dataBuilder(null);
        }

        return new DataWrapper(false).msgBuilder("输入题目类型错误，请输入FillIn，SingleChoice，JavaProgram其中之一的字符串").dataBuilder(null);
    }

    @Override
    public DataWrapper searchUserInfoByUserId(String userId) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", userId);
        int num = studentMapper.selectCount(queryWrapper);
        if(num == 0)
            return new DataWrapper(false).msgBuilder("未找到对应用户，请检查用户id是否正确").dataBuilder(null);
        Student studentInfo = studentMapper.selectOne(queryWrapper);
        return new DataWrapper(true).msgBuilder("找到对应用户").dataBuilder(studentInfo);
    }

    @Override
    public DataWrapper searchUserAnswerRecordByUserId(String userId, int lastNum) {
        if(lastNum <= 0)
            return new DataWrapper(false).msgBuilder("输出的做题记录数量不合法，输出的做题记录数量应大于0").dataBuilder(null);
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", userId);
        int num = studentMapper.selectCount(queryWrapper);
        if(num == 0)
            return new DataWrapper(false).msgBuilder("未找到对应用户，请检查用户id是否正确").dataBuilder(null);

        QueryWrapper<StudentAnswerRecord> answerRecordQueryWrapper = new QueryWrapper<>();
        answerRecordQueryWrapper.eq("userId", userId);
        int answerRecordNum = studentAnswerRecordMapper.selectCount(answerRecordQueryWrapper);

        // if answerRecordNum is less than the num we required, we only give the answerRecordNum of records
        if(answerRecordNum == 0)
            return new DataWrapper(true).msgBuilder("用户没有做题记录").dataBuilder(null);
        if(answerRecordNum < lastNum){
            List<StudentAnswerRecord> studentAnswerRecordList = studentAnswerRecordMapper.selectList(answerRecordQueryWrapper);
            studentAnswerRecordList = studentAnswerRecordList.subList(0,answerRecordNum);
            return new DataWrapper(true).msgBuilder("用户做题记录数小于要求输出的数量，输出用户所有做题记录").dataBuilder(studentAnswerRecordList);
        }
        List<StudentAnswerRecord> studentAnswerRecordList = studentAnswerRecordMapper.selectList(answerRecordQueryWrapper);
        studentAnswerRecordList = studentAnswerRecordList.subList(0,lastNum);
        return new DataWrapper(true).msgBuilder("用户做题记录数大于等于要求输出的数量，输出符合要求数量的做题记录").dataBuilder(studentAnswerRecordList);
    }
}
