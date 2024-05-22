package com.dream.exerciseSystem.mapper;

import com.dream.exerciseSystem.domain.UserVector;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface UserVectorRepository extends ElasticsearchRepository<UserVector, String> {
    @Query("{" +
            "    \"script_score\": {" +
            "       \"query\": {\"match_all\": {}}," +
            "       \"script\": {"+
            "           \"source\": \"cosineSimilarity(params.queryVector, 'vector') +0.1\","+
            "           \"params\": {\"queryVector\": ?0}"+
            "       }"+
            "   }"+
            "}")
    List<UserVector> findBySimilar(String content);

}
