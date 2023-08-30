package com.dream.exerciseSystem.dao;


import com.dream.exerciseSystem.domain.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class StudentDaoTest {
    @Autowired
    private IStudentDao studentDao;

    @Test
    void testCreateOne() {
        Student student = new Student("dream", "123", "18800118477@163.com");
        studentDao.createOne(student);
    }


    @Test
    void testRetrieveOneByName() {
        Student student = studentDao.retrieveOneByName("dream");
        System.out.println(student);
    }

    @Test
    void testRetrieveOneByEmail() {
        Student student = studentDao.retrieveOneByEmail("18800118477@163.com");
        System.out.println(student);
    }

    @Test
    void testRetrieveAll() {
        List<Student> students = studentDao.retrieveAll();
        System.out.println(students);
    }
}
