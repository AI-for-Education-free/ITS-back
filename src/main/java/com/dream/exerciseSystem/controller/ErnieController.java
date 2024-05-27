package com.dream.exerciseSystem.controller;

import com.dream.exerciseSystem.service.ErnieService;
import com.dream.exerciseSystem.utils.DataWrapper;
import com.dream.exerciseSystem.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/ernie")
public class ErnieController {

    @Resource
    private ErnieService ernieService;

    @PostMapping("/chat/{exerciseId}")
    @ResponseBody
    public DataWrapper getJavaExerciseType(@PathVariable String exerciseId, @RequestBody Map<String, String> requestBody, HttpServletRequest request) throws IOException {
        String token = request.getHeader("token");
        // 解析token
        String studentId;
        Claims claims = JwtUtil.parsedResult(token);
        studentId = claims.getSubject();
        String studentQuery = requestBody.get("StudentQuery");
        return ernieService.chatWithStudentQuery(studentId, exerciseId, studentQuery);
        //return new DataWrapper(true).msgBuilder("请求习题类型成功").dataBuilder(data);
    }
}
