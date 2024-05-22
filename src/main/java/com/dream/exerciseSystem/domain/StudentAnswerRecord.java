package com.dream.exerciseSystem.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 *
 * @TableName StudentAnswerRecord
 */
@TableName(value ="StudentAnswerRecord")
@Data
public class StudentAnswerRecord implements Serializable {
    /**
     * student only id
     */
    @TableId
    private String id;

    /**
     * student account id
     */
    private String userId;

    /**
     * question id
     */
    private String questionId;

    /**
     * answer is correct or not
     */
    private Integer answerCorrectness;

    /**
     * time of student commit answer
     */
    private Long answerTimestamp;

    /**
     * time consume of answering
     */
    private Integer answerTimeConsume;

    /**
     * times of hint
     */
    private Integer hintCount;

    /**
     * code error type
     */
    private String codeErrorType;

    /**
     * code error info
     */
    private String codeErrorInfo;

    private String answer;

    private int questionType;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public StudentAnswerRecord(String id, String userId, String questionId, Integer answerCorrectness, Long answerTimestamp, String answer, int questionType) {
        this.id = id;
        this.userId = userId;
        this.questionId = questionId;
        this.answerCorrectness = answerCorrectness;
        this.answerTimestamp = answerTimestamp;
        this.answer = answer;
        this.questionType = questionType;
    }

    public StudentAnswerRecord() {
    }

    public StudentAnswerRecord(String id, String userId, String questionId, Integer answerCorrectness, Long answerTimestamp, Integer answerTimeConsume, Integer hintCount, String codeErrorType, String codeErrorInfo, String answer, int questionType) {
        this.id = id;
        this.userId = userId;
        this.questionId = questionId;
        this.answerCorrectness = answerCorrectness;
        this.answerTimestamp = answerTimestamp;
        this.answerTimeConsume = answerTimeConsume;
        this.hintCount = hintCount;
        this.codeErrorType = codeErrorType;
        this.codeErrorInfo = codeErrorInfo;
        this.answer = answer;
        this.questionType = questionType;
    }

    public StudentAnswerRecord(String userId, String questionId, Long answerTimestamp, boolean correctness, int questionType) {
        this.id = userId + questionId + answerTimestamp;
        this.userId = userId;
        this.questionId = questionId;
        this.answerTimestamp = answerTimestamp;
        this.answerCorrectness = correctness ? 1 : 0;
        this.answerTimeConsume = 0;
        this.hintCount = 0;
        this.codeErrorType = "";
        this.codeErrorInfo = "";
        this.answer = "";
        this.questionType = questionType;
    }
}
