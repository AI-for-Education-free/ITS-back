package com.dream.exerciseSystem.controller;

import com.dream.exerciseSystem.domain.exercise.ExerciseBasicInfo;
import com.dream.exerciseSystem.service.IFillInExerciseService;
import com.dream.exerciseSystem.service.IJavaProgramExerciseService;
import com.dream.exerciseSystem.service.SingleChoiceExerciseService;
import com.dream.exerciseSystem.utils.DataWrapper;
import com.dream.exerciseSystem.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/exercises")
public class ExerciseController {
    @Autowired
    private IJavaProgramExerciseService javaProgramExerciseService;

    @Autowired
    private SingleChoiceExerciseService singleChoiceExerciseService;

    @Autowired
    private IFillInExerciseService fillInExerciseService;

    @GetMapping("/type")
    @ResponseBody
    public DataWrapper getJavaExerciseType() {
        HashMap<String, List<String>> data = new HashMap<>();
        List<String> types = new ArrayList<>();
        types.add("JAVA_PROGRAM_EXERCISE");
        types.add("SINGLE_CHOICE_EXERCISE");
        types.add("FILL_IN_EXERCISE");
        data.put("typeList", types);
        return new DataWrapper(true).msgBuilder("请求习题类型成功").dataBuilder(data);
    }

    @GetMapping("/subject")
    @ResponseBody
    public DataWrapper getSubjectAll() {
        HashMap<String, List<String>> data = new HashMap<>();
        List<String> subjectList = new ArrayList<>();
        subjectList.add("JAVA");
        subjectList.add("MATH");
        data.put("subjectList", subjectList);
        return new DataWrapper(true).msgBuilder("请求已有学科成功").dataBuilder(data);
    }

    @GetMapping("/java/program/all")
    @ResponseBody
    public DataWrapper getJavaProgramExerciseAll() {
        return javaProgramExerciseService.getExerciseAll();
    }

    @GetMapping("/java/program/one/{exerciseId}")
    @ResponseBody
    public DataWrapper getJavaProgramExerciseOneById(@PathVariable String exerciseId) {
        return javaProgramExerciseService.getExerciseById(exerciseId);
    }

    @GetMapping("/singleChoice/all")
    @ResponseBody
    public DataWrapper getJavaSingleChoiceExerciseAll() {
        return singleChoiceExerciseService.getExerciseAll();
    }

    @GetMapping("/java/singleChoice/one/{exerciseId}")
    @ResponseBody
    public DataWrapper getJavaSingleChoiceExerciseOneById(@PathVariable String exerciseId) {
        return singleChoiceExerciseService.getExerciseById(exerciseId);
    }

    @PostMapping("/java/singleChoice/check/{exerciseId}")
    @ResponseBody
    public DataWrapper checkSingleChoiceExercise(@PathVariable String exerciseId, @RequestBody Map<String, String> requestBody, HttpServletRequest request) throws Exception{
        String token = request.getHeader("token");
        // 解析token
        String studentId;
        Claims claims = JwtUtil.parsedResult(token);
        studentId = claims.getSubject();

        String choiceAnswer = requestBody.get("submissionChoiceAnswer");
        return singleChoiceExerciseService.checkSingleChoiceExercise(exerciseId, choiceAnswer, studentId);
    }

    @PostMapping("/java/program/check/{exerciseId}")
    @ResponseBody
    public DataWrapper checkJavaProgramExercise(@PathVariable String exerciseId, @RequestBody Map<String, String> requestBody) throws Exception {
        String submissionCode = "package org.dream.solution;\n" + requestBody.get("submissionCode");
        return javaProgramExerciseService.checkExercise(exerciseId, submissionCode);
    }

    @PostMapping("/fillIn/check/{exerciseId}")
    @ResponseBody
    public DataWrapper checkFillInExercise(@PathVariable String exerciseId, @RequestBody Map<String, HashMap<String, String>> requestBody, HttpServletRequest request) throws Exception{
        String token = request.getHeader("token");
        // 解析token
        String studentId;
        Claims claims = JwtUtil.parsedResult(token);
        studentId = claims.getSubject();

        HashMap<String, String> fillInAnswer = requestBody.get("submissionFillInAnswer");
        return fillInExerciseService.checkFillInExercise(exerciseId, fillInAnswer, studentId);
    }


    @PostMapping("/java/program/check/xzy/{exerciseId}")
    @ResponseBody
    public DataWrapper checkJavaProgramExerciseXzy(@PathVariable String exerciseId, @RequestBody Map<String, String> requestBody, HttpServletRequest request) throws Exception {
        String token = request.getHeader("token");
        // 解析token
        String studentId;
        Claims claims = JwtUtil.parsedResult(token);
        studentId = claims.getSubject();
        String submissionCode = "package org.dream.solution;\n" + requestBody.get("submissionCode");
        return javaProgramExerciseService.checkExercise(exerciseId, submissionCode, studentId);
    }

    @GetMapping("/java/basicInfo/all")
    @ResponseBody
    public DataWrapper getJavaExerciseBasicInfoAll() {
        DataWrapper singleChoiceExerciseBasicInfo = singleChoiceExerciseService.getExerciseAllBasicInfo();
        DataWrapper programExerciseBasicInfo = javaProgramExerciseService.getExerciseAllBasicInfo();
        if (!singleChoiceExerciseBasicInfo.isFlag() && !programExerciseBasicInfo.isFlag()) {
            return new DataWrapper(false).msgBuilder("请求失败");
        } else {
            Map<String, List<ExerciseBasicInfo>> data = new HashMap<>();
            List<ExerciseBasicInfo> exerciseBasicInfoList = new ArrayList<>();
            HashMap<String, Object> singleChoiceExerciseBasicInfoData = (HashMap<String, Object>) singleChoiceExerciseBasicInfo.getData();
            HashMap<String, Object> programExerciseBasicInfoData = (HashMap<String, Object>) programExerciseBasicInfo.getData();
            exerciseBasicInfoList.addAll((List<ExerciseBasicInfo>) singleChoiceExerciseBasicInfoData.get("exerciseBasicInfoList"));
            exerciseBasicInfoList.addAll((List<ExerciseBasicInfo>) programExerciseBasicInfoData.get("exerciseBasicInfoList"));
            data.put("exerciseBasicInfoList", exerciseBasicInfoList);
            return new DataWrapper(true).msgBuilder("请求成功").dataBuilder(data);
        }
    }
}