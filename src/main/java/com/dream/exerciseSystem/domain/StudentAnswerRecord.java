package com.dream.exerciseSystem.domain;

import lombok.Data;

import java.security.Timestamp;

/**
 *
 * @Description Student info corresponding to answer record
 * @author xzy
 *
 */
@Data
public class StudentAnswerRecord<T> {
    // identity of student
    private String studentId;

    //identity of question
    private String questionId;

    //answer (including select and code answer)
    private T answer;

    private Timestamp answerTimestamp;

    private boolean answerState;

    private String backInfo;

    private int hintNum;

    public StudentAnswerRecord(String studentId, String questionId, T answer, Timestamp answerTimestamp, boolean answerState, String backInfo) {
        this.studentId = studentId;
        this.questionId = questionId;
        this.answer = answer;
        this.answerTimestamp = answerTimestamp;
        this.answerState = answerState;
        this.backInfo = backInfo;
    }

    public StudentAnswerRecord(String studentId, String questionId, T answer, Timestamp answerTimestamp, boolean answerState, String backInfo, int hintNum) {
        this.studentId = studentId;
        this.questionId = questionId;
        this.answer = answer;
        this.answerTimestamp = answerTimestamp;
        this.answerState = answerState;
        this.backInfo = backInfo;
        this.hintNum = hintNum;
    }
}
