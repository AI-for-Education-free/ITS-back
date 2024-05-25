package com.dream.exerciseSystem;

import com.dream.exerciseSystem.domain.Student;
import com.dream.exerciseSystem.domain.exercise.Exercise;
import com.dream.exerciseSystem.domain.exercise.FillInExercise;
import com.dream.exerciseSystem.domain.exercise.JavaProgramExercise;
import com.dream.exerciseSystem.domain.exercise.SingleChoiceExercise;
import com.dream.exerciseSystem.service.RecommendService;
import com.dream.exerciseSystem.service.impl.RecommendServiceImpl;
import com.dream.exerciseSystem.utils.MongoUtils;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.bson.Document;
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

    @Resource
    private MongoUtils mongoUtils;

    @Test
    void javaSingleChoiceExercise2database() throws IOException {
        // 对应数据库里的singleChoiceExercise集合
        String fPath = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "exercises", "program", "java", "singleChoiceExercises.json").toString();
        File file = new File(fPath);
        String jsonString = Files.readString(Path.of(file.getAbsolutePath()));
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        JSONArray exercises = jsonObject.getJSONArray("exercises");
        for (int i = 0; i < exercises.size(); i++) {
            JSONObject exerciseObject = exercises.getJSONObject(i);
            SingleChoiceExercise singleChoiceExercise = SingleChoiceExercise.generateExerciseFromJsonObject(exerciseObject, "JAVA");
            mongoTemplate.save(singleChoiceExercise);
        }
    }

    @Test
    void javaProgramExercise2database() throws IOException {
        String fPath = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "exercises", "program", "java", "programExercises.json").toString();
        File file = new File(fPath);
        String jsonString = Files.readString(Path.of(file.getAbsolutePath()));
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        JSONArray exerciseArray = jsonObject.getJSONArray("exercises");
        for (int i = 0; i < exerciseArray.size(); i++) {
            JSONObject exerciseObject = exerciseArray.getJSONObject(i);
            JavaProgramExercise javaProgramExercise = JavaProgramExercise.generateExerciseFromJsonObject(exerciseObject, "JAVA");
            mongoTemplate.save(javaProgramExercise);
        }
    }

    @Test
    void xes3g5mSingleChoiceExercise2database() throws IOException {
        // 对应数据库里的singleChoiceExercise集合
        String fPath = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "exercises", "math", "xes3g5m", "single_choice_question.json").toString();
        File file = new File(fPath);
        String jsonString = Files.readString(Path.of(file.getAbsolutePath()));
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        JSONArray exercises = jsonObject.getJSONArray("exercises");
        for (int i = 0; i < exercises.size(); i++) {
            JSONObject exerciseObject = exercises.getJSONObject(i);
            SingleChoiceExercise singleChoiceExercise = SingleChoiceExercise.generateExerciseFromJsonObject(exerciseObject, "MATH");
            mongoTemplate.save(singleChoiceExercise);
        }
    }

    @Test
    void xes3g5mFillInExercise2database() throws IOException {
        // 对应数据库里的singleChoiceExercise集合
        String fPath = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "exercises", "math", "xes3g5m", "fill_in_question.json").toString();
        File file = new File(fPath);
        String jsonString = Files.readString(Path.of(file.getAbsolutePath()));
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        JSONArray exercises = jsonObject.getJSONArray("exercises");
        for (int i = 0; i < exercises.size(); i++) {
            JSONObject exerciseObject = exercises.getJSONObject(i);
            FillInExercise fillInExercise = FillInExercise.generateExerciseFromJsonObject(exerciseObject, "MATH");
            mongoTemplate.save(fillInExercise);
        }
    }

    @Test
    void randomGetData(){
        List<Exercise> list = mongoUtils.getRandomExerciseContent();
        for(Exercise document:list){
            System.out.println(document.toString());
        }
    }
}
