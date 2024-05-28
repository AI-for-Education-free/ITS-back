package com.dream.exerciseSystem.service;

import com.dream.exerciseSystem.utils.DataWrapper;

/**
 *
 * The interface is used for executing search action
 *
 */
public interface SearchService {

    DataWrapper searchByQuestionIdAndQuestionType(String questionId, String questionType);

    DataWrapper searchUserInfoByUserId(String userId);

    DataWrapper searchUserAnswerRecordByUserId(String userId, int lastNum);

}
