package com.dream.exerciseSystem.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "exercisevector")
//@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ExerciseVector {
    @Id
    private String id;

//    @Field(type = FieldType.Keyword)
//    private String name;
    @Field(type = FieldType.Keyword)
    private String exerciseType;

    @Field(type = FieldType.Dense_Vector, dims = 64, index = true) // dims 表示向量的维度
    private float[] vector;

}
