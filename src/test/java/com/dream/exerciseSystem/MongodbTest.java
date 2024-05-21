package com.dream.exerciseSystem;

import com.dream.exerciseSystem.domain.Student;
import com.dream.exerciseSystem.domain.exercise.JavaProgramExercise;
import com.dream.exerciseSystem.domain.exercise.SingleChoiceExercise;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest
public class MongodbTest {
    @Resource
    private MongoTemplate mongoTemplate;

    @Test
    void javaSingleChoiceExercise2database() throws IOException {
        // 对应数据库里的singleChoiceExercise集合
        String fPath = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "exercises", "program", "java", "singleChoiceExercises.json").toString();
        File file = new File(fPath);
        String jsonString = Files.readString(Path.of(file.getAbsolutePath()));
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        JSONArray exercises = jsonObject.getJSONArray("exercises");
        List<SingleChoiceExercise> singleChoiceExercises = new ArrayList<>();
        for (int i = 0; i < exercises.size(); i++) {
            JSONObject exerciseObject = exercises.getJSONObject(i);
            singleChoiceExercises.add(SingleChoiceExercise.generateExerciseFromJsonObject(exerciseObject, "JAVA"));
        }

        // 先删除所有数据，再导入全部数据
        mongoTemplate.dropCollection("singleChoiceExercise");
        mongoTemplate.createCollection("singleChoiceExercise");
        mongoTemplate.insert(singleChoiceExercises, "singleChoiceExercise");
    }

    @Test
    void javaProgramExercise2database() throws IOException {
        String fPath = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "exercises", "program", "java", "programExercises.json").toString();
        File file = new File(fPath);
        String jsonString = Files.readString(Path.of(file.getAbsolutePath()));
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        JSONArray exerciseArray = jsonObject.getJSONArray("exercises");
        List<JavaProgramExercise> javaProgramExercises = new ArrayList<>();
        for (int i = 0; i < exerciseArray.size(); i++) {
            JSONObject exerciseObject = exerciseArray.getJSONObject(i);
            javaProgramExercises.add(JavaProgramExercise.generateExerciseFromJsonObject(exerciseObject, "JAVA"));
        }

        // 先删除所有数据，再导入全部数据
        mongoTemplate.dropCollection("programExercise");
        mongoTemplate.createCollection("programExercise");
        mongoTemplate.insert(javaProgramExercises, "programExercise");
    }

    @Test
    void xes3g5mSingleChoiceExercise2database() throws IOException {
        // 对应数据库里的singleChoiceExercise集合
        String fPath = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "exercises", "math", "xes3g5m", "single_choice_question.json").toString();
        File file = new File(fPath);
        String jsonString = Files.readString(Path.of(file.getAbsolutePath()));
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        JSONArray exercises = jsonObject.getJSONArray("exercises");
        List<SingleChoiceExercise> singleChoiceExercises = new ArrayList<>();
        for (int i = 0; i < exercises.size(); i++) {
            JSONObject exerciseObject = exercises.getJSONObject(i);
            singleChoiceExercises.add(SingleChoiceExercise.generateExerciseFromJsonObject(exerciseObject, "JAVA"));
        }

        // 先删除所有数据，再导入全部数据
        mongoTemplate.dropCollection("singleChoiceExercise");
        mongoTemplate.createCollection("singleChoiceExercise");
        mongoTemplate.insert(singleChoiceExercises, "singleChoiceExercise");
    }
}
