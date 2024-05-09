package com.dream.exerciseSystem.service;

import com.dream.exerciseSystem.domain.Student;
import com.dream.exerciseSystem.service.exception.NonexistentUserException;
import com.dream.exerciseSystem.service.exception.PasswordErrorException;
import com.dream.exerciseSystem.service.impl.StudentService;
import com.dream.exerciseSystem.utils.DataWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.annotation.Resource;
import java.util.HashMap;


@SpringBootTest
//@ActiveProfiles("dev")
public class StudentServiceTest {
    @Resource
    private StudentService studentService;

    @Test
    void testLogin() {
        DataWrapper trueStudentQueryResult = studentService.login("test2@gmail.com", "123456");
        System.out.println(trueStudentQueryResult);
//        Assertions.assertThrows(PasswordErrorException.class,
//                () -> studentService.login(passwordErrorStudent.getId(), passwordErrorStudent.getPassword()));
//        Assertions.assertThrows(NonexistentUserException.class,
//                () -> studentService.login(nonexistentStudent.getId(), nonexistentStudent.getPassword()));
    }

    @Test
    void testRegister() {
//        Student existedStudent = new Student("xzj", "12345678", "18800118477@163.com");
        Student newStudent = new Student("xzy2", "test2@gmail.com", "123456");
//        DataWrapper registerResult1 = studentService.register(existedStudent);
        DataWrapper registerResult2 = studentService.register(newStudent);
//        System.out.println(registerResult1);
        System.out.println(registerResult2);
    }
}
