package com.dream.exerciseSystem.utils;

import com.dream.exerciseSystem.domain.exercise.*;
import net.sf.json.JSONObject;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@Component
public class MongoUtils {

    @Resource
    private MongoTemplate mongoTemplate;

//    public List<String> getIdFromExerciseContent(List<Exercise> exerciseList){
//        List<String> exerciseIdList = new ArrayList<>();
//        for(Exercise exercise: exerciseList){
//            org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
//            query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("exercise").is(exercise));
//            if (mongoTemplate.exists(query, "javaProgramExercise")) {
//                JavaProgramExercise result = mongoTemplate.findOne(query, JavaProgramExercise.class);
//                exerciseIdList.add(result.getId());
//            } else if (mongoTemplate.exists(query, "javaSingleChoiceExercise")) {
//                SingleChoiceExercise result = mongoTemplate.findOne(query, SingleChoiceExercise.class);
//                exerciseIdList.add(result.getId());
//            } else if (mongoTemplate.exists(query, "fillInExercise")) {
//                FillInExercise result = mongoTemplate.findOne(query, FillInExercise.class);
//                exerciseIdList.add(result.getId());
//            }
//        }
//        return  exerciseIdList;
//    }

    public HashMap<String, Exercise> getRandomExerciseContent() {
        AggregationOperation sampleOperationSingleChoice = new AggregationOperation() {
            @Override
            public Document toDocument(AggregationOperationContext context) {
                return new Document("$sample", new Document("size", 2));
            }
        };

        AggregationOperation sampleOperationFillIn = new AggregationOperation() {
            @Override
            public Document toDocument(AggregationOperationContext context) {
                return new Document("$sample", new Document("size", 2));
            }
        };

        AggregationOperation sampleOperationJavaProgram = new AggregationOperation() {
            @Override
            public Document toDocument(AggregationOperationContext context) {
                return new Document("$sample", new Document("size", 1));
            }
        };

        // 创建聚合管道
        Aggregation aggregationSingleChoice = Aggregation.newAggregation(sampleOperationSingleChoice);
        Aggregation aggregationFillIn = Aggregation.newAggregation(sampleOperationFillIn);
        Aggregation aggregationJavaProgram = Aggregation.newAggregation(sampleOperationJavaProgram);

        // 执行聚合查询
        AggregationResults<Document> resultsSingleChoice = mongoTemplate.aggregate(aggregationSingleChoice, "javaSingleChoiceExercise", Document.class);
        AggregationResults<Document> resultsFillIn = mongoTemplate.aggregate(aggregationFillIn, "fillInExercise", Document.class);
        AggregationResults<Document> resultsJavaProgram = mongoTemplate.aggregate(aggregationJavaProgram, "javaProgramExercise", Document.class);

        // 返回结果列表
        HashMap<String, Exercise> exerciseHashMap = new HashMap<>();
        for(Document document: resultsSingleChoice){
            String exerciseId = (String) document.get("_id");
            Document exerciseDocument = (Document) document.get("exercise");
            MongoConverter mongoConverter = mongoTemplate.getConverter();
            Exercise exercise = mongoConverter.read(Exercise.class, exerciseDocument);
            exerciseHashMap.put(exerciseId, exercise);
        }
        for(Document document: resultsFillIn){
            String exerciseId = (String) document.get("_id");
            Document exerciseDocument = (Document) document.get("exercise");
            MongoConverter mongoConverter = mongoTemplate.getConverter();
            Exercise exercise = mongoConverter.read(Exercise.class, exerciseDocument);
            exerciseHashMap.put(exerciseId, exercise);
        }
        for(Document document: resultsJavaProgram){
            String exerciseId = (String) document.get("_id");
            Document exerciseDocument = (Document) document.get("exercise");
            MongoConverter mongoConverter = mongoTemplate.getConverter();
            Exercise exercise = mongoConverter.read(Exercise.class, exerciseDocument);
            exerciseHashMap.put(exerciseId, exercise);
        }

        return exerciseHashMap;
    }

}
