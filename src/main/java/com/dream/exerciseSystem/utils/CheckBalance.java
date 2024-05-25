package com.dream.exerciseSystem.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dream.exerciseSystem.domain.UserBalanceRecord;
import com.dream.exerciseSystem.mapper.UserBalanceRecordMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.xml.crypto.Data;

@Component
public class CheckBalance {

    @Resource
    private UserBalanceRecordMapper userBalanceRecordMapper;

    public DataWrapper checkFAQBalanceToken(String userId, int FAQTokenBalance){
        QueryWrapper<UserBalanceRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId",userId);
        UserBalanceRecord userBalanceRecord = userBalanceRecordMapper.selectOne(queryWrapper);
        int userFAQTokenBalance = userBalanceRecord.getFAQTokenBalance();
        if(FAQTokenBalance > userFAQTokenBalance){
            return new DataWrapper(false).msgBuilder("token balance is not suffienct").dataBuilder(null);
        }
        else{
            userBalanceRecord.setFAQTokenBalance(userFAQTokenBalance-FAQTokenBalance);
            userBalanceRecordMapper.updateById(userBalanceRecord);
            String msg = "token balance deducted success, token balance is"+(userFAQTokenBalance-FAQTokenBalance);
            return new DataWrapper(true).msgBuilder(msg).dataBuilder(null);
        }
    }

    public DataWrapper checkRecommendBalance(String userId, int recommendTokenBalance){
        QueryWrapper<UserBalanceRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId",userId);
        UserBalanceRecord userBalanceRecord = userBalanceRecordMapper.selectOne(queryWrapper);

        int recommendFreeUses = userBalanceRecord.getRecommendFreeUses();
        if(recommendFreeUses >= 1){
            userBalanceRecord.setRecommendFreeUses(recommendFreeUses-1);
            userBalanceRecordMapper.updateById(userBalanceRecord);
            if(recommendFreeUses == 1){
                return new DataWrapper(true).msgBuilder("All free uses have been exhausted").dataBuilder(null);
            }
        }
        return null;
    }
}
