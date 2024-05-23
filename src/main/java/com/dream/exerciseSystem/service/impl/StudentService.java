package com.dream.exerciseSystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dream.exerciseSystem.config.RabbitMQConfig;
import com.dream.exerciseSystem.constant.StudentInfoConstant;
import com.dream.exerciseSystem.domain.LoginStudentDetails;
import com.dream.exerciseSystem.domain.Student;
//import com.dream.exerciseSystem.domain.Student2Mongodb;
import com.dream.exerciseSystem.domain.UserBalanceRecord;
import com.dream.exerciseSystem.mapper.UserBalanceRecordMapper;
import com.dream.exerciseSystem.service.IStudentService;
import com.dream.exerciseSystem.utils.DataWrapper;
import com.dream.exerciseSystem.utils.JwtUtil;
import com.dream.exerciseSystem.utils.RedisCache;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.dream.exerciseSystem.mapper.StudentMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 * Modified for the new model studentInfo
 * @author xzy
 * @createDate 2024.5.9
 *
 */
@Service
public class StudentService extends ServiceImpl<StudentMapper, Student> implements IStudentService{
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    // use mybatis instead of using DAO, therefore delete the code below
    // @author xzy
    //    @Autowired(required = false)
    //    private IStudentDao studentDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     *
     * Add mybatis mapper bean
     *
     */
    @Resource
    private StudentMapper studentMapper;


    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Resource
    private UserBalanceRecordMapper userBalanceRecordMapper;


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
        //String id = loginStudentDetails.getStudent().getId().toString();
        String id = loginStudentDetails.getStudent().getId();
        String jwt = JwtUtil.getInstance().clear().subjectBuilder(id).build();
        Map<String,String> data = new HashMap<>();
        data.put("student", loginStudentDetails.getStudent().getName());
        data.put("token", jwt);
        // 把完整的用户信息存入redis  id作为key
        redisCache.setCacheObject("student:login:" + id, loginStudentDetails);

        return new DataWrapper(true).msgBuilder("登入成功").dataBuilder(data);
    }

    /**
     *
     *  Modified by xzy, use mybatis
     *
     * @param student
     * @return Datawrapper
     */
    @Override
    public DataWrapper register(Student student) {

//        Student retrieveResult = studentDao.retrieveOneByEmail(student.getEmail());
//        if (!Objects.isNull(retrieveResult)) {
//            throw new RuntimeException("该邮箱已被注册");
//        }
//        String encodedPassword = passwordEncoder.encode(student.getPassword());
//        student.setPassword(encodedPassword);
//        boolean createResult = studentDao.createOne(student);
//        Map<String, String> data = new HashMap<>();
//        if (createResult) {
//            return new DataWrapper(true).msgBuilder("注册成功").dataBuilder(data);
//        } else {
//            data.put("reason", "插入数据失败");
//            return new DataWrapper(false).msgBuilder("注册失败").dataBuilder(data);
//        }
        // Query if the email is registered or not
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", student.getEmail());
        // Get the email number from the database
        int emailCount = studentMapper.selectCount(queryWrapper);
        if (emailCount > 0) {
            throw new RuntimeException("该邮箱已被注册");
        }

        // Encode the password
        String encodedPassword = passwordEncoder.encode(student.getPassword());
        student.setPassword(encodedPassword);
        student.setId(DigestUtils.md5DigestAsHex((StudentInfoConstant.salt+student.getEmail()).getBytes()));
        boolean createResult = this.save(student);
        Map<String, String> data = new HashMap<>();

        // Create the new balance table for the new registered student
        UserBalanceRecord userBalanceRecord = new UserBalanceRecord();
        userBalanceRecord.setUserId(DigestUtils.md5DigestAsHex((StudentInfoConstant.salt+student.getEmail()).getBytes()));
        userBalanceRecordMapper.insert(userBalanceRecord);

        if (createResult) {
            return new DataWrapper(true).msgBuilder("注册成功").dataBuilder(data);
        } else {
            data.put("reason", "插入数据失败");
            return new DataWrapper(false).msgBuilder("注册失败").dataBuilder(data);
        }

    }

    @Override
    @RabbitListener(queues = RabbitMQConfig.REGISTER_QUEUE)
    public void registerRabbitMQ(Student student){
        // Query if the email is registered or not
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", student.getEmail());
        // Get the email number from the database
        int emailCount = studentMapper.selectCount(queryWrapper);
        if (emailCount > 0) {
            rabbitTemplate.convertAndSend(RabbitMQConfig.REPLY_QUEUE, new DataWrapper(false).msgBuilder("the email has been registered").dataBuilder(null));
            //throw new RuntimeException("该邮箱已被注册");
            throw new MessageConversionException("该邮箱已被注册");
            //return;
        }

        // Encode the password
        String encodedPassword = passwordEncoder.encode(student.getPassword());
        student.setPassword(encodedPassword);
        student.setId(DigestUtils.md5DigestAsHex((StudentInfoConstant.salt+student.getEmail()).getBytes()));
        boolean createResult = this.save(student);
        Map<String, String> data = new HashMap<>();

        if (createResult) {
            rabbitTemplate.convertAndSend(RabbitMQConfig.REPLY_QUEUE, new DataWrapper(true).msgBuilder("注册成功").dataBuilder(data));
            //return new DataWrapper(true).msgBuilder("注册成功").dataBuilder(data);
        } else {
            data.put("reason", "插入数据失败");
            rabbitTemplate.convertAndSend(RabbitMQConfig.REPLY_QUEUE, new DataWrapper(false).msgBuilder("注册失败").dataBuilder(data));
            //return new DataWrapper(false).msgBuilder("注册失败").dataBuilder(data);
        }
    }
}
