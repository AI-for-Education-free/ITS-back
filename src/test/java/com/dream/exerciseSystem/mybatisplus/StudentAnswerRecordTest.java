package com.dream.exerciseSystem.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dream.exerciseSystem.domain.StudentAnswerRecord;
import com.dream.exerciseSystem.mapper.StudentAnswerRecordMapper;
import com.dream.exerciseSystem.service.StudentAnswerRecordService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class StudentAnswerRecordTest {

    @Resource
    private StudentAnswerRecordService studentAnswerRecordService;

    @Resource
    private StudentAnswerRecordMapper studentAnswerRecordMapper;

    @Test
    void addAnswerRecord(){
        String id = "xzy29";
        String userId = "xzy9";
        String questionId = "dusdhq9";
        int answerCorrectness = 0;
        long answerTimestamp = 12345659;
        int questionType = 2;

        StudentAnswerRecord studentAnswerRecord = new StudentAnswerRecord();
        studentAnswerRecord.setQuestionType(questionType);
        studentAnswerRecord.setId(id);
        studentAnswerRecord.setUserId(userId);
        studentAnswerRecord.setQuestionId(questionId);
        studentAnswerRecord.setAnswerCorrectness(answerCorrectness);
        studentAnswerRecord.setAnswerTimestamp(answerTimestamp);

        //studentAnswerRecordService.save(studentAnswerRecord);


    }

    @Test
    void searchDataByOrder(){
        QueryWrapper<StudentAnswerRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", "48f4409046ea1d9dcfa81b5c76792261").orderByDesc("answerTimestamp");
        List<StudentAnswerRecord> exerciseRecordList = studentAnswerRecordMapper.selectList(queryWrapper);
        if(exerciseRecordList.size() < 5){
            List<StudentAnswerRecord> exerciseFalseRecordList = exerciseRecordList.stream()
                    .filter(record -> record.getAnswerCorrectness() == 0)
                    //.map(record -> record)
                    .collect(Collectors.toList());
            if(!exerciseFalseRecordList.isEmpty()){
                for(StudentAnswerRecord i: exerciseFalseRecordList){
                    System.out.println(i.getAnswerTimestamp());
                }
            }
        }

    }

}
