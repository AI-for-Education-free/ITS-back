package com.dream.exerciseSystem.controller;


import com.dream.exerciseSystem.utils.DataWrapper;
import com.dream.exerciseSystem.domain.Student;
import com.dream.exerciseSystem.service.IStudentService;
import com.dream.exerciseSystem.service.exception.NonexistentUserException;
import com.dream.exerciseSystem.service.exception.PasswordErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/students")
public class StudentController {
    // 上面用了注解Slf4j，就可以不自己创建log了
//    private static final Logger log = LoggerFactory.getLogger(StudentController.class);
    @Autowired
    private IStudentService studentService;

    @PostMapping("/login")
    @ResponseBody
    public DataWrapper handleLogin(@RequestBody Student student) throws PasswordErrorException, NonexistentUserException {
        log.info(student.getEmail() + " login at " + new Date());
        return studentService.login(student.getEmail(), student.getPassword());
    }

    @PostMapping("/register")
    @ResponseBody
    public DataWrapper handleRegister(@RequestBody Student student) {
        log.info(student.getName() + " register at " + new Date());
        return studentService.register(student);
    }


}
