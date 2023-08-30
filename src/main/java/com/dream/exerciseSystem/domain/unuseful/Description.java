package com.dream.exerciseSystem.domain.exercise;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class Description {
    public final DescriptionType type;
    public  String english;
    public  String chinese;
    public  String code;
    public  DescriptionStyle style;
    public  String imageName;

    public Description() {
        this.type = null;
        this.english = null;
        this.chinese = null;
        this.code = null;
        this.style = null;
        this.imageName = null;
    }

    public Description(DescriptionType type, String english, String chinese, DescriptionStyle style) {
        this.type = type;
        this.english = english;
        this.chinese = chinese;
        this.style = style;
    }

    public Description(DescriptionType type, String codeOrImgName) {
        this.type = type;
        if (type == DescriptionType.CODE) {
            this.code = codeOrImgName;
        }
        if (type == DescriptionType.IMAGE) {
            this.imageName = codeOrImgName;
        }
    }
}
