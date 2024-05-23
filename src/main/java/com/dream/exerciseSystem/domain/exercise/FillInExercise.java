package com.dream.exerciseSystem.domain.exercise;


import lombok.Data;
import lombok.ToString;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

@Data
@ToString
@Document(collection = "fillInExercise")
public class FillInExercise {
    public String exerciseType = "FILL_IN_EXERCISE";
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

    public FillInExercise(String id, String subjectType, Exercise exercise, HashMap<String, String> correctAnswer, List<String> concepts, List<String> tags, int orderInTag) {
        this.id = id;
        this.subjectType = subjectType;
        this.exercise = exercise;
        this.correctAnswer = correctAnswer;
        this.concepts = concepts;
        this.tags = tags;
        this.orderInTag = orderInTag;
    }

    public static FillInExercise generateExerciseFromJsonObject(JSONObject jsonObject, String subjectType) {
        int orderInTag = (int) jsonObject.get("orderInTag");
        String id = (String) jsonObject.get("id");
        JSONArray conceptArray = jsonObject.getJSONArray("concepts");
        JSONArray tagArray = jsonObject.getJSONArray("tags");
        JSONArray correctAnswerArray = jsonObject.getJSONArray("correctAnswer");
        List<String> concepts = Util.generateStringListFromJsonObject(conceptArray);
        List<String> tags = Util.generateStringListFromJsonObject(tagArray);
        List<String> correctAnswerList = Util.generateStringListFromJsonObject(correctAnswerArray);
        HashMap<String, String> correctAnswer = new HashMap<>();
        IntStream.range(0, correctAnswerList.size())
                .forEach(index -> {
                    String element = correctAnswerList.get(index);
                    correctAnswer.put(index+"", element);
                });

        JSONArray exerciseContentArray = jsonObject.getJSONArray("exerciseContents");
        JSONArray explanationContentArray = jsonObject.getJSONArray("explanationContents");
        List<Content> exerciseContents = Content.generateContentsFromJsonArray(exerciseContentArray);
        List<Content> explanationContents = Content.generateContentsFromJsonArray(explanationContentArray);

        Exercise exercise = new Exercise(exerciseContents, null, explanationContents);

        return new FillInExercise(id, subjectType, exercise, correctAnswer, concepts, tags, orderInTag);
    }

    public static void main(String[] args) throws IOException {
        String fPath = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "exercises", "math", "xes3g5m", "fill_in_question.json").toString();
        File file = new File(fPath);
        String jsonString = Files.readString(Path.of(file.getAbsolutePath()));
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        String subjectType = (String) jsonObject.get("subjectType");
        JSONArray exerciseArray = jsonObject.getJSONArray("exercises");
        List<FillInExercise> fillInExercises = new ArrayList<>();
        for (int i = 0; i < exerciseArray.size(); i++) {
            JSONObject exerciseObject = exerciseArray.getJSONObject(i);
            fillInExercises.add(generateExerciseFromJsonObject(exerciseObject, subjectType));
        }
    }
}
