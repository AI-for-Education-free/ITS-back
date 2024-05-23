package com.dream.exerciseSystem.service;


import com.dream.exerciseSystem.utils.DataWrapper;

import java.util.List;
import java.util.Map;

public interface ErnieService {
    String getAccessToken(String apiKey, String secretKey);
    DataWrapper testHello(String accessToken);
    DataWrapper simpleChat(String accessToken, String chatUrl, List<Map<String, String>> messages);
    DataWrapper chatWithTemplate(String accessToken, String chatUrl, String templatePath);

}
