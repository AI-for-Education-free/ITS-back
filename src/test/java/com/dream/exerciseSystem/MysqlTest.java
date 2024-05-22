package com.dream.exerciseSystem;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dream.exerciseSystem.constant.StudentInfoConstant;
import com.dream.exerciseSystem.domain.Student;
import com.dream.exerciseSystem.domain.StudentAnswerRecord;
import com.dream.exerciseSystem.service.IStudentService;
import com.dream.exerciseSystem.service.StudentAnswerRecordService;
import com.dream.exerciseSystem.service.impl.StudentService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@SpringBootTest
public class MysqlTest {
    @Resource
    private IStudentService iStudentService;

    @Resource
    private StudentAnswerRecordService studentAnswerRecordService;

    @Test
    void xes3g5mUser2Database() throws IOException, JSONException {
        String fPath = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "user", "xes3g5m.json").toString();
        File file = new File(fPath);
        String jsonString = Files.readString(Path.of(file.getAbsolutePath()));
        JSONArray jsonArray = new JSONArray(jsonString);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            Student student = new Student();

            String name = obj.getString("name");
            String email = obj.getString("email");
            String password = obj.getString("password");
            String id = DigestUtils.md5DigestAsHex((StudentInfoConstant.salt+email).getBytes());
            student.setId(id);
            student.setName(name);
            student.setEmail(email);
            student.setPassword(password);

            boolean result = iStudentService.save(student);
            Assertions.assertTrue(result);
        }
    }

//    @Test
//    void xes3g5mUserBehavior2database() throws IOException, JSONException {
//        String dataDir = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "userBehavior").toString();
//        String prefix = "xes3g5m_data_";
//        List<String> jsonFilePaths = new ArrayList<>();
//        File directory = new File(dataDir);
//
//        // 使用FileFilter过滤器获取符合条件的文件路径
//        File[] files = directory.listFiles(new FileFilter() {
//            @Override
//            public boolean accept(File file) {
//                return file.isFile() && file.getName().startsWith(prefix) && file.getName().toLowerCase().endsWith(".json");
//            }
//        });
//
//        if (files != null) {
//            for (File file : files) {
//                jsonFilePaths.add(file.getAbsolutePath());
//            }
//        }
//
//        List<File> fileList = jsonFilePaths.stream().map(
//                File::new
//        ).collect(Collectors.toList());
//
//        for (File file: fileList) {
//            String jsonString = Files.readString(Path.of(file.getAbsolutePath()));
//            org.json.JSONArray jsonArray = new org.json.JSONArray(jsonString);
//            for (int i = 0; i < jsonArray.length(); i++) {
//                org.json.JSONObject obj = jsonArray.getJSONObject(i);
//                String studentName = obj.getString("user_name");
//                String questionId = obj.getString("question_id");
//                boolean correct = obj.getBoolean("correct");
//                Long timestamp = obj.getLong("timestamp");
//
//                // 因为json数据里只有name，所以先根据name查id
//                QueryWrapper<Student> queryUserIdWrapper = new QueryWrapper<>();
//                queryUserIdWrapper.eq("name", studentName);
//                List<Student> targetStudents = iStudentService.list(queryUserIdWrapper);
//                Student targetStudent = targetStudents.get(0);
//                String studentId = targetStudent.getId();
//
//                StudentAnswerRecord studentAnswerRecord = new StudentAnswerRecord(
//                        studentId, questionId, timestamp, correct
//                );
//
//                // 查询是否存在数据，因为该数据集下可能存在一个人在相同时间戳下做了同一道题两次
//                QueryWrapper<StudentAnswerRecord> queryStudentAnswerRecordIdWrapper = new QueryWrapper<>();
//                queryStudentAnswerRecordIdWrapper.eq("id", studentId + questionId + timestamp);
//                List<StudentAnswerRecord> studentAnswerRecords = studentAnswerRecordService.list(queryStudentAnswerRecordIdWrapper);
//                if (studentAnswerRecords.size() == 0) {
//                    boolean result = studentAnswerRecordService.save(studentAnswerRecord);
//                    Assertions.assertTrue(result);
//                }
//            }
//        }
//    }
}
