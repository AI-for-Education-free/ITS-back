package com.dream.exerciseSystem.domain.exercise;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
public class Content {
    // contentType: TEXT, IMAGE, VIDEO, CODE
    public String contentType;
    // style: NORMAL, BOLD, ITALIC, BOLD_ITALIC
    public String style;
    public String english;
    public String chinese;
    public String code;
    public String imageName;

    public Content() {
        this.contentType = "";
        this.style = "";
        this.english = "";
        this.chinese = "";
        this.code = "";
        this.imageName = "";
    }

    public Content(String contentType, String style, String english, String chinese, String code, String imageName) {
        this.contentType = contentType;
        this.style = style;
        this.english = english;
        this.chinese = chinese;
        this.code = code;
        this.imageName = imageName;
    }

    // 如果content是代码或图片，使用这个构造器
    public Content(String contentType, String codeOrImageName) {
        this.contentType = contentType;
        if (contentType.equals("CODE")) {
            this.code = codeOrImageName;
            this.imageName = "";
        } else {
            this.imageName = codeOrImageName;
            this.code = "";
        }
        this.style = "";
        this.english = "";
        this.chinese = "";
    }

    // 如果content是文本，并且指定字体风格
    public Content(String contentType, String style, String english, String chinese) {
        this.contentType = contentType;
        this.style = style;
        this.english = english;
        this.chinese = chinese;
        this.code = "";
        this.imageName = "";
    }

    // 如果content是文本，不指定字体风格
    public Content(String contentType, String english, String chinese) {
        this.contentType = contentType;
        this.style = "NORMAL";
        this.english = english;
        this.chinese = chinese;
        this.code = "";
        this.imageName = "";
    }

    public static List<Content> generateContentsFromJsonArray(JSONArray jsonArray) {
        List<Content> contents = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject contentObject = jsonArray.getJSONObject(i);
            Content content = Content.generateContentFromJsonObject(contentObject);
            contents.add(content);
        }
        return contents;
    }

    public static Content generateContentFromJsonObject(JSONObject jsonObject) {
        String contentType = (String) jsonObject.get("contentType");
        String codeOrImageName;
        // CODE
        if (contentType.equals("CODE")) {
            codeOrImageName = (String) jsonObject.get("code");
            return new Content(contentType, codeOrImageName);
        }
        // IMAGE
        if (contentType.equals("IMAGE")) {
            codeOrImageName = (String) jsonObject.get("imageName");
            return new Content(contentType, codeOrImageName);
        }
        // TEXT，暂时不考虑VIDEO
        String chinese;
        String english;
        if (jsonObject.containsKey("chinese")){
            chinese = (String) jsonObject.get("chinese");
        } else {
            chinese = "";
        }
        if (jsonObject.containsKey("english")){
            english = (String) jsonObject.get("english");
        } else {
            english = "";
        }
        if (jsonObject.containsKey("style")) {
            return new Content(contentType, (String) jsonObject.get("style"), english, chinese);
        } else {
            return new Content(contentType, english, chinese);
        }
    }
}
