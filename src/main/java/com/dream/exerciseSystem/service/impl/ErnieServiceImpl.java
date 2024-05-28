package com.dream.exerciseSystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dream.exerciseSystem.constant.BaiduAccessConstant;
import com.dream.exerciseSystem.domain.UserBalanceRecord;
import com.dream.exerciseSystem.domain.exercise.*;
import com.dream.exerciseSystem.mapper.StudentAnswerRecordMapper;
import com.dream.exerciseSystem.mapper.UserBalanceRecordMapper;
import com.dream.exerciseSystem.service.ErnieService;
import com.dream.exerciseSystem.utils.DataWrapper;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Service
public class ErnieServiceImpl implements ErnieService {

    @Resource
    private UserBalanceRecordMapper userBalanceRecordMapper;

    @Resource
    private MongoTemplate mongoTemplate;

    private final float temperature = (float) 0.95;
    private final float penaltyScore = (float) 1;

    public String getAccessToken(String apiKey, String secretKey) {
        RestTemplate restTemplate = new RestTemplate();

        // 设置URL和URL参数
        String url = "https://aip.baidubce.com/oauth/2.0/token?";
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("client_id", apiKey)
                .queryParam("client_secret", secretKey)
                .queryParam("grant_type", "client_credentials");
        URI uri = uriBuilder.build().encode().toUri();

        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "");

        // 设置请求体
        Map<String, Object> requestBody = new HashMap<>();

        // 发送请求并接收响应
        ResponseEntity<Object> responseEntity = getHttpResponse(restTemplate, uri, headers, requestBody);

        // 解析返回结果
        return ((HashMap<String, String>) responseEntity.getBody()).get("result");
    }

    @Override
    public DataWrapper testHello(String accessToken) {
        Map<String, String> data = new HashMap<>();
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/yi_34b_chat";

        // 设置URL和URL参数
        URI uri = buildUriWithAccessToken(url, accessToken);

        // 设置请求头
        HttpHeaders headers = buildHeaders();

        // 将对话信息转换为json字符串，以便放入body中
        List<HashMap<String, String>> messages = new ArrayList<>();
        HashMap<String, String> msg = new HashMap<>();
        msg.put("role", "user");
        msg.put("content", "你好，请自我介绍");
        messages.add(msg);

        // 设置请求体
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("messages", messages);
        requestBody.put("temperature", this.temperature);
        requestBody.put("top_k", 1);

        // 发送请求并接收响应
        ResponseEntity<Object> responseEntity = getHttpResponse(restTemplate, uri, headers, requestBody);

        // 解析返回结果
        HashMap<String, String> chatResult = parseChatResponseEntity(responseEntity);
        data.put("chatResult", chatResult.get("chatResult"));
        data.put("promptTokens", chatResult.get("promptTokens"));
        data.put("completionTokens", chatResult.get("completionTokens"));

        return new DataWrapper(true).msgBuilder("测试成功").dataBuilder(data);
    }

    @Override
    public DataWrapper simpleChat(String accessToken, String chatUrl, List<Map<String, String>> messages) {
        Map<String, String> data = new HashMap<>();
        RestTemplate restTemplate = new RestTemplate();

        // 设置URL和URL参数
        URI uri = buildUriWithAccessToken(chatUrl, accessToken);

        // 设置请求头
        HttpHeaders headers = buildHeaders();

        // 设置请求体
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("messages", messages);
        requestBody.put("temperature", this.temperature);

        // 发送请求并接收响应
        ResponseEntity<Object> responseEntity = getHttpResponse(restTemplate, uri, headers, requestBody);

        // 解析返回结果
        HashMap<String, String> chatResult = parseChatResponseEntity(responseEntity);
        data.put("chatResult", chatResult.get("chatResult"));
        data.put("promptTokens", chatResult.get("promptTokens"));
        data.put("completionTokens", chatResult.get("completionTokens"));

        return new DataWrapper(true).msgBuilder("测试成功").dataBuilder(data);
    }

    @Override
    public DataWrapper chatWithTemplate(String accessToken, String chatUrl, String templatePath) {
        return new DataWrapper(true).msgBuilder("测试成功");
    }

    @Override
    public DataWrapper chatWithStudentQuery(String studentId, String exerciseId, String studentQuery) throws IOException {
        // Init some data structure
        HashMap<String, Integer> numTokensBeforeChat;
        int promptTokens;
        int completionTokens;
        int userFAQTokenBalance;
        Exercise exercise = new Exercise();


        // loading template content
        String templateContent = this.loadTemplate(BaiduAccessConstant.templatePath);

        // query the exercise belongs to which subject, and acquire new templateContent
        org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
        query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("_id").is(exerciseId));
        if (mongoTemplate.exists(query, "javaProgramExercise")) {
            JavaProgramExercise result = mongoTemplate.findById(exerciseId, JavaProgramExercise.class);
            templateContent = templateContent.replace("${#SUBJECT#}$", result.getExerciseType());
            String tagsStr = "`" + String.join("、", result.getTags()) + "`";
            templateContent = templateContent.replace("${#CONCEPT_RELATED#}$", tagsStr);
            exercise = result.getExercise();
        }
        if (mongoTemplate.exists(query, "javaSingleChoiceExercise")) {
            SingleChoiceExercise result = mongoTemplate.findById(exerciseId, SingleChoiceExercise.class);
            templateContent = templateContent.replace("${#SUBJECT#}$", result.getExerciseType());
            String tagsStr = "`" + String.join("、", result.getTags()) + "`";
            templateContent = templateContent.replace("${#CONCEPT_RELATED#}$", tagsStr);
            exercise = result.getExercise();
        }
        if (mongoTemplate.exists(query, "fillInExercise")) {
            FillInExercise result = mongoTemplate.findById(exerciseId, FillInExercise.class);
            templateContent = templateContent.replace("${#SUBJECT#}$", result.getExerciseType());
            String tagsStr = "`" + String.join("、", result.getTags()) + "`";
            templateContent = templateContent.replace("${#CONCEPT_RELATED#}$", tagsStr);
            exercise = result.getExercise();
        }

        List<Content> exerciseContents = exercise.getExerciseContents();
        StringBuilder exerciseContentStr = new StringBuilder();
        for (Content exerciseContent: exerciseContents) {
            String contentType = exerciseContent.getContentType();
            if (contentType.equals("TEXT")) {
                exerciseContentStr.append(exerciseContent.getChinese()).append("\n");
            }
        }
        templateContent = templateContent.replace("${#QUESTION_CONTENT#}$", exerciseContentStr);
        String prompt = templateContent.replace("${#STUDENT_QUERY#}$", studentQuery);

        // Firstly, we calculate the prompt token according to the studentQuery
        numTokensBeforeChat = this.getNumToken(BaiduAccessConstant.accessToken, prompt, BaiduAccessConstant.modelName);
        promptTokens = numTokensBeforeChat.get("promptTokens");

        // Query the user balance record if the user has the sufficient tokens for continue chat
        QueryWrapper<UserBalanceRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", studentId);
        UserBalanceRecord userBalanceRecord = userBalanceRecordMapper.selectOne(queryWrapper);
        userFAQTokenBalance = userBalanceRecord.getFAQTokenBalance();
        if(userFAQTokenBalance < promptTokens){
            return new DataWrapper(true).msgBuilder("余额不足，无法生成prompt").dataBuilder("asking token: "+promptTokens);
        }
        // if user has enough token for submitting prompts, continue
        // Generate the new messages
        List<Map<String, String>> messages = new ArrayList<>();
        HashMap<String, String> msg = new HashMap<>();
        msg.put("role", "user");
        msg.put("content", prompt);
        messages.add(msg);

        DataWrapper response = this.simpleChat(BaiduAccessConstant.accessToken, BaiduAccessConstant.chatUrl, messages);

        HashMap<String, String> responseData = (HashMap<String, String>) response.getData();
        completionTokens = Integer.parseInt(responseData.get("completionTokens"));
        // if token is enough, return false
        if(userFAQTokenBalance < completionTokens+promptTokens){
            return new DataWrapper(true).msgBuilder("余额不足，无法获取回答").dataBuilder("all need tokens: "+(completionTokens+promptTokens));
        }
        // if token is enough, deduct the token in the datasource and return the answer back to frontend
        userBalanceRecord.setFAQTokenBalance(userFAQTokenBalance-completionTokens-promptTokens);
        userBalanceRecordMapper.updateById(userBalanceRecord);
        return response;
    }

    public String loadTemplate(String templatePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(templatePath)));
    }

    public static List<Double> extractEmbeddingValues(ResponseEntity<Object> responseEntity) {
        if (responseEntity == null || responseEntity.getBody() == null) {
            return null;
        }

        LinkedHashMap<String, Object> body = (LinkedHashMap<String, Object>) responseEntity.getBody();
        ArrayList<Object> data = (ArrayList<Object>) body.get("data");

        if (data == null || data.isEmpty()) {
            return null;
        }

        LinkedHashMap<String, Object> firstDataEntry = (LinkedHashMap<String, Object>) data.get(0);
        ArrayList<Double> embeddingArray = (ArrayList<Double>) firstDataEntry.get("embedding");

        if (embeddingArray == null || embeddingArray.isEmpty()) {
            return null;
        }

        // 假设 embeddingArray 是 List<Double>
        return embeddingArray;
    }

    public List<Float> getTextEmbFromBgeLargeZH(String accessToken, String text) {
        String url = "https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/embeddings/bge_large_zh";
        RestTemplate restTemplate = new RestTemplate();

        // 设置URL和URL参数
        URI uri = buildUriWithAccessToken(url, accessToken);

        // 设置请求头
        HttpHeaders headers = buildHeaders();

        // 设置请求体
        List<String> texts = new ArrayList<>();
        texts.add(text);
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("input", texts);

        // 发送请求并接收响应
        ResponseEntity<Object> responseEntity = getHttpResponse(restTemplate, uri, headers, requestBody);

        // 从 responseEntity 中提取 embedding 的 value 数组
        List<Double> embArray = extractEmbeddingValues(responseEntity);

        List<Float> embList = new ArrayList<>();
        for (Double item : embArray) {
            embList.add(item.floatValue());
        }

        return embList;
    }

    public HashMap<String, Integer> getNumToken(String accessToken, String text, String modelName) {
        String url = "https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/tokenizer/erniebot";
        RestTemplate restTemplate = new RestTemplate();

        // 设置URL和URL参数
        URI uri = buildUriWithAccessToken(url, accessToken);

        // 设置请求头
        HttpHeaders headers = buildHeaders();

        // 设置请求体
        String[] options = {"ernie-4.0-8k", "ernie-3.5-8k", "ernie-speed-8k", "ernie-speed-128k", "ernie-lite-8k",
        "ernie-tiny-8k", "ernie-char-8k"};
        boolean isValidModelName = Arrays.asList(options).contains(modelName);
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("prompt", text);
        if (isValidModelName) {
            requestBody.put("model", modelName);
        }

        ResponseEntity<Object> responseEntity = getHttpResponse(restTemplate, uri, headers, requestBody);
        HashMap<String, Object> body = (HashMap<String, Object>) responseEntity.getBody();
        HashMap<String, Integer> usage = (HashMap<String, Integer>) body.get("usage");
        Integer promptTokens = (Integer) usage.get("prompt_tokens");
        Integer completionTokens = (Integer) usage.get("completion_tokens");
        Integer totalTokens = (Integer) usage.get("total_tokens");

        HashMap<String, Integer> result = new HashMap<>();
        result.put("promptTokens", promptTokens);
        result.put("completionTokens", completionTokens);
        result.put("totalTokens", totalTokens);

        return result;
    }

    public URI buildUriWithAccessToken(String url, String accessToken) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("access_token", accessToken);
        return uriBuilder.build().encode().toUri();
    }

    public HttpHeaders buildHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "");

        return headers;
    }

    public ResponseEntity<Object> getHttpResponse(RestTemplate restTemplate, URI uri, HttpHeaders headers, Map<String, Object> requestBody) {
        // 创建HttpEntity对象，包含请求头和请求体，发送请求并接收响应
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
        return restTemplate.exchange(uri, HttpMethod.POST, requestEntity, Object.class);
    }

    public HashMap<String, String> parseChatResponseEntity(ResponseEntity<Object> responseEntity) {
        HashMap<String, String> data = new HashMap<>();
        HashMap<String, Object> body = (HashMap<String, Object>) responseEntity.getBody();
        String chatResult = (String) body.get("result");
        HashMap<String, Integer> usage = (HashMap<String, Integer>) body.get("usage");
        Integer promptTokens = (Integer) usage.get("prompt_tokens");
        Integer completionTokens = (Integer) usage.get("completion_tokens");

        data.put("chatResult", chatResult);
        data.put("promptTokens", promptTokens.toString());
        data.put("completionTokens", completionTokens.toString());

        return data;
    }
}
