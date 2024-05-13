package com.dream.exerciseSystem.domain;

import lombok.Data;

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

    private Long answerTimestamp;

    private boolean answerState;

    private String backInfo;

    private int hintNum;

    public StudentAnswerRecord(String studentId, String questionId, boolean answerState) {
        this.studentId = studentId;
        this.questionId = questionId;
        this.answerState = answerState;
    }
}
