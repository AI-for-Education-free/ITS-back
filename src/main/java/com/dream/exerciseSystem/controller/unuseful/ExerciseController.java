package com.dream.exerciseSystem.controller.unuseful;

import com.dream.exerciseSystem.service.unuseful.IExerciseService;
import com.dream.exerciseSystem.utils.DataWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/exercises")
public class ExerciseController {
    @Autowired
    private IExerciseService exerciseService;

    @GetMapping("/{exerciseId}")
    @ResponseBody
    public DataWrapper handleRequireOneExercise(@PathVariable String exerciseId) {
        return exerciseService.getExerciseById(exerciseId);
    }

    @ResponseBody
    public DataWrapper handleRequireExerciseTypes() {
        return new DataWrapper(true).msgBuilder("请求习题类型成功").dataBuilder("program,single-choice");
    }

    @PostMapping("/check/{exerciseId}")
    @ResponseBody
    public DataWrapper handleCheckExercise(@PathVariable String exerciseId, @RequestBody Map<String, String> requestBody) throws Exception {
        String submissionCode = "package org.dream.solution;\n" + requestBody.get("submissionCode");
        return exerciseService.getExerciseResult(exerciseId, submissionCode);
    }
}
