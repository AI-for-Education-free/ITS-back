package com.dream.exerciseSystem.service.impl;

import com.dream.exerciseSystem.dao.IStudentDao;
import com.dream.exerciseSystem.domain.LoginStudentDetails;
import com.dream.exerciseSystem.domain.Student;
import com.dream.exerciseSystem.service.IStudentService;
import com.dream.exerciseSystem.utils.DataWrapper;
import com.dream.exerciseSystem.utils.JwtUtil;
import com.dream.exerciseSystem.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class StudentService implements IStudentService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private IStudentDao studentDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public DataWrapper login(String email, String password) {
        // AuthenticationManager authenticate进行用户认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        // 如果认证没通过，给出对应的提示
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("登录失败");
        }
        // 如果认证通过了，使用id生成一个jwt
        LoginStudentDetails loginStudentDetails = (LoginStudentDetails) authenticate.getPrincipal();
        String id = loginStudentDetails.getStudent().getId().toString();
        String jwt = JwtUtil.getInstance().clear().subjectBuilder(id).build();
        Map<String,String> data = new HashMap<>();
        data.put("student", loginStudentDetails.getStudent().getName());
        data.put("token", jwt);
        // 把完整的用户信息存入redis  id作为key
        redisCache.setCacheObject("student:login:" + id, loginStudentDetails);

        return new DataWrapper(true).msgBuilder("登入成功").dataBuilder(data);
    }

    @Override
    public DataWrapper register(Student student) {
        Student retrieveResult = studentDao.retrieveOneByEmail(student.getEmail());
        if (!Objects.isNull(retrieveResult)) {
            throw new RuntimeException("该邮箱已被注册");
        }
        String encodedPassword = passwordEncoder.encode(student.getPassword());
        student.setPassword(encodedPassword);
        boolean createResult = studentDao.createOne(student);
        Map<String, String> data = new HashMap<>();
        if (createResult) {
            return new DataWrapper(true).msgBuilder("注册成功").dataBuilder(data);
        } else {
            data.put("reason", "插入数据失败");
            return new DataWrapper(false).msgBuilder("注册失败").dataBuilder(data);
        }
    }
}
