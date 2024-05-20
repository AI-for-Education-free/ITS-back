package com.dream.exerciseSystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dream.exerciseSystem.domain.Student;
import com.dream.exerciseSystem.service.exception.NonexistentUserException;
import com.dream.exerciseSystem.service.exception.PasswordErrorException;
import com.dream.exerciseSystem.utils.DataWrapper;

public interface IStudentService extends IService<Student> {
    DataWrapper login(String email, String password);
    //DataWrapper register(Student student);
    DataWrapper register(Student student);

    void registerRabbitMQ(Student student);
}
