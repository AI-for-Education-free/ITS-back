package com.dream.exerciseSystem.domain;
import lombok.Data;


// 替代getter、setter、toString等
@Data
public class User4MongodbTest {
    private String name;
    private Integer id;

    public User4MongodbTest() {}
    public User4MongodbTest(String name) {
        this.name = name;
    }
}
