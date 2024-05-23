package com.dream.exerciseSystem.domain.exercise;


import lombok.Data;
import lombok.ToString;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
@ToString
@Document(collection = "javaFillinExercise")
public class FillinExercise {
    public String exerciseType = "FILLIN_EXERCISE";
    @MongoId
    public String id;
    // subjectType: JAVA
    public String subjectType;
    public Exercise exercise;
    public HashMap<String, String> correctAnswer;
    public List<String> concepts;
    public List<String> tags;
    // 在同一个tag下的习题顺序
    public int orderInTag;

    public FillinExercise(String id, String subjectType, Exercise exercise, HashMap<String, String> correctAnswer, List<String> concepts, List<String> tags, int orderInTag) {
        this.id = id;
        this.subjectType = subjectType;
        this.exercise = exercise;
        this.correctAnswer = correctAnswer;
        this.concepts = concepts;
        this.tags = tags;
        this.orderInTag = orderInTag;
    }

    public static FillinExercise generateFillinExerciseFromJsonObject(JSONObject jsonObject, String subjectType) {
        int orderInTag = (int) jsonObject.get("orderInTag");
        String id = (String) jsonObject.get("id");
        //HashMap<Integer, String> correctAnswer = (HashMap<Integer, String>) jsonObject.get("correctAnswer");

        JSONObject correctAnswerJSONObject = jsonObject.getJSONObject("correctAnswer");
        JSONArray conceptArray = jsonObject.getJSONArray("concepts");
        JSONArray tagArray = jsonObject.getJSONArray("tags");

        HashMap<String, String> correctAnswer = Util.generateHashMapFromJsonObject(correctAnswerJSONObject);
        List<String> concepts = Util.generateStringListFromJsonObject(conceptArray);
        List<String> tags = Util.generateStringListFromJsonObject(tagArray);

        JSONArray exerciseContentArray = jsonObject.getJSONArray("exerciseContents");
        JSONArray explanationContentArray = jsonObject.getJSONArray("explanationContents");
        List<Content> exerciseContents = Content.generateContentsFromJsonArray(exerciseContentArray);
        List<Content> explanationContents = Content.generateContentsFromJsonArray(explanationContentArray);

        Exercise exercise = new Exercise(exerciseContents, null, explanationContents);

        return new FillinExercise(id, subjectType, exercise, correctAnswer, concepts, tags,
                orderInTag);
    }

}
