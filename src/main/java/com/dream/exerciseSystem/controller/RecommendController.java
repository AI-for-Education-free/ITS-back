package com.dream.exerciseSystem.controller;

import com.dream.exerciseSystem.service.RecommendService;
import com.dream.exerciseSystem.utils.DataWrapper;
import com.dream.exerciseSystem.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/recommend")
public class RecommendController {

    @Resource
    private RecommendService recommendService;

    @PostMapping("default")
    public DataWrapper defaultRecommend(HttpServletRequest request){
        String token = request.getHeader("token");
        // 解析token
        String studentId;
        Claims claims = JwtUtil.parsedResult(token);
        studentId = claims.getSubject();

        return recommendService.defaultRecommend(studentId);
    }
}
