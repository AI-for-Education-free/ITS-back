package com.dream.exerciseSystem;


import com.dream.exerciseSystem.constant.StudentInfoConstant;
import com.dream.exerciseSystem.domain.Student;
import com.dream.exerciseSystem.domain.exercise.Util;
import com.dream.exerciseSystem.service.ErnieService;
import com.dream.exerciseSystem.service.impl.ErnieServiceImpl;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@SpringBootTest
public class ElasticsearchTest {
    String accessKey = "ALTAKOzE4JzuH5ePBQsgtnhraI";
    String secrectKey = "a9f126a0a6124690af02d1958b392b71";

    @Autowired
    ErnieService ernieService;

    @Test
    void loadMathExerciseKtVector() throws IOException, JSONException {
        String fPath = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "exercises", "math", "xes3g5m", "its_question_kt_portrait.json").toString();
        File file = new File(fPath);
        String jsonString = Files.readString(Path.of(file.getAbsolutePath()));
        JSONArray jsonArray = new JSONArray(jsonString);

        HashMap<String, List<Float>> questionVector = new HashMap<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);

            String id = obj.getString("question_id");
            JSONArray latentArray = obj.getJSONArray("question_emb");
            List<Float> latentList = new ArrayList<>();
            for (int j = 0; j < latentArray.length(); j++) {
                Double item = (Double)latentArray.get(j);
                latentList.add(item.floatValue());
            }

            questionVector.put(id, latentList);
        }

        System.out.println(questionVector);
    }

    @Test
    void loadUserMathVector() throws IOException, JSONException {
        String fPath = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "user", "xes3g5m_user_part.json").toString();
        File file = new File(fPath);
        String jsonString = Files.readString(Path.of(file.getAbsolutePath()));
        JSONArray jsonArray = new JSONArray(jsonString);

        HashMap<String, List<Float>> userVector = new HashMap<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);

            String email = obj.getString("email");
            String id = DigestUtils.md5DigestAsHex((StudentInfoConstant.salt+email).getBytes());

            JSONArray latentArray = obj.getJSONArray("latent");
            List<Float> latentList = new ArrayList<>();
            for (int j = 0; j < latentArray.length(); j++) {
                Double item = (Double)latentArray.get(j);
                latentList.add(item.floatValue());
            }

            userVector.put(id, latentList);
        }

        System.out.println(userVector);
    }

    @Test
    void getMathExerciseLlmVector() {
        String token = "24.7774bf49bfab28c02f5d3ac41dbcbed7.2592000.1718783984.282335-72413740";
        ErnieServiceImpl ernieServiceImpl = new ErnieServiceImpl();
        ernieServiceImpl.getTextEmbFromBgeLargeZH(token, "推荐一些美食");
    }
}
