package com.dream.exerciseSystem.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dream.exerciseSystem.domain.StudentAnswerRecord;
import com.dream.exerciseSystem.service.StudentAnswerRecordService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class StudentAnswerRecordTest {

    @Resource
    private StudentAnswerRecordService studentAnswerRecordService;
    @Test
    void addAnswerRecord(){
        String id = "xzy1";
        String userId = "xzy";
        String questionId = "dusdhq";
        int answerCorrectness = 0;
        long answerTimestamp = 1234565;

        StudentAnswerRecord studentAnswerRecord = new StudentAnswerRecord();
        studentAnswerRecord.setId(id);
        studentAnswerRecord.setUserId(userId);
        studentAnswerRecord.setQuestionId(questionId);
        studentAnswerRecord.setAnswerCorrectness(answerCorrectness);
        studentAnswerRecord.setAnswerTimestamp(answerTimestamp);

        studentAnswerRecordService.save(studentAnswerRecord);


    }

}
