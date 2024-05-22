package com.dream.exerciseSystem.domain.exercise;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lombok.ToString;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data @Getter @Setter
@ToString
@Document(collection = "javaSingleChoiceExercise")
public class SingleChoiceExercise {
    public String exerciseType = "SINGLE_CHOICE_EXERCISE";
    @MongoId
    public String id;
    // subjectType: JAVA
    public String subjectType;
    public Exercise exercise;
    public int correctAnswer;
    public List<List<Content>> options;
    public List<String> concepts;
    public List<String> tags;
    // 在同一个tag下的习题顺序
    public int orderInTag;

    public SingleChoiceExercise(String id, String subjectType, Exercise exercise, int correctAnswer,
                                List<List<Content>> options, List<String> concepts, List<String> tags, int orderInTag) {
        this.id = id;
        this.subjectType = subjectType;
        this.exercise = exercise;
        this.correctAnswer = correctAnswer;
        this.options = options;
        this.concepts = concepts;
        this.tags = tags;
        this.orderInTag = orderInTag;
    }

    public static SingleChoiceExercise generateExerciseFromJsonObject(JSONObject jsonObject, String subjectType) {
        int orderInTag = (int) jsonObject.get("orderInTag");
        String id = (String) jsonObject.get("id");
        int correctAnswer = (int) jsonObject.get("correctAnswer");

        JSONArray conceptArray = jsonObject.getJSONArray("concepts");
        JSONArray tagArray = jsonObject.getJSONArray("tags");
        List<String> concepts = Util.generateStringListFromJsonObject(conceptArray);
        List<String> tags = Util.generateStringListFromJsonObject(tagArray);

        JSONArray exerciseContentArray = jsonObject.getJSONArray("exerciseContents");
        JSONArray explanationContentArray = jsonObject.getJSONArray("explanationContents");
        List<Content> exerciseContents = Content.generateContentsFromJsonArray(exerciseContentArray);
        List<Content> explanationContents = Content.generateContentsFromJsonArray(explanationContentArray);

        JSONArray optionsArray = jsonObject.getJSONArray("options");
        List<List<Content>> optionsContents = new ArrayList<>();
        for (int i = 0; i < optionsArray.size(); i++) {
            JSONArray optionContentArray = optionsArray.getJSONArray(i);
            List<Content> optionContent = new ArrayList<>();
            for (int j = 0; j < optionContentArray.size(); j++) {
                JSONObject optionContentObject = optionContentArray.getJSONObject(j);
                optionContent.add(Content.generateContentFromJsonObject(optionContentObject));
            }
            optionsContents.add(optionContent);
        }

        // 对于单选题，没有answerContents，正确答案在correctAnswer
        Exercise exercise = new Exercise(exerciseContents, null, explanationContents);

        return new SingleChoiceExercise(id, subjectType, exercise, correctAnswer, optionsContents, concepts, tags,
                orderInTag);
    }

    public static void main(String[] args) throws IOException {
        String fPath = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "exercises", "math", "xes3g5m", "singleChoiceExercises.json").toString();
        File file = new File(fPath);
        String jsonString = Files.readString(Path.of(file.getAbsolutePath()));
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        String subjectType = (String) jsonObject.get("subjectType");
        JSONArray exerciseArray = jsonObject.getJSONArray("exercises");
        List<SingleChoiceExercise> singleChoiceExercises = new ArrayList<>();
        for (int i = 0; i < exerciseArray.size(); i++) {
            JSONObject exerciseObject = exerciseArray.getJSONObject(i);
            singleChoiceExercises.add(generateExerciseFromJsonObject(exerciseObject, subjectType));
        }
    }
}
