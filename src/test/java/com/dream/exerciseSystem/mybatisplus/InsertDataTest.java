package com.dream.exerciseSystem.mybatisplus;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dream.exerciseSystem.domain.Student;
import com.dream.exerciseSystem.mapper.StudentMapper;
import com.dream.exerciseSystem.service.IStudentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class InsertDataTest {
    @Resource
    private IStudentService iStudentService;

    @Resource
    private StudentMapper studentMapper;

    @Test
    /**
     * @Description test add student function
     * @Author xzy
     *
     */
    void testAddStudent(){
        Student studentInfo = new Student();

        int testId = 1;
        String testName = "xzy";
        String testEmail = "xxxxx@gmail.com";
        String testPassword = "123456";

        studentInfo.setId(testId);
        studentInfo.setName(testName);
        studentInfo.setEmail(testEmail);
        studentInfo.setPassword(testPassword);

        boolean result = iStudentService.save(studentInfo);
        Assertions.assertTrue(result);
    }

}
