package com.dream.exerciseSystem.domain.exercise;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Setter
@Getter
public class Exercise {
    // 习题的内容，即习题文本、图片等
    public List<Content> exerciseContents;
    // 答案的内容，即答案文本、图片等
    public List<Content> answerContents;
    // 解释的内容，即解释文本、图片等
    public List<Content> explanationContents;

    public Exercise() {
        this.exerciseContents = null;
        this.answerContents = null;
        this.explanationContents = null;
    }

    // 目前的习题有全部信息
    public Exercise(List<Content> exerciseContents, List<Content> answerContents, List<Content> explanationContents) {
        this.exerciseContents = exerciseContents;
        this.answerContents = answerContents;
        this.explanationContents = explanationContents;
    }

    public Exercise(List<Content> exerciseContents, List<Content> explanationContents) {
        this.exerciseContents = exerciseContents;
        this.explanationContents = explanationContents;
        this.answerContents = null;
    }
}
