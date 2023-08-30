package com.dream.exerciseSystem.domain.exercise;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Data
public abstract class Exercise {
    public ExerciseType exerciseType;
    public int order;
    public String id;
    public SubjectType subject;
    public List<String> concepts;
    public final List<Description> exerciseDescriptionContents;
    public final List<Description> answerDescriptionContents;
    public final List<Description> explanationDescriptionContents;
    public List<String> tags;

    public Exercise() {
        this.exerciseType = null;
        this.order = -1;
        this.id = null;
        this.subject = null;
        this.concepts = null;
        this.exerciseDescriptionContents = null;
        this.answerDescriptionContents = null;
        this.explanationDescriptionContents = null;
        this.tags = null;
    }

    protected Exercise(ExerciseType exerciseType, int order, String id, SubjectType subject, List<String> concepts, List<Description> exerciseDescriptionContents,
                       List<Description> answerDescriptionContents, List<Description> explanationDescriptionContents,
                       List<String> tags) {
        this.exerciseType = exerciseType;
        this.order = order;
        this.id = id;
        this.subject = subject;
        this.concepts = concepts;
        this.exerciseDescriptionContents = exerciseDescriptionContents;
        this.answerDescriptionContents = answerDescriptionContents;
        this.explanationDescriptionContents = explanationDescriptionContents;
        this.tags = tags;
    }

}
