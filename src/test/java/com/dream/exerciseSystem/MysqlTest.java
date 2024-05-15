package com.dream.exerciseSystem;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dream.exerciseSystem.domain.Student;
import com.dream.exerciseSystem.domain.StudentAnswerRecord;
import com.dream.exerciseSystem.service.IStudentService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@SpringBootTest
public class MysqlTest {
    @Resource
    private IStudentService iStudentService;

    @Test
    void testMysql(){
        Student studentInfo = new Student();

        int testId = 1;
        String testName = "xzj";
        String testEmail = "18800118477@163.com";
        String testPassword = "xzj12345";

        studentInfo.setId(testId);
        studentInfo.setName(testName);
        studentInfo.setEmail(testEmail);
        studentInfo.setPassword(testPassword);

        boolean result = iStudentService.save(studentInfo);
        Assertions.assertTrue(result);
    }

    @Test
    void xes3g5mUser2Database() throws IOException, JSONException {
        String fPath = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "user", "xes3g5m.json").toString();
        File file = new File(fPath);
        String jsonString = Files.readString(Path.of(file.getAbsolutePath()));
        JSONArray jsonArray = new JSONArray(jsonString);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            Student studentInfo = new Student();

            String name = obj.getString("name");
            String email = obj.getString("email");
            String password = obj.getString("password");
            studentInfo.setName(name);
            studentInfo.setEmail(email);
            studentInfo.setPassword(password);

            boolean result = iStudentService.save(studentInfo);
            Assertions.assertTrue(result);
        }
    }

    @Test
    void xes3g5mUserBehavior2database() throws IOException, JSONException {
        String fPath = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "userBehavior", "xes3g5m_demo.json").toString();
        File file = new File(fPath);
        String jsonString = Files.readString(Path.of(file.getAbsolutePath()));
        org.json.JSONArray jsonArray = new org.json.JSONArray(jsonString);
        for (int i = 0; i < jsonArray.length(); i++) {
            org.json.JSONObject obj = jsonArray.getJSONObject(i);
            String studentName = obj.getString("user_name");
            String questionId = obj.getString("question_id");
            boolean correct = obj.getBoolean("correct");
            Long timestamp = obj.getLong("timestamp");

            // 因为json数据里只有name（目前id是存储自增的id，不唯一，后续要改），所以先根据name查id
            QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("name", studentName);
            List<Student> targetStudents = iStudentService.list(queryWrapper);
            Student targetStudent = targetStudents.get(0);
            String studentId = targetStudent.getId().toString();

            StudentAnswerRecord<String> studentAnswerRecord = new StudentAnswerRecord<>(studentId, questionId, correct);
            studentAnswerRecord.setAnswerTimestamp(timestamp);
            System.out.println();
        }
    }
}
