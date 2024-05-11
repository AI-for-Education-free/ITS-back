package com.dream.exerciseSystem;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dream.exerciseSystem.domain.Student;
import com.dream.exerciseSystem.mapper.StudentMapper;
import com.dream.exerciseSystem.service.IStudentService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MysqlTest {
    @Resource
    private IStudentService iStudentService;

    @Resource
    private StudentMapper studentMapper;

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
}
