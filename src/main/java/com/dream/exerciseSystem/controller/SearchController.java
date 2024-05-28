package com.dream.exerciseSystem.controller;

import com.dream.exerciseSystem.service.SearchService;
import com.dream.exerciseSystem.utils.DataWrapper;
import com.dream.exerciseSystem.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/search")
public class SearchController {

    @Resource
    private SearchService searchService;

    @PostMapping("/getExerciseInfo")
    @ResponseBody
    public DataWrapper getExerciseInfoByQuestionIdAndQuestionType(@RequestBody Map<String, String> requestBody, HttpServletRequest request){
        String token = request.getHeader("token");
        // 解析token
        String studentId;
        Claims claims = JwtUtil.parsedResult(token);
        studentId = claims.getSubject();

        String questionId = requestBody.get("questionId");
        String questionType = requestBody.get("questionType");

        return searchService.searchByQuestionIdAndQuestionType(questionId, questionType);
    }

    @GetMapping("/getStudentInfo")
    @ResponseBody
    public DataWrapper getStudentInfoByStudentId(HttpServletRequest request){
        String token = request.getHeader("token");
        // 解析token
        String userId;
        Claims claims = JwtUtil.parsedResult(token);
        userId = claims.getSubject();

        return searchService.searchUserInfoByUserId(userId);
    }

    @PostMapping("/getStudentAnswerRecord")
    @ResponseBody
    public DataWrapper getStudentAnswerRecordWithLastNum(@RequestBody Map<String, String> requestBody, HttpServletRequest request){
        String token = request.getHeader("token");
        // 解析token
        String userId;
        Claims claims = JwtUtil.parsedResult(token);
        userId = claims.getSubject();

        int lastNum = Integer.parseInt(requestBody.get("lastNum"));
        return searchService.searchUserAnswerRecordByUserId(userId, lastNum);
    }
}
