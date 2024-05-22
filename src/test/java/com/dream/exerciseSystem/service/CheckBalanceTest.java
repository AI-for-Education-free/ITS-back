package com.dream.exerciseSystem.service;

import com.dream.exerciseSystem.utils.CheckBalance;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class CheckBalanceTest {

    @Resource
    private CheckBalance checkBalance;

    @Test
    void modifyBalanceTest(){
        String userId = "3447b4a703e0d929a5fb7690156767e5";
        int FAQBalanceToken = 500;
        boolean state = checkBalance.checkFAQBalanceToken(userId, FAQBalanceToken);
        Assertions.assertTrue(state);
    }
}
