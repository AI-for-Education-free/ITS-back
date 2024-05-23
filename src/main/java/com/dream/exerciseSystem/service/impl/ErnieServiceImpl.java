package com.dream.exerciseSystem.service.impl;

import com.dream.exerciseSystem.service.ErnieService;
import com.dream.exerciseSystem.utils.DataWrapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Service
public class ErnieServiceImpl implements ErnieService {
    private final float temperature = (float) 0.95;
    private final float penaltyScore = (float) 1;

    @Override
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

        return new DataWrapper(true).msgBuilder("测试成功").dataBuilder(data);
    }

    @Override
    public DataWrapper chatWithTemplate(String accessToken, String chatUrl, String templatePath) {

        return new DataWrapper(true).msgBuilder("测试成功");
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

    public int getNumToken(String accessToken, String text, String modelName) {
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
        requestBody.put("input", text);
        if (isValidModelName) {
            requestBody.put("model", modelName);
        }

        ResponseEntity<Object> responseEntity = getHttpResponse(restTemplate, uri, headers, requestBody);
        HashMap<String, Object> body = (HashMap<String, Object>) responseEntity.getBody();
        HashMap<String, Integer> usage = (HashMap<String, Integer>) body.get("usage");
        Integer promptTokens = (Integer) usage.get("prompt_tokens");

        return promptTokens;
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
