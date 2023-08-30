package com.dream.exerciseSystem.service.impl;

import com.dream.exerciseSystem.dao.IStudentDao;
import com.dream.exerciseSystem.domain.LoginStudentDetails;
import com.dream.exerciseSystem.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private IStudentDao studentDao;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 查询用户信息
        Student targetStudent = studentDao.retrieveOneByEmail(email);
        if (Objects.isNull(targetStudent)) {
            throw new RuntimeException("用户名不存在");
        }
        // todo 查询权限信息

        List<String> list = new ArrayList<>();
        list.add("vip");
        return new LoginStudentDetails(targetStudent, list);
    }
}
