package com.dream.exerciseSystem.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dream.exerciseSystem.domain.UserBalanceRecord;
import com.dream.exerciseSystem.mapper.UserBalanceRecordMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class CheckBalance {

    @Resource
    private UserBalanceRecordMapper userBalanceRecordMapper;

    public boolean checkFAQBalanceToken(String userId, int FAQTokenBalance){
        QueryWrapper<UserBalanceRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId",userId);
        UserBalanceRecord userBalanceRecord = userBalanceRecordMapper.selectOne(queryWrapper);
        int userFAQTokenBalance = userBalanceRecord.getFAQTokenBalance();
        if(FAQTokenBalance > userFAQTokenBalance){
            return false;
        }
        else{
            userBalanceRecord.setFAQTokenBalance(userFAQTokenBalance-FAQTokenBalance);
            userBalanceRecordMapper.updateById(userBalanceRecord);
            return true;
        }
    }

//    public boolean checkRecommendBalance(String userId, int recommendTokenBalance){
//
//    }
}
