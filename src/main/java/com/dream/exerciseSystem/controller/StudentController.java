package com.dream.exerciseSystem.controller;


import com.dream.exerciseSystem.config.RabbitMQConfig;
import com.dream.exerciseSystem.utils.DataWrapper;
import com.dream.exerciseSystem.domain.Student;
import com.dream.exerciseSystem.service.IStudentService;
import com.dream.exerciseSystem.service.exception.NonexistentUserException;
import com.dream.exerciseSystem.service.exception.PasswordErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/students")
public class StudentController {
    // 上面用了注解Slf4j，就可以不自己创建log了
//    private static final Logger log = LoggerFactory.getLogger(StudentController.class);
    @Autowired
    private IStudentService studentService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

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

    //This url is used for register by using rabbitMQ, do not use this url for registering
    @PostMapping("/xzy/register")
    @ResponseBody
    public DataWrapper handleRegisterTest(@RequestBody Student student) {
        String correlationId = UUID.randomUUID().toString();
        CorrelationData correlationData = new CorrelationData(correlationId);

        log.info(student.getName() + " register at " + new Date());
        rabbitTemplate.convertAndSend(RabbitMQConfig.REGISTER_QUEUE, student, correlationData);

        Object registerResult = rabbitTemplate.receiveAndConvert(RabbitMQConfig.REPLY_QUEUE, 2000);
        return (DataWrapper) registerResult;
        //return studentService.register(student);
    }
}
