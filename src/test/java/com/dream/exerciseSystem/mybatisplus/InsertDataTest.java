package com.dream.exerciseSystem.mybatisplus;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dream.exerciseSystem.domain.Student;
import com.dream.exerciseSystem.mapper.StudentMapper;
import com.dream.exerciseSystem.service.IStudentService;
import com.dream.exerciseSystem.utils.DataWrapper;
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
        Student studentInfo = new Student("xzy","ulyess3@gmail.com","123456");

//        String testName = "xzy";
//        String testEmail = "ulyess3@gmail.com";
//        String testPassword = "123456";
//
//        studentInfo.setName(testName);
//        studentInfo.setEmail(testEmail);
//        studentInfo.setPassword(testPassword);

        DataWrapper dataWrapper = iStudentService.register(studentInfo);
        Assertions.assertNotNull(dataWrapper);
    }

}
