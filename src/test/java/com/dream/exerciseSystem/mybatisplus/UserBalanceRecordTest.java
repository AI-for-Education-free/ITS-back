package com.dream.exerciseSystem.mybatisplus;

import com.dream.exerciseSystem.domain.UserBalanceRecord;
import com.dream.exerciseSystem.service.UserBalanceRecordService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class UserBalanceRecordTest {

    @Resource
    private UserBalanceRecordService userBalanceRecordService;

    @Test
    void insertTestData(){
        UserBalanceRecord userBalanceRecord = new UserBalanceRecord();
        userBalanceRecord.setUserId("1234556");
        userBalanceRecordService.save(userBalanceRecord);
    }
}
