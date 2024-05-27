package com.dream.exerciseSystem.constant;

import java.nio.file.Paths;

public class BaiduAccessConstant {

    public static final String accessToken = "24.7774bf49bfab28c02f5d3ac41dbcbed7.2592000.1718783984.282335-72413740";

    public static final String chatUrl = "https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/completions_pro";

    public static final String templatePath = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "template", "chat_wo_know_state.md").toString();

    public static final String modelName = "ernie-4.0-8k";
}
