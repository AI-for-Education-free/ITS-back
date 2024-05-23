package com.dream.exerciseSystem.service;

import com.dream.exerciseSystem.service.impl.ErnieServiceImpl;
import com.dream.exerciseSystem.utils.DataWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class ErnieServiceTest {
    @Autowired
    ErnieService ernieService;

    @Test
    void testGetAccessToken() {
        String token = ernieService.getAccessToken("pXRhQnbhEQ1bNnWTs6vkVAxK", "5VGOnQHbNvPhsbh0hMUB3qoxY1LfNcWO");
        System.out.println(token);
    }

    @Test
    void testHello() {
        // 先运行上面的testGetAccessToken获取一下token
        String token = "24.7774bf49bfab28c02f5d3ac41dbcbed7.2592000.1718783984.282335-72413740";
        DataWrapper response = ernieService.testHello(token);
        System.out.println(response);
    }

    @Test
    void testChat() {
        // 先运行上面的testGetAccessToken获取一下token
        String token = "24.7774bf49bfab28c02f5d3ac41dbcbed7.2592000.1718783984.282335-72413740";
        // 这个自己到官网上选择
        String chatUrl = "https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/completions_pro";

        List<Map<String, String>> messages = new ArrayList<>();
        HashMap<String, String> msg = new HashMap<>();
        msg.put("role", "user");
        msg.put("content", "你好，请自我介绍，并告诉我你擅长的事情");
        messages.add(msg);

        DataWrapper response = ernieService.simpleChat(token, chatUrl, messages);
        System.out.println(response);
    }

    @Test
    void testLoadTemplate() throws IOException {
        ErnieServiceImpl ernieServiceImpl = new ErnieServiceImpl();
        String templatePath = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "template", "rec_wo_know_state.md").toString();
        String templateContent = ernieServiceImpl.loadTemplate(templatePath);
        System.out.println(templateContent);
    }
}
