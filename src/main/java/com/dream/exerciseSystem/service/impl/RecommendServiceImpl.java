package com.dream.exerciseSystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dream.exerciseSystem.domain.StudentAnswerRecord;
import com.dream.exerciseSystem.mapper.StudentAnswerRecordMapper;
import com.dream.exerciseSystem.service.RecommendService;
import com.dream.exerciseSystem.utils.DataWrapper;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.QueryBuilders;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecommendServiceImpl extends ServiceImpl<StudentAnswerRecordMapper, StudentAnswerRecord> implements RecommendService {

    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;

    @Resource
    private StudentAnswerRecordMapper studentAnswerRecordMapper;

    @Override
    public DataWrapper defaultRecommend(String studentId) {
        // query the student exercise history from the db
        QueryWrapper<StudentAnswerRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", studentId).orderByDesc("answerTimestamp");
        List<StudentAnswerRecord> exerciseRecordList = studentAnswerRecordMapper.selectList(queryWrapper);

        // condition: if there exists less than five records, we recommend easy exercise;
        if(exerciseRecordList.size() < 5){
            List<StudentAnswerRecord> exerciseFalseRecordList = exerciseRecordList.stream()
                    .filter(record -> record.getAnswerCorrectness() == 0)
                    //.map(record -> record)
                    .collect(Collectors.toList());
            List<StudentAnswerRecord> exerciseCorrectRecordList = exerciseFalseRecordList.stream()
                    .filter((record -> record.getAnswerCorrectness() == 1))
                    .collect(Collectors.toList());
            if(exerciseFalseRecordList.isEmpty()){
                return null;
            }

        }

        return null;
    }
}
