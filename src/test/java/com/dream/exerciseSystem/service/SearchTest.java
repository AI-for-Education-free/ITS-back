package com.dream.exerciseSystem.service;

import com.dream.exerciseSystem.utils.DataWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class SearchTest {

    @Resource
    private SearchService searchService;

    @Test
    void searchByQuestionIdAndQuestionType(){
        String questionType;
        String questionId;
        // test for wrong type
        questionType = "MATH";
        questionId = "1607982877";
        DataWrapper dataWrapper = searchService.searchByQuestionIdAndQuestionType(questionId, questionType);
        System.out.println(dataWrapper.getMsg());
        // test for wrong id
        questionType = "javaprogram";
        questionId = "1607982878";
        DataWrapper dataWrapper1 = searchService.searchByQuestionIdAndQuestionType(questionId, questionType);
        System.out.println(dataWrapper1.getMsg());
        // test for correct id and type
        questionType = "javaprogram";
        questionId = "1607982877";
        DataWrapper dataWrapper2 = searchService.searchByQuestionIdAndQuestionType(questionId, questionType);
        System.out.println(dataWrapper2.getMsg());
    }

    @Test
    void getUserInfo(){
        String userId;

        // Test wrong id
        userId = "0541a7017f6bfed8125fc4453d4a9db1";
        DataWrapper dataWrapper = searchService.searchUserInfoByUserId(userId);
        System.out.println(dataWrapper.getMsg());

        // Correct id
        userId = "0541a7017f6bfed8125fc4453d4a9dbf";
        DataWrapper dataWrapper1 = searchService.searchUserInfoByUserId(userId);
        System.out.println(dataWrapper1.getMsg());
    }

    @Test
    void getUserAnswerRecord(){
        String userId;
        int lastNum;

        // Test wrong id
        userId = "0541a7017f6bfed8125fc4453d4a9db1";
        lastNum = 5;
        DataWrapper dataWrapper = searchService.searchUserAnswerRecordByUserId(userId, lastNum);
        System.out.println(dataWrapper.getMsg());
        // Test correct id with wrong num
        userId = "09599008e2d74e89f644d37ad5958ebd";
        lastNum = 0;
        DataWrapper dataWrapper1 = searchService.searchUserAnswerRecordByUserId(userId, lastNum);
        System.out.println(dataWrapper1.getMsg());
        // Test correct id with small num
        lastNum = 200;
        DataWrapper dataWrapper2 = searchService.searchUserAnswerRecordByUserId(userId, lastNum);
        System.out.println(dataWrapper2.getMsg());
        // Test correct id with huge num
        lastNum = 50;
        DataWrapper dataWrapper4 = searchService.searchUserAnswerRecordByUserId(userId, lastNum);
        System.out.println(dataWrapper4.getMsg());
    }
}
