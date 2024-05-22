package com.dream.exerciseSystem.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 *
 * @author xzy
 */
@Document(indexName = "uservector")
//@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class UserVector {
    @Id
    private String id;

//    @Field(type = FieldType.Keyword)
//    private String name;

    @Field(type = FieldType.Dense_Vector, dims = 800, index = true) // dims 表示向量的维度
    private float[] vector;

    @Field(type = FieldType.Dense_Vector, dims = 800, index = true) // dims 表示向量的维度
    private float[] vector2;
}
