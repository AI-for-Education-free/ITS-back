package com.dream.exerciseSystem.utils;


import com.dream.exerciseSystem.domain.exercise.SingleChoiceExercise;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;


public class Exercise2Database {

    public static void main(String[] args) throws Exception {
        // 创建mongo连接
        MongoClientURI uri = new MongoClientURI("mongodb://127.0.0.1:27017");
        MongoClient mongoClient = new MongoClient(uri);

        // 获取mongo数据库
        MongoDatabase database = mongoClient.getDatabase("exerciseSystem");

        // 获取mongo集合
        MongoCollection<Document> programExerciseCollection = database.getCollection("programExercise");
        MongoCollection<Document> choiceExerciseCollection = database.getCollection("programExercise");


    }
}
