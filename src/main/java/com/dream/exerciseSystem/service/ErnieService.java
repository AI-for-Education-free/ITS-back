package com.dream.exerciseSystem.service;


import com.dream.exerciseSystem.utils.DataWrapper;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ErnieService {
    DataWrapper testHello(String accessToken);
    DataWrapper simpleChat(String accessToken, String chatUrl, List<Map<String, String>> messages);
    DataWrapper chatWithTemplate(String accessToken, String chatUrl, String templatePath);

    DataWrapper chatWithStudentQuery(String studentId, String exerciseId, String studentQuery) throws IOException;
}
