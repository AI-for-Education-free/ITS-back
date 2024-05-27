package com.dream.exerciseSystem.service;

import com.dream.exerciseSystem.domain.exercise.Content;
import com.dream.exerciseSystem.domain.exercise.Exercise;
import com.dream.exerciseSystem.domain.exercise.SingleChoiceExercise;
import com.dream.exerciseSystem.service.impl.ErnieServiceImpl;
import com.dream.exerciseSystem.utils.DataWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.annotation.Resource;
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

    @Resource
    private MongoTemplate mongoTemplate;

    @Test
    void testGetAccessToken() {
        ErnieServiceImpl ernieServiceImpl = new ErnieServiceImpl();
        String token = ernieServiceImpl.getAccessToken("pXRhQnbhEQ1bNnWTs6vkVAxK", "5VGOnQHbNvPhsbh0hMUB3qoxY1LfNcWO");
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
        // 这个自己到官网上选择，但是现阶段我们固定使用这个，以后为用户提供更多选择
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
    void testGetTokensNum() {
        // 先运行上面的testGetAccessToken获取一下token
        String token = "24.7774bf49bfab28c02f5d3ac41dbcbed7.2592000.1718783984.282335-72413740";
        String text = "你好，请自我介绍，并告诉我你能帮我做什么？";
        String modelName = "ernie-4.0-8k";

        ErnieServiceImpl ernieServiceImpl = new ErnieServiceImpl();
        HashMap<String, Integer> numTokens = ernieServiceImpl.getNumToken(token, text, modelName);

        System.out.println(numTokens);
    }

    @Test
    void testChatWithoutKnowledgeState() throws IOException {
        // 先运行上面的testGetAccessToken获取一下token
        String token = "24.7774bf49bfab28c02f5d3ac41dbcbed7.2592000.1718783984.282335-72413740";
        // 这个自己到官网上选择，目前统一使用这个
        String chatUrl = "https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/completions_pro";

        ErnieServiceImpl ernieServiceImpl = new ErnieServiceImpl();
        String templatePath = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "template", "chat_wo_know_state.md").toString();
        String templateContent = ernieServiceImpl.loadTemplate(templatePath);

        // 假设是数学题
        templateContent = templateContent.replace("${#SUBJECT#}$", "数学");
        // 假设是询问的习题id是64c6206c3741d385a487eb16bf637ba2（选择题）
        String exerciseId = "64c6206c3741d385a487eb16bf637ba2";
        SingleChoiceExercise singleChoiceExercise = mongoTemplate.findById(exerciseId, SingleChoiceExercise.class);

        String tagsStr = "`" + String.join("、", singleChoiceExercise.getTags()) + "`";
        templateContent = templateContent.replace("${#CONCEPT_RELATED#}$", tagsStr);

        Exercise exercise = singleChoiceExercise.getExercise();
        List<Content> exerciseContents = exercise.getExerciseContents();
        StringBuilder exerciseContentStr = new StringBuilder();
        for (Content exerciseContent: exerciseContents) {
            String contentType = exerciseContent.getContentType();
            if (contentType.equals("TEXT")) {
                exerciseContentStr.append(exerciseContent.getChinese()).append("\n");
            }
        }
        templateContent = templateContent.replace("${#QUESTION_CONTENT#}$", exerciseContentStr);

        // 假设这是学生的问题
        String studentQuery = "我不知道该怎么解这道题，请帮助我。";
        String prompt = templateContent.replace("${#STUDENT_QUERY#}$", studentQuery);

        List<Map<String, String>> messages = new ArrayList<>();
        HashMap<String, String> msg = new HashMap<>();
        msg.put("role", "user");
        msg.put("content", prompt);
        messages.add(msg);

        DataWrapper response = ernieService.simpleChat(token, chatUrl, messages);
        System.out.println(response);
    }
}
